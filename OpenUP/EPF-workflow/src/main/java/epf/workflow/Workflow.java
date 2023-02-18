package epf.workflow;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.JsonValue;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.util.MapUtil;
import epf.util.json.JsonUtil;
import epf.workflow.schema.ActionDefinition;
import epf.workflow.schema.ActionMode;
import epf.workflow.schema.CallbackState;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.ErrorDefinition;
import epf.workflow.schema.EventDataFilters;
import epf.workflow.schema.EventDefinition;
import epf.workflow.schema.EventState;
import epf.workflow.schema.ForEachState;
import epf.workflow.schema.InjectState;
import epf.workflow.schema.OnEventsDefinition;
import epf.workflow.schema.OperationState;
import epf.workflow.schema.ParallelState;
import epf.workflow.schema.ParallelStateBranch;
import epf.workflow.schema.ProducedEventDefinition;
import epf.workflow.schema.SleepState;
import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.SwitchState;
import epf.workflow.schema.SwitchStateConditions;
import epf.workflow.schema.SwitchStateDataConditions;
import epf.workflow.schema.SwitchStateEventConditions;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.Type;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.WorkflowError;
import epf.workflow.util.WorkflowUtil;
import epf.workflow.schema.State;
import epf.workflow.schema.StateDataFilters;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Workflow {
	
	/**
	 * 
	 */
	private final List<OnEvents> onEvents = new CopyOnWriteArrayList<>();
	
	/**
	 * 
	 */
	private final List<Callback> callbacks = new CopyOnWriteArrayList<>();
	
	/**
	 * 
	 */
	@Inject
	javax.enterprise.event.Event<Event> producedEvent;
	
	/**
	 * 
	 */
	@Inject
	ManagedExecutor executor;

	/**
	 * @param workflowDefinition
	 * @param workflowDataInput
	 * @throws Exception
	 */
	public void start(final WorkflowDefinition workflowDefinition, final WorkflowData workflowData) throws Exception {
		State startState = null;
		if(workflowDefinition.getStart() != null) {
			if(workflowDefinition.getStart() instanceof String) {
				final String start = (String)workflowDefinition.getStart();
				for(State state : workflowDefinition.getStates()) {
					if(start.equals(state.getName())) {
						startState = state;
						break;
					}
				}
			}
			else if(workflowDefinition.getStart() instanceof StartDefinition) {
				final StartDefinition startDef = (StartDefinition) workflowDefinition.getStart();
				for(State state : workflowDefinition.getStates()) {
					if(startDef.getStateName().equals(state.getName())) {
						startState = state;
						break;
					}
				}
			}
		}
		else {
			startState = workflowDefinition.getStates()[0];
		}
		if(startState != null) {
			if(startState.getType() == Type.Event) {
				final EventState eventState = (EventState)startState;
				startEventState(workflowDefinition, eventState, workflowData);
			}
			else {
				final WorkflowInstance workflowInstance = new WorkflowInstance(workflowDefinition, workflowData, new Event[0]);
				workflowInstance.start(startState);
				startState(startState, workflowInstance);
			}
		}
	}
	
	private void startState(final State state, final WorkflowInstance workflowInstance) throws Exception {
		switch(state.getType()) {
			case Event:
				final EventState eventState = (EventState)state;
				startEventState(eventState, workflowInstance);
				break;
			case Operation:
				final OperationState operationState = (OperationState) state;
				startOperationState(operationState, workflowInstance);
				break;
			case Switch:
				final SwitchState switchState = (SwitchState) state;
				startSwitchState(switchState, workflowInstance);
				break;
			case Sleep:
				final SleepState sleepState = (SleepState) state;
				startSleepState(sleepState, workflowInstance);
				break;
			case Parallel:
				final ParallelState parallelState = (ParallelState) state;
				startParallelState(parallelState, workflowInstance);
				break;
			case Inject:
				final InjectState injectState = (InjectState) state;
				startInjectState(injectState, workflowInstance);
				break;
			case ForEach:
				final ForEachState forEachState = (ForEachState) state;
				startForEachState(forEachState, workflowInstance);
				break;
			case Callback:
				final CallbackState callbackState = (CallbackState) state;
				startCallbackState(callbackState, workflowInstance);
				break;
			default:
				break;
		}
	}
	
	private void startEventState(final EventState eventState, final WorkflowInstance workflowInstance) {
		if(workflowInstance.getWorkflowDefinition().getEvents() instanceof EventDefinition[]) {
			onEvents.add(new OnEvents(eventState, workflowInstance));
		}
	}
	
	private void startEventState(final WorkflowDefinition workflowDefinition, final EventState eventState, final WorkflowData workflowData) {
		if(workflowDefinition.getEvents() instanceof EventDefinition[]) {
			onEvents.add(new OnEvents(workflowDefinition, eventState, workflowData));
		}
	}
	
	private void startOperationState(final OperationState operationState, final WorkflowInstance workflowInstance) throws Exception {
		try {
			filterStateDataInput(operationState.getStateDataFilter(), workflowInstance.getWorkflowData());
			final Duration actionExecTimeout = WorkflowUtil.getActionExecTimeout(workflowInstance.getWorkflowDefinition(), operationState);
			performActions(workflowInstance.getWorkflowDefinition(), operationState.getActionMode(), operationState.getActions(), workflowInstance.getWorkflowData(), actionExecTimeout);
			if(operationState.getEnd() != null) {
				end(operationState, operationState.getEnd(), workflowInstance);
			}
			else {
				filterStateDataOutput(operationState.getStateDataFilter(), workflowInstance.getWorkflowData());
				transition(operationState, operationState.getTransition(), workflowInstance);
			}
		}
		catch(WorkflowException ex) {
			if(operationState.getOnErrors() != null) {
				onErrors(operationState, operationState.getOnErrors(), ex, workflowInstance);
			}
		}
	}
	
	private void startSwitchState(final SwitchState switchState, final WorkflowInstance workflowInstance) throws Exception {
		filterStateDataInput(switchState.getStateDataFilter(), workflowInstance.getWorkflowData());
		boolean evaluate = false;
		if(switchState.getDataConditions() != null) {
			try {
				for(SwitchStateDataConditions condition : switchState.getDataConditions()) {
					if(WorkflowUtil.evaluateCondition(workflowInstance.getWorkflowData().getInput(), condition.getCondition())) {
						endCondition(switchState, condition, workflowInstance);
						evaluate = true;
						break;
					}
				}
			}
			catch(WorkflowException ex) {
				if(switchState.getOnErrors() != null) {
					onErrors(switchState, switchState.getOnErrors(), ex, workflowInstance);
				}
			}
		}
		else if(switchState.getEventConditions() != null) {
			final Map<String, EventDefinition> eventDefs = new HashMap<>();
			MapUtil.putAll(eventDefs, (EventDefinition[])workflowInstance.getWorkflowDefinition().getEvents(), EventDefinition::getName);
			try {
				for(SwitchStateEventConditions condition : switchState.getEventConditions()) {
					final EventDefinition eventDefinition = eventDefs.get(condition.getEventRef());
					boolean isEvent = false;
					for(Event event : workflowInstance.getEvents()) {
						if(isEvent(eventDefinition, event)) {
							isEvent = true;
							break;
						}
					}
					if(isEvent) {
						evaluate = true;
						endCondition(switchState, condition, workflowInstance);
						break;
					}
				}
			}
			catch(WorkflowException ex) {
				if(switchState.getOnErrors() != null) {
					onErrors(switchState, switchState.getOnErrors(), ex, workflowInstance);
				}
			}
		}
		if(!evaluate) {
			if(switchState.getDefaultCondition() instanceof TransitionDefinition) {
				filterStateDataOutput(switchState.getStateDataFilter(), workflowInstance.getWorkflowData());
				transition(switchState, switchState.getDefaultCondition(), workflowInstance);
			}
			else if(switchState.getDefaultCondition() instanceof EndDefinition) {
				end(switchState, switchState.getDefaultCondition(), workflowInstance);
			}
		}
	}
	
	private void startSleepState(final SleepState sleepState, final WorkflowInstance workflowInstance) throws Exception {
		final Duration duration = Duration.parse(sleepState.getDuration());
		Thread.currentThread();
		Thread.sleep(duration.getSeconds() * 1000);
		if(sleepState.getEnd() != null) {
			end(sleepState, sleepState.getEnd(), workflowInstance);
		}
		else if(sleepState.getTransition() != null) {
			transition(sleepState, sleepState.getTransition(), workflowInstance);
		}
	}
	
	private void startParallelState(final ParallelState parallelState, final WorkflowInstance workflowInstance) throws Exception {
		filterStateDataInput(parallelState.getStateDataFilter(), workflowInstance.getWorkflowData());
		final List<ParallelBranch> branches = new ArrayList<>();
		for(ParallelStateBranch branch : parallelState.getBranches()) {
			branches.add(new ParallelBranch(branch));
		}
		try {
			switch(parallelState.getCompletionType()) {
				case allOf:
					executor.invokeAll(branches);
					break;
				case atLeast:
					executor.invokeAny(branches);
					break;
				default:
					break;
			}
			if(parallelState.getEnd() != null) {
				end(parallelState, parallelState.getEnd(), workflowInstance);
			}
			else if(parallelState.getTransition() != null) {
				filterStateDataOutput(parallelState.getStateDataFilter(), workflowInstance.getWorkflowData());
				transition(parallelState, parallelState.getTransition(), workflowInstance);
			}
		}
		catch(ExecutionException ex) {
			if(parallelState.getOnErrors() != null && ex.getCause() instanceof WorkflowException) {
				onErrors(parallelState, parallelState.getOnErrors(), (WorkflowException)ex.getCause(), workflowInstance);
			}
		}
	}
	
	private void startInjectState(final InjectState injectState, final WorkflowInstance workflowInstance) throws Exception {
		filterStateDataInput(injectState.getStateDataFilter(), workflowInstance.getWorkflowData());
		WorkflowUtil.mergeStateDataOutput(workflowInstance.getWorkflowData(), injectState.getData());
		if(injectState.getEnd() != null) {
			end(injectState, injectState.getEnd(), workflowInstance);
		}
		else if(injectState.getTransition() != null) {
			filterStateDataOutput(injectState.getStateDataFilter(), workflowInstance.getWorkflowData());
			transition(injectState, injectState.getTransition(), workflowInstance);
		}
	}
	
	private void startForEachState(final ForEachState forEachState, final WorkflowInstance workflowInstance) throws Exception {
		filterStateDataInput(forEachState.getStateDataFilter(), workflowInstance.getWorkflowData());
		try {
			switch(forEachState.getMode()) {
				case parallel:
					final List<ForEach> batch = new ArrayList<>();
					executor.invokeAll(batch);
					break;
				case sequential:
					break;
				default:
					break;
			}
			if(forEachState.getEnd() != null) {
				end(forEachState, forEachState.getEnd(), workflowInstance);
			}
			else if(forEachState.getTransition() != null) {
				filterStateDataOutput(forEachState.getStateDataFilter(), workflowInstance.getWorkflowData());
				transition(forEachState, forEachState.getTransition(), workflowInstance);
			}
		}
		catch(WorkflowException ex) {
			if(forEachState.getOnErrors() != null) {
				onErrors(forEachState, forEachState.getOnErrors(), ex, workflowInstance);
			}
		}
	}
	
	private void startCallbackState(final CallbackState callbackState, final WorkflowInstance workflowInstance) throws Exception {
		filterStateDataInput(callbackState.getStateDataFilter(), workflowInstance.getWorkflowData());
		try {
			final Action action = newAction(workflowInstance.getWorkflowDefinition(), callbackState.getAction(), workflowInstance.getWorkflowData());
			action.call();
			callbacks.add(new Callback(callbackState, workflowInstance));
		}
		catch(WorkflowException ex) {
			if(callbackState.getOnErrors() != null) {
				onErrors(callbackState, callbackState.getOnErrors(), ex, workflowInstance);
			}
		}
	}
	
	private void callback(final CallbackState callbackState, final WorkflowInstance workflowInstance, final EventDefinition eventDefinition, final Event event) throws Exception {
		filterEventData(eventDefinition, callbackState.getEventDataFilter(), workflowInstance.getWorkflowData(), event);
		if(callbackState.getEnd() != null) {
			end(callbackState, callbackState.getEnd(), workflowInstance);
		}
		else if(callbackState.getTransition() != null) {
			filterStateDataOutput(callbackState.getStateDataFilter(), workflowInstance.getWorkflowData());
			transition(callbackState, callbackState.getTransition(), workflowInstance);
		}
	}
	
	private void endCondition(final SwitchState switchState, final SwitchStateConditions condition, final WorkflowInstance workflowInstance) throws Exception {
		if(condition.getEnd() != null) {
			end(switchState, condition.getEnd(), workflowInstance);
		}
		else if(condition.getTransition() != null) {
			filterStateDataOutput(switchState.getStateDataFilter(), workflowInstance.getWorkflowData());
			transition(switchState, condition.getTransition(), workflowInstance);
		}
	}
	
	/**
	 * @param event
	 * @throws Exception 
	 */
	public void onEvent(@Observes final Event event) throws Exception {
		for(OnEvents onEventsItem : this.onEvents) {
			final WorkflowDefinition workflowDefinition = onEventsItem.getWorkflowDefinition();
			if(workflowDefinition.getEvents() instanceof EventDefinition[]) {
				onEvents(onEventsItem, event);
			}
		}
		callback(event);
	}
	
	private void callback(final Event event) throws Exception {
		for(Callback callback : callbacks) {
			if(callback.getWorkflowInstance().getWorkflowDefinition().getEvents() instanceof EventDefinition[]) {
				final EventDefinition[] events = (EventDefinition[]) callback.getWorkflowInstance().getWorkflowDefinition().getEvents();
				final Optional<EventDefinition> eventDefinition = Arrays.asList(events).stream().filter(e -> e.getName().equals(callback.getCallbackState().getEventRef())).findFirst();
				if(eventDefinition.isPresent() && isEvent(eventDefinition.get(), event)) {
					callbacks.remove(callback);
					callback(callback.getCallbackState(), callback.getWorkflowInstance(), eventDefinition.get(), event);
				}
			}
		}
	}
	
	private void onEvents(final OnEvents onEvents, final Event event) throws Exception {
		final WorkflowDefinition workflowDefinition = onEvents.getWorkflowDefinition();
		final EventDefinition[] events = (EventDefinition[]) workflowDefinition.getEvents();
		final Map<String, EventDefinition> eventDefs = new ConcurrentHashMap<>();
		MapUtil.putAll(eventDefs, events, EventDefinition::getName);
		final EventState eventState = onEvents.getEventState();
		for(OnEventsDefinition onEventDef : eventState.getOnEvents()) {
			onEvents(eventState, onEvents, onEventDef, eventDefs, event);
		}
	}
	
	private static boolean isEvent(final EventDefinition eventDefinition, final Event event) {
		return eventDefinition.getSource().equals(event.getSource().toString()) && eventDefinition.getType().equals(event.getType());
	}
	
	private void onEvents(final EventState eventState, final OnEvents onEvents, final OnEventsDefinition onEvent, final Map<String, EventDefinition> eventDefs, final Event event) throws Exception {
		final List<EventDefinition> eventRefs = MapUtil.getAll(eventDefs, onEvent.getEventRefs());
		boolean isEvent = false;
		EventDefinition eventDefinition = null;
		for(EventDefinition eventRef : eventRefs) {
			if(isEvent(eventRef, event)) {
				eventDefinition = eventRef;
				if(eventState.isExclusive()) {
					isEvent = true;
					break;
				}
				else {
					onEvents.addEvent(event);
				}
			}
		}
		if(!isEvent) {
			isEvent = true;
			for(EventDefinition eventRef : eventRefs) {
				if(isEvent) {
					for(Event ev : onEvents.getEvents()) {
						if(isEvent(eventRef, ev)) {
							eventDefinition = eventRef;
						}
						else {
							isEvent = false;
							break;
						}
					}
				}
			}
		}
		if(isEvent) {
			this.onEvents.remove(onEvents);
			WorkflowInstance workflowInstance = onEvents.getWorkflowInstance();
			if(workflowInstance == null) {
				workflowInstance = new WorkflowInstance(onEvents.getWorkflowDefinition(), onEvents.getWorkflowData(), onEvents.getEvents());
				workflowInstance.start(eventState);
			}
			try {
				filterStateDataInput(eventState.getStateDataFilter(), workflowInstance.getWorkflowData());
				filterEventData(eventDefinition, onEvent.getEventDataFilter(), workflowInstance.getWorkflowData(), event);
				final Duration actionExecTimeout = WorkflowUtil.getActionExecTimeout(workflowInstance.getWorkflowDefinition(), onEvents.getEventState());
				performActions(workflowInstance.getWorkflowDefinition(), onEvent.getActionMode(), onEvent.getActions(), workflowInstance.getWorkflowData(), actionExecTimeout);
				if(onEvents.getEventState().getEnd() != null) {
					end(onEvents.getEventState(), onEvents.getEventState().getEnd(), workflowInstance);
				}
				else {
					filterStateDataOutput(eventState.getStateDataFilter(), workflowInstance.getWorkflowData());
					transition(onEvents.getEventState(), onEvents.getEventState().getTransition(), workflowInstance);
				}
			}
			catch(WorkflowException ex) {
				if(onEvents.getEventState().getOnErrors() != null) {
					onErrors(onEvents.getEventState(), onEvents.getEventState().getOnErrors(), ex, workflowInstance);
				}
			}
		}
	}
	
	private void filterStateDataInput(final StateDataFilters stateDataFilters, final WorkflowData workflowData) {
		if(stateDataFilters != null && stateDataFilters.getInput() != null) {
			final JsonValue newInput = WorkflowUtil.filterValue(stateDataFilters.getInput(), workflowData.getInput());
			workflowData.setInput(newInput);
		}
	}
	
	private void filterStateDataOutput(final StateDataFilters stateDataFilters, final WorkflowData workflowData) {
		if(stateDataFilters != null && stateDataFilters.getOutput() != null) {
			final JsonValue newOutput = WorkflowUtil.filterValue(stateDataFilters.getOutput(), workflowData.getOutput());
			workflowData.setOutput(newOutput);
		}
	}
	
	private void filterEventData(final EventDefinition eventDefinition, final EventDataFilters eventDataFilters, final WorkflowData WorkflowData, final Event event) throws Exception {
		if(eventDataFilters != null && eventDataFilters.isUseData()) {
			JsonValue eventData = null;
			if(eventDefinition.isDataOnly()) {
				if(event.getData() instanceof JsonValue) {
					eventData = (JsonValue) event.getData();
				}
				else if(event.getData() instanceof String) {
					eventData = JsonUtil.readValue((String)event.getData());
				}
				else {
					eventData = JsonUtil.toJson(event.getData());
				}
			}
			else {
				eventData = JsonUtil.toJson(event);
			}
			JsonValue data = eventData;
			if(eventDataFilters.getData() != null) {
				data = WorkflowUtil.filterValue(eventDataFilters.getData(), eventData);
			}
			if(eventDataFilters.getToStateData() != null) {
				WorkflowUtil.mergeStateDataOutput(eventDataFilters.getToStateData(), WorkflowData, data);
			}
			else {
				WorkflowUtil.mergeStateDataOutput(WorkflowData, data);
			}
		}
	}
	
	private void onErrors(final State state, final ErrorDefinition[] onErrors, final WorkflowException exception, final WorkflowInstance workflowInstance) throws Exception {
		for(ErrorDefinition errorDefinition : onErrors) {
			final List<WorkflowError> errors = getErrors(workflowInstance.getWorkflowDefinition(), errorDefinition);
			final Optional<WorkflowError> error = errors.stream().filter(err -> err.equals(exception.getWorkflowError())).findFirst();
			if(error.isPresent()) {
				if(errorDefinition.getEnd() != null) {
					end(state, errorDefinition.getEnd(), workflowInstance);
				}
				else if(errorDefinition.getTransition() != null) {
					transition(state, errorDefinition.getTransition(), workflowInstance);
				}
				return;
			}
		}
		throw exception;
	}
	
	private void compensate(final State state, final WorkflowInstance workflowInstance) throws Exception {
		String compensatedBy = null;
		switch(state.getType()) {
			case Event:
				final EventState eventState = (EventState)state;
				compensatedBy = eventState.getCompensatedBy();
				break;
			case Operation:
				final OperationState operationState = (OperationState) state;
				compensatedBy = operationState.getCompensatedBy();
				break;
			case Switch:
				final SwitchState switchState = (SwitchState) state;
				compensatedBy = switchState.getCompensatedBy();
				break;
			case Sleep:
				break;
			case Parallel:
				final ParallelState parallelState = (ParallelState) state;
				compensatedBy = parallelState.getCompensatedBy();
				break;
			case Inject:
				final InjectState injectState = (InjectState) state;
				compensatedBy = injectState.getCompensatedBy();
				break;
			case ForEach:
				final ForEachState forEachState = (ForEachState) state;
				compensatedBy = forEachState.getCompensatedBy();
				break;
			case Callback:
				final CallbackState callbackState = (CallbackState) state;
				compensatedBy = callbackState.getCompensatedBy();
				break;
			default:
				break;
		}
		if(compensatedBy != null) {
			for(State compensatedByState : workflowInstance.getWorkflowDefinition().getStates()) {
				if(compensatedByState.getName().equals(state.getName())) {
					boolean usedForCompensation = false;
					switch(compensatedByState.getType()) {
						case Event:
							break;
						case Operation:
							final OperationState operationState = (OperationState) state;
							usedForCompensation = operationState.isUsedForCompensation();
							break;
						case Switch:
							final SwitchState switchState = (SwitchState) state;
							usedForCompensation = switchState.isUsedForCompensation();
							break;
						case Sleep:
							break;
						case Parallel:
							final ParallelState parallelState = (ParallelState) state;
							usedForCompensation = parallelState.isUsedForCompensation();
							break;
						case Inject:
							final InjectState injectState = (InjectState) state;
							usedForCompensation = injectState.isUsedForCompensation();
							break;
						case ForEach:
							final ForEachState forEachState = (ForEachState) state;
							usedForCompensation = forEachState.isUsedForCompensation();
							break;
						case Callback:
							final CallbackState callbackState = (CallbackState) state;
							usedForCompensation = callbackState.isUsedForCompensation();
							break;
						default:
							break;
					}
					if(usedForCompensation) {
						workflowInstance.compensate(state);
						startState(state, workflowInstance);
					}
					break;
				}
			}
		}
	}
	
	private void end(final State state, final Object end, final WorkflowInstance workflowInstance) throws Exception {
		workflowInstance.end(state);
		if(end instanceof EndDefinition) {
			final EndDefinition endDefinition = (EndDefinition) end;
			if(endDefinition.isCompensate()) {
				compensate(state, workflowInstance);
			}
			if(endDefinition.isTerminate()) {
				return;
			}
			if(endDefinition.getProduceEvents() != null) {
				produceEvents(workflowInstance.getWorkflowDefinition(), endDefinition.getProduceEvents());
			}
		}
	}
	
	private void transition(final State state, final Object transition, final WorkflowInstance workflowInstance) throws Exception {
		if(transition instanceof String) {
			for(State nextState : workflowInstance.getWorkflowDefinition().getStates()) {
				if(nextState.getName().equals((String)transition)) {
					workflowInstance.transition(nextState);
					startState(nextState, workflowInstance);
					return;
				}
			}
		}
		else if(transition instanceof TransitionDefinition) {
			final TransitionDefinition transitionDef = (TransitionDefinition) transition;
			if(transitionDef.isCompensate()) {
				compensate(state, workflowInstance);
			}
			produceEvents(workflowInstance.getWorkflowDefinition(), transitionDef.getProduceEvents());
			for(State nextState : workflowInstance.getWorkflowDefinition().getStates()) {
				if(nextState.getName().equals(transitionDef.getNextState())) {
					workflowInstance.transition(nextState);
					startState(nextState, workflowInstance);
					return;
				}
			}
		}
	}
	
	private void produceEvents(final WorkflowDefinition workflowDefinition, final ProducedEventDefinition[] produceEvents) throws Exception {
		if(!(workflowDefinition.getEvents() instanceof String)) {
			final Map<String, EventDefinition> eventDefs = new HashMap<>();
			MapUtil.putAll(eventDefs, (EventDefinition[])workflowDefinition.getEvents(), EventDefinition::getName);
			for(ProducedEventDefinition produceEventDef : produceEvents) {
				final EventDefinition eventDef = eventDefs.get(produceEventDef.getEventRef());
				final Event event = new Event();
				event.setSource(new URI(eventDef.getSource()));
				event.setType(eventDef.getType());
				if(!(produceEventDef.getData() instanceof String)) {
					event.setData(produceEventDef.getData());
				}
				producedEvent.fire(event);
			}
		}
	}
	
	private void filterActionDataInput(final ActionDefinition actionDefinition, final WorkflowData workflowData, final WorkflowData actionData) {
		if(actionDefinition.getActionDataFilter() != null && actionDefinition.getActionDataFilter().getFromStateData() != null) {
			final JsonValue input = WorkflowUtil.filterValue(actionDefinition.getActionDataFilter().getFromStateData(), workflowData.getInput());
			actionData.setInput(input);
		}
	}
	
	private void filterActionDataOutput(final ActionDefinition actionDefinition, final WorkflowData workflowData, final WorkflowData actionData) {
		if(actionDefinition.getActionDataFilter() != null && actionDefinition.getActionDataFilter().isUseResults() && actionDefinition.getActionDataFilter().getResults() != null) {
			if(actionDefinition.getActionDataFilter().getToStateData() != null) {
				WorkflowUtil.mergeStateDataOutput(actionDefinition.getActionDataFilter().getToStateData(), workflowData, actionData.getOutput());
			}
			else {
				WorkflowUtil.mergeStateDataOutput(workflowData, actionData.getOutput());
			}
		}
	}
	
	private Action newAction(final WorkflowDefinition workflowDefinition, final ActionDefinition actionDefinition, final WorkflowData workflowData) {
		final WorkflowData actionData = new WorkflowData();
		filterActionDataInput(actionDefinition, workflowData, actionData);
		return new Action(workflowDefinition, actionDefinition, actionData);
	}
	
	private void performActions(final WorkflowDefinition workflowDefinition, final ActionMode mode, final ActionDefinition[] actionDefs, final WorkflowData workflowData, final Duration actionExecTimeout) throws Exception {
		final List<Action> actions = Arrays.asList(actionDefs).stream().map(actionDef -> newAction(workflowDefinition, actionDef, workflowData)).collect(Collectors.toList());
		switch(mode) {
			case parallel:
				if(actionExecTimeout != null) {
					executor.invokeAll(actions, actionExecTimeout.toMillis(), TimeUnit.MICROSECONDS);
				}
				else {
					executor.invokeAll(actions);
				}
				break;
			case sequential:
				for(Action action : actions) {
					if(actionExecTimeout != null) {
						executor.invokeAll(Arrays.asList(action), actionExecTimeout.toMillis(), TimeUnit.MICROSECONDS);
					}
					else {
						action.call();
					}
				}
				break;
			default:
				break;
		}
		for(Action action : actions) {
			filterActionDataOutput(action.getActionDefinition(), workflowData, action.getWorkflowData());
		}
	}
	
	private List<WorkflowError> getErrors(final WorkflowDefinition workflowDefinition, final ErrorDefinition errorDefinition) {
		if(!(workflowDefinition.getErrors() instanceof String)) {
			final WorkflowError[] errors = (WorkflowError[]) workflowDefinition.getErrors();
			if(errorDefinition.getErrorRefs() != null) {
				final Map<String, WorkflowError> map = new HashMap<>();
				MapUtil.putAll(map, errors, WorkflowError::getName);
				return MapUtil.getAll(map, errorDefinition.getErrorRefs());
			}
			else if(errorDefinition.getErrorRef() != null) {
				for(WorkflowError error : errors) {
					if(error.getName().equals(errorDefinition.getErrorRef())) {
						return Arrays.asList(error);
					}
				}
			}
		}
		return Arrays.asList();
	}
}
