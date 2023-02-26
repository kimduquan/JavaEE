package epf.workflow;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonValue;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.util.MapUtil;
import epf.util.json.JsonUtil;
import epf.workflow.action.Action;
import epf.workflow.action.SubflowAction;
import epf.workflow.action.schema.ActionDefinition;
import epf.workflow.action.schema.Mode;
import epf.workflow.event.Event;
import epf.workflow.event.EventAction;
import epf.workflow.event.OnEvents;
import epf.workflow.event.schema.EventDataFilters;
import epf.workflow.event.schema.EventDefinition;
import epf.workflow.event.schema.OnEventsDefinition;
import epf.workflow.event.schema.ProducedEventDefinition;
import epf.workflow.event.util.EventUtil;
import epf.workflow.function.FunctionAction;
import epf.workflow.schema.ContinueAs;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.ErrorDefinition;
import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.WorkflowError;
import epf.workflow.state.Branch;
import epf.workflow.state.Callback;
import epf.workflow.state.schema.CallbackState;
import epf.workflow.state.schema.EventState;
import epf.workflow.state.schema.ForEachState;
import epf.workflow.state.schema.InjectState;
import epf.workflow.state.schema.OperationState;
import epf.workflow.state.schema.ParallelState;
import epf.workflow.state.schema.ParallelStateBranch;
import epf.workflow.state.schema.SleepState;
import epf.workflow.state.schema.State;
import epf.workflow.state.schema.StateDataFilters;
import epf.workflow.state.schema.SwitchState;
import epf.workflow.state.schema.SwitchStateConditions;
import epf.workflow.state.schema.SwitchStateDataConditions;
import epf.workflow.state.schema.SwitchStateEventConditions;
import epf.workflow.state.schema.Type;
import epf.workflow.state.util.StateUtil;
import epf.workflow.util.ELUtil;
import epf.workflow.util.TimeoutUtil;
import epf.workflow.schema.SubFlowRefDefinition;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class WorkflowRuntime {
	
	/**
	 * 
	 */
	private final Map<String, WorkflowInstance> workflowInstances = new ConcurrentHashMap<>();
	
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
	private final List<EventAction> eventActions = new CopyOnWriteArrayList<>();
	
	/**
	 * 
	 */
	@Inject
	transient javax.enterprise.event.Event<Event> producedEvent;
	
	/**
	 * 
	 */
	@Inject
	transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowPersistence workflowRepository;

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
			if(startState.getType() == Type.event) {
				final EventState eventState = (EventState)startState;
				startEventState(workflowDefinition, eventState, workflowData);
			}
			else {
				final WorkflowInstance workflowInstance = newWorkflowInstance(workflowDefinition, workflowData, new Event[0]);
				startState(startState, workflowInstance);
			}
		}
	}
	
	private WorkflowInstance newWorkflowInstance(WorkflowDefinition workflowDefinition, WorkflowData workflowData, Event[] events) {
		final WorkflowInstance workflowInstance = new WorkflowInstance(workflowDefinition, UUID.randomUUID().toString(), workflowData, events);
		if(Boolean.TRUE.equals(workflowInstance.getWorkflowDefinition().isKeepActive())) {
			workflowInstances.put(workflowInstance.getId(), workflowInstance);
		}
		return workflowInstance;
	}
	
	private void startState(final State state, final WorkflowInstance workflowInstance) throws Exception {
		if(workflowInstance.isTerminate()) {
			return;
		}
		switch(state.getType()) {
			case event:
				final EventState eventState = (EventState)state;
				startEventState(eventState, workflowInstance);
				break;
			case operation:
				final OperationState operationState = (OperationState) state;
				startOperationState(operationState, workflowInstance);
				break;
			case Switch:
				final SwitchState switchState = (SwitchState) state;
				startSwitchState(switchState, workflowInstance);
				break;
			case sleep:
				final SleepState sleepState = (SleepState) state;
				startSleepState(sleepState, workflowInstance);
				break;
			case parallel:
				final ParallelState parallelState = (ParallelState) state;
				startParallelState(parallelState, workflowInstance);
				break;
			case inject:
				final InjectState injectState = (InjectState) state;
				startInjectState(injectState, workflowInstance);
				break;
			case foreach:
				final ForEachState forEachState = (ForEachState) state;
				startForEachState(forEachState, workflowInstance);
				break;
			case callback:
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
			final Duration actionExecTimeout = TimeoutUtil.getActionExecTimeout(workflowInstance.getWorkflowDefinition(), operationState);
			performActions(operationState.getActionMode(), operationState.getActions(), actionExecTimeout, workflowInstance);
			if(workflowInstance.isTerminate()) {
				return;
			}
			if(operationState.getTransition() != null) {
				filterStateDataOutput(operationState.getStateDataFilter(), workflowInstance.getWorkflowData());
				transition(operationState.getTransition(), workflowInstance);
			}
			else if(operationState.getEnd() != null) {
				end(operationState.getEnd(), workflowInstance);
			}
		}
		catch(WorkflowException ex) {
			if(operationState.getOnErrors() != null) {
				onErrors(operationState.getOnErrors(), ex, workflowInstance);
			}
		}
	}
	
	private void startSwitchState(final SwitchState switchState, final WorkflowInstance workflowInstance) throws Exception {
		filterStateDataInput(switchState.getStateDataFilter(), workflowInstance.getWorkflowData());
		boolean evaluate = false;
		if(switchState.getDataConditions() != null) {
			try {
				for(SwitchStateDataConditions condition : switchState.getDataConditions()) {
					if(workflowInstance.isTerminate()) {
						return;
					}
					if(ELUtil.evaluateCondition(workflowInstance.getWorkflowData().getInput(), condition.getCondition())) {
						endCondition(switchState, condition, workflowInstance);
						evaluate = true;
						break;
					}
				}
			}
			catch(WorkflowException ex) {
				if(switchState.getOnErrors() != null) {
					onErrors(switchState.getOnErrors(), ex, workflowInstance);
				}
			}
		}
		else if(switchState.getEventConditions() != null) {
			final Map<String, EventDefinition> eventDefs = new HashMap<>();
			MapUtil.putAll(eventDefs, (EventDefinition[])workflowInstance.getWorkflowDefinition().getEvents(), EventDefinition::getName);
			try {
				for(SwitchStateEventConditions condition : switchState.getEventConditions()) {
					if(workflowInstance.isTerminate()) {
						return;
					}
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
					onErrors(switchState.getOnErrors(), ex, workflowInstance);
				}
			}
		}
		if(workflowInstance.isTerminate()) {
			return;
		}
		if(!evaluate) {
			if(switchState.getDefaultCondition() instanceof TransitionDefinition) {
				filterStateDataOutput(switchState.getStateDataFilter(), workflowInstance.getWorkflowData());
				transition(switchState.getDefaultCondition(), workflowInstance);
			}
			else if(switchState.getDefaultCondition() instanceof EndDefinition) {
				end(switchState.getDefaultCondition(), workflowInstance);
			}
		}
	}
	
	private void startSleepState(final SleepState sleepState, final WorkflowInstance workflowInstance) throws Exception {
		final Duration duration = Duration.parse(sleepState.getDuration());
		Thread.currentThread();
		Thread.sleep(duration.getSeconds() * 1000);
		if(sleepState.getTransition() != null) {
			transition(sleepState.getTransition(), workflowInstance);
		}
		else if(sleepState.getEnd() != null) {
			end(sleepState.getEnd(), workflowInstance);
		}
	}
	
	private void startParallelState(final ParallelState parallelState, final WorkflowInstance workflowInstance) throws Exception {
		filterStateDataInput(parallelState.getStateDataFilter(), workflowInstance.getWorkflowData());
		final List<Branch> branches = new ArrayList<>();
		for(ParallelStateBranch branch : parallelState.getBranches()) {
			branches.add(newParallelBranch(branch, workflowInstance));
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
			if(workflowInstance.isTerminate()) {
				return;
			}
			if(parallelState.getTransition() != null) {
				filterStateDataOutput(parallelState.getStateDataFilter(), workflowInstance.getWorkflowData());
				transition(parallelState.getTransition(), workflowInstance);
			}
			else if(parallelState.getEnd() != null) {
				end(parallelState.getEnd(), workflowInstance);
			}
		}
		catch(ExecutionException ex) {
			if(parallelState.getOnErrors() != null && ex.getCause() instanceof WorkflowException) {
				onErrors(parallelState.getOnErrors(), (WorkflowException)ex.getCause(), workflowInstance);
			}
		}
	}
	
	private void startInjectState(final InjectState injectState, final WorkflowInstance workflowInstance) throws Exception {
		filterStateDataInput(injectState.getStateDataFilter(), workflowInstance.getWorkflowData());
		StateUtil.mergeStateDataOutput(workflowInstance.getWorkflowData(), injectState.getData());
		if(injectState.getTransition() != null) {
			filterStateDataOutput(injectState.getStateDataFilter(), workflowInstance.getWorkflowData());
			transition(injectState.getTransition(), workflowInstance);
		}
		else if(injectState.getEnd() != null) {
			end(injectState.getEnd(), workflowInstance);
		}
	}
	
	private Branch newBranch(final ActionDefinition[] actionDefinitions, final WorkflowData workflowData, final WorkflowInstance workflowInstance) {
		final List<Action> actions = new ArrayList<>();
		for(ActionDefinition actionDefinition : actionDefinitions) {
			final Optional<Action> action = newAction(workflowInstance.getWorkflowDefinition(), actionDefinition, workflowData);
			if(action.isPresent()) {
				actions.add(action.get());
			}
		}
		return new Branch(actions.toArray(new Action[0]), workflowData, workflowInstance);
	}
	
	private Branch newForEachBranch(final ForEachState forEachState, final WorkflowInstance workflowInstance, final JsonValue value) {
		final WorkflowData newWorkflowData = new WorkflowData();
		newWorkflowData.setInput(workflowInstance.getWorkflowData().getOutput());
		ELUtil.setValue(forEachState.getIterationParam(), newWorkflowData.getInput(), value);
		return newBranch(forEachState.getActions(), newWorkflowData, workflowInstance);
	}
	
	private Branch newParallelBranch(final ParallelStateBranch branch, final WorkflowInstance workflowInstance) {
		final WorkflowData newWorkflowData = new WorkflowData();
		newWorkflowData.setInput(workflowInstance.getWorkflowData().getOutput());
		return newBranch(branch.getActions(), newWorkflowData, workflowInstance);
	}
	
	private void startForEachState(final ForEachState forEachState, final WorkflowInstance workflowInstance) throws Exception {
		filterStateDataInput(forEachState.getStateDataFilter(), workflowInstance.getWorkflowData());
		final JsonArray inputCollection = ELUtil.getValue(forEachState.getInputCollection(), workflowInstance.getWorkflowData().getInput()).asJsonArray();
		int batchSize = inputCollection.size();
		if(forEachState.getBatchSize() instanceof String) {
			batchSize = Integer.valueOf((String)forEachState.getBatchSize());
		}
		else if(forEachState.getBatchSize() instanceof Integer) {
			batchSize = (int) forEachState.getBatchSize();
		}
		List<Branch> iterations = null;
		try {
			switch(forEachState.getMode()) {
				case parallel:
					int index = 0;
					while(!workflowInstance.isTerminate()) {
						final List<JsonValue> batchInputs = inputCollection.subList(index, Math.min(index + batchSize, inputCollection.size()));
						if(batchInputs.isEmpty()) {
							break;
						}
						final List<Branch> batch = batchInputs.stream().map(value -> newForEachBranch(forEachState, workflowInstance, value)).collect(Collectors.toList());
						executor.invokeAll(batch);
						index += batchInputs.size();
					}
					break;
				case sequential:
					iterations = inputCollection.stream().map(value -> newForEachBranch(forEachState, workflowInstance, value)).collect(Collectors.toList());
					for(Branch forEach : iterations) {
						if(workflowInstance.isTerminate()) {
							return;
						}
						forEach.call();
					}
					break;
				default:
					break;
			}
			if(workflowInstance.isTerminate()) {
				return;
			}
			for(Branch forEach : iterations) {
				for(Action action : forEach.getActions()) {
					filterActionDataOutput(action.getActionDefinition(), forEach.getWorkflowData(), action.getWorkflowData());
				}
			}
			for(Branch forEach : iterations) {
				ELUtil.setValue(forEachState.getOutputCollection(), workflowInstance.getWorkflowData().getOutput(), forEach.getWorkflowData().getOutput());
			}
			if(forEachState.getTransition() != null) {
				filterStateDataOutput(forEachState.getStateDataFilter(), workflowInstance.getWorkflowData());
				transition(forEachState.getTransition(), workflowInstance);
			}
			else if(forEachState.getEnd() != null) {
				end(forEachState.getEnd(), workflowInstance);
			}
		}
		catch(WorkflowException ex) {
			if(forEachState.getOnErrors() != null) {
				onErrors(forEachState.getOnErrors(), ex, workflowInstance);
			}
		}
	}
	
	private void startCallbackState(final CallbackState callbackState, final WorkflowInstance workflowInstance) throws Exception {
		filterStateDataInput(callbackState.getStateDataFilter(), workflowInstance.getWorkflowData());
		try {
			final Optional<Action> action = newAction(workflowInstance.getWorkflowDefinition(), callbackState.getAction(), workflowInstance.getWorkflowData());
			if(action.isPresent()) {
				action.get().call();
			}
			if(workflowInstance.isTerminate()) {
				return;
			}
			callbacks.add(new Callback(callbackState, workflowInstance));
		}
		catch(WorkflowException ex) {
			if(callbackState.getOnErrors() != null) {
				onErrors(callbackState.getOnErrors(), ex, workflowInstance);
			}
		}
	}
	
	private void callback(final CallbackState callbackState, final WorkflowInstance workflowInstance, final EventDefinition eventDefinition, final Event event) throws Exception {
		filterEventData(eventDefinition, callbackState.getEventDataFilter(), workflowInstance.getWorkflowData(), event);
		if(callbackState.getTransition() != null) {
			filterStateDataOutput(callbackState.getStateDataFilter(), workflowInstance.getWorkflowData());
			transition(callbackState.getTransition(), workflowInstance);
		}
		else if(callbackState.getEnd() != null) {
			end(callbackState.getEnd(), workflowInstance);
		}
	}
	
	private void endCondition(final SwitchState switchState, final SwitchStateConditions condition, final WorkflowInstance workflowInstance) throws Exception {
		if(condition.getTransition() != null) {
			filterStateDataOutput(switchState.getStateDataFilter(), workflowInstance.getWorkflowData());
			transition(condition.getTransition(), workflowInstance);
		}
		else if(condition.getEnd() != null) {
			end(condition.getEnd(), workflowInstance);
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
		for(EventAction eventAction : eventActions) {
			final EventDefinition consumeEventDefinition = EventUtil.getEventDefinition(eventAction.getWorkflowDefinition(), eventAction.getActionDefinition().getEventRef().getConsumeEventRef());
			if(isEvent(consumeEventDefinition, event)) {
				eventAction.call();
			}
		}
	}
	
	private void callback(final Event event) throws Exception {
		for(Callback callback : callbacks) {
			if(callback.getWorkflowInstance().getWorkflowDefinition().getEvents() instanceof EventDefinition[]) {
				final EventDefinition[] events = (EventDefinition[]) callback.getWorkflowInstance().getWorkflowDefinition().getEvents();
				final Optional<EventDefinition> eventDefinition = Arrays.asList(events).stream().filter(e -> e.getName().equals(callback.getCallbackState().getEventRef())).findFirst();
				if(eventDefinition.isPresent() && isEvent(eventDefinition.get(), event)) {
					callbacks.remove(callback);
					if(callback.getWorkflowInstance().isTerminate()) {
						continue;
					}
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
				workflowInstance = newWorkflowInstance(onEvents.getWorkflowDefinition(), onEvents.getWorkflowData(), onEvents.getEvents());
			}
			if(workflowInstance.isTerminate()) {
				return;
			}
			try {
				filterStateDataInput(eventState.getStateDataFilter(), workflowInstance.getWorkflowData());
				filterEventData(eventDefinition, onEvent.getEventDataFilter(), workflowInstance.getWorkflowData(), event);
				final Duration actionExecTimeout = TimeoutUtil.getActionExecTimeout(workflowInstance.getWorkflowDefinition(), onEvents.getEventState());
				performActions(onEvent.getActionMode(), onEvent.getActions(), actionExecTimeout, workflowInstance);
				if(workflowInstance.isTerminate()) {
					return;
				}
				if(onEvents.getEventState().getTransition() != null) {
					filterStateDataOutput(eventState.getStateDataFilter(), workflowInstance.getWorkflowData());
					transition(onEvents.getEventState().getTransition(), workflowInstance);
				}
				if(onEvents.getEventState().getEnd() != null) {
					end(onEvents.getEventState().getEnd(), workflowInstance);
				}
			}
			catch(WorkflowException ex) {
				if(onEvents.getEventState().getOnErrors() != null) {
					onErrors(onEvents.getEventState().getOnErrors(), ex, workflowInstance);
				}
			}
		}
	}
	
	private void filterStateDataInput(final StateDataFilters stateDataFilters, final WorkflowData workflowData) {
		if(stateDataFilters != null && stateDataFilters.getInput() != null) {
			final JsonValue newInput = ELUtil.getValue(stateDataFilters.getInput(), workflowData.getInput());
			workflowData.setInput(newInput);
		}
	}
	
	private void filterStateDataOutput(final StateDataFilters stateDataFilters, final WorkflowData workflowData) {
		if(stateDataFilters != null && stateDataFilters.getOutput() != null) {
			final JsonValue newOutput = ELUtil.getValue(stateDataFilters.getOutput(), workflowData.getOutput());
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
					eventData = JsonUtil.toJsonValue(event.getData());
				}
			}
			else {
				eventData = JsonUtil.toJsonValue(event);
			}
			JsonValue data = eventData;
			if(eventDataFilters.getData() != null) {
				data = ELUtil.getValue(eventDataFilters.getData(), eventData);
			}
			if(eventDataFilters.getToStateData() != null) {
				StateUtil.mergeStateDataOutput(eventDataFilters.getToStateData(), WorkflowData, data);
			}
			else {
				StateUtil.mergeStateDataOutput(WorkflowData, data);
			}
		}
	}
	
	private void onErrors(final ErrorDefinition[] onErrors, final WorkflowException exception, final WorkflowInstance workflowInstance) throws Exception {
		for(ErrorDefinition errorDefinition : onErrors) {
			final List<WorkflowError> errors = getErrors(workflowInstance.getWorkflowDefinition(), errorDefinition);
			final Optional<WorkflowError> error = errors.stream().filter(err -> err.equals(exception.getWorkflowError())).findFirst();
			if(error.isPresent()) {
				if(workflowInstance.isTerminate()) {
					return;
				}
				if(errorDefinition.getTransition() != null) {
					transition(errorDefinition.getTransition(), workflowInstance);
				}
				else if(errorDefinition.getEnd() != null) {
					end(errorDefinition.getEnd(), workflowInstance);
				}
				return;
			}
		}
		throw exception;
	}
	
	private void compensateState(final State state, final Map<String, State> states, final WorkflowInstance workflowInstance) throws Exception {
		String compensatedBy = null;
		switch(state.getType()) {
			case event:
				final EventState eventState = (EventState)state;
				compensatedBy = eventState.getCompensatedBy();
				break;
			case operation:
				final OperationState operationState = (OperationState) state;
				compensatedBy = operationState.getCompensatedBy();
				break;
			case Switch:
				final SwitchState switchState = (SwitchState) state;
				compensatedBy = switchState.getCompensatedBy();
				break;
			case sleep:
				break;
			case parallel:
				final ParallelState parallelState = (ParallelState) state;
				compensatedBy = parallelState.getCompensatedBy();
				break;
			case inject:
				final InjectState injectState = (InjectState) state;
				compensatedBy = injectState.getCompensatedBy();
				break;
			case foreach:
				final ForEachState forEachState = (ForEachState) state;
				compensatedBy = forEachState.getCompensatedBy();
				break;
			case callback:
				final CallbackState callbackState = (CallbackState) state;
				compensatedBy = callbackState.getCompensatedBy();
				break;
			default:
				break;
		}
		if(compensatedBy != null) {
			final State compensatedState = states.get(compensatedBy);
			startState(compensatedState, workflowInstance);
		}
	}
	
	private void compensate(final WorkflowInstance workflowInstance) throws Exception {
		final List<State> compensateStates = new ArrayList<>(Arrays.asList(workflowInstance.getStates()));
		Collections.reverse(compensateStates);
		final Map<String, State> states = new HashMap<>();
		MapUtil.putAll(states, workflowInstance.getWorkflowDefinition().getStates(), State::getName);
		for(State compensateState : compensateStates) {
			if(workflowInstance.isTerminate()) {
				return;
			}
			compensateState(compensateState, states, workflowInstance);
		}
	}
	
	private void continueAs(final Object continueAs, final WorkflowData workflowData) throws Exception {
		if(continueAs instanceof String) {
			final WorkflowDefinition workflowDefinition = workflowRepository.find((String)continueAs);
			final WorkflowData newWorkflowData = new WorkflowData();
			newWorkflowData.setInput(workflowData.getOutput());
			start(workflowDefinition, newWorkflowData);
		}
		else if(continueAs instanceof ContinueAs) {
			final ContinueAs continueAsDef = (ContinueAs) continueAs;
			final WorkflowDefinition workflowDefinition = workflowRepository.find(continueAsDef.getWorkflowId(), continueAsDef.getVersion());
			final WorkflowData newWorkflowData = new WorkflowData();
			if(continueAsDef.getData() != null) {
				JsonValue input = null;
				if(continueAsDef.getData() instanceof String) {
					final String data = (String)continueAsDef.getData();
					input = ELUtil.getValue(data, workflowData.getOutput());
				}
				else if(continueAsDef.getData() instanceof JsonValue) {
					input = (JsonValue) continueAsDef.getData();
				}
				else {
					input = JsonUtil.toJsonValue(continueAsDef.getData());
				}
				newWorkflowData.setInput(input);
			}
			else {
				newWorkflowData.setInput(workflowData.getOutput());
			}
			start(workflowDefinition, newWorkflowData);
		}
	}
	
	private void termiateSubFlows(final WorkflowInstance workflowInstance) {
		for(Future<?> subFlow : workflowInstance.getSubFlows()) {
			subFlow.cancel(true);
		}
	}
	
	private void termiate(final WorkflowInstance workflowInstance) {
		workflowInstance.terminate();
		workflowInstances.remove(workflowInstance.getId());
		termiateSubFlows(workflowInstance);
	}
	
	private void end(final Object end, final WorkflowInstance workflowInstance) throws Exception {
		if(end instanceof EndDefinition) {
			final EndDefinition endDefinition = (EndDefinition) end;
			if(endDefinition.isCompensate()) {
				compensate(workflowInstance);
			}
			if(Boolean.TRUE.equals(endDefinition.isTerminate())) {
				termiate(workflowInstance);
				return;
			}
			if(endDefinition.getProduceEvents() != null) {
				produceEvents(workflowInstance.getWorkflowDefinition(), endDefinition.getProduceEvents());
			}
			termiateSubFlows(workflowInstance);
			if(endDefinition.getContinueAs() != null) {
				continueAs(endDefinition.getContinueAs(), workflowInstance.getWorkflowData());
			}
		}
		else {
			termiateSubFlows(workflowInstance);
		}
	}
	
	private void transition(final Object transition, final WorkflowInstance workflowInstance) throws Exception {
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
				compensate(workflowInstance);
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
	
	private void produceEvent(final Map<String, EventDefinition> EventDefinitions, final ProducedEventDefinition producedEventDefinition) throws Exception {
		final EventDefinition eventDefinition = EventDefinitions.get(producedEventDefinition.getEventRef());
		final Event event = new Event();
		event.setSource(new URI(eventDefinition.getSource()));
		event.setType(eventDefinition.getType());
		if(!(producedEventDefinition.getData() instanceof String)) {
			event.setData(producedEventDefinition.getData());
		}
		producedEvent.fire(event);
	}
	
	private void produceEvents(final WorkflowDefinition workflowDefinition, final ProducedEventDefinition[] produceEvents) throws Exception {
		final Map<String, EventDefinition> eventDefinitions = new HashMap<>();
		MapUtil.putAll(eventDefinitions, workflowDefinition.getEvents(), EventDefinition::getName);
		for(ProducedEventDefinition producedEventDefinition : produceEvents) {
			produceEvent(eventDefinitions, producedEventDefinition);
		}
	}
	
	private void filterActionDataInput(final ActionDefinition actionDefinition, final WorkflowData workflowData, final WorkflowData actionData) {
		if(actionDefinition.getActionDataFilter() != null && actionDefinition.getActionDataFilter().getFromStateData() != null) {
			final JsonValue input = ELUtil.getValue(actionDefinition.getActionDataFilter().getFromStateData(), workflowData.getInput());
			actionData.setInput(input);
		}
	}
	
	private void filterActionDataOutput(final ActionDefinition actionDefinition, final WorkflowData workflowData, final WorkflowData actionData) {
		if(actionDefinition.getActionDataFilter() != null && actionDefinition.getActionDataFilter().isUseResults() && actionDefinition.getActionDataFilter().getResults() != null) {
			if(actionDefinition.getActionDataFilter().getToStateData() != null) {
				StateUtil.mergeStateDataOutput(actionDefinition.getActionDataFilter().getToStateData(), workflowData, actionData.getOutput());
			}
			else {
				StateUtil.mergeStateDataOutput(workflowData, actionData.getOutput());
			}
		}
	}
	
	private Optional<Action> newAction(final WorkflowDefinition workflowDefinition, final ActionDefinition actionDefinition, final WorkflowData workflowData) {
		Optional<Action> action = Optional.empty();
		final WorkflowData actionData = new WorkflowData();
		filterActionDataInput(actionDefinition, workflowData, actionData);
		if(actionDefinition.getFunctionRef() != null) {
			action = Optional.of(new FunctionAction(workflowDefinition, actionDefinition, actionData));
		}
		else if(actionDefinition.getEventRef() != null) {
			if(actionDefinition.getEventRef().getConsumeEventRef() != null) {
				eventActions.add(new EventAction(workflowDefinition, actionDefinition, producedEvent, actionData));
			}
			else {
				action = Optional.of(new EventAction(workflowDefinition, actionDefinition, producedEvent, actionData));
			}
		}
		else if(actionDefinition.getSubFlowRef() != null) {
			final WorkflowDefinition subWorkflowDefinition = getSubWorkflowDefinition(actionDefinition.getSubFlowRef());
			SubFlowRefDefinition subFlowRefDefinition = null;
			if(actionDefinition.getSubFlowRef() instanceof SubFlowRefDefinition) {
				subFlowRefDefinition = (SubFlowRefDefinition)actionDefinition.getSubFlowRef();
			}
			action = Optional.of(new SubflowAction(workflowDefinition, actionDefinition, this, subFlowRefDefinition, subWorkflowDefinition, actionData));
		}
		return action;
	}
	
	private void performActions(final Mode mode, final ActionDefinition[] actionDefinitions, final Duration actionExecTimeout, final WorkflowInstance workflowInstance) throws Exception {
		final List<Action> actions = new CopyOnWriteArrayList<>();
		for(ActionDefinition actionDefinition : actionDefinitions) {
			final Optional<Action> action = newAction(workflowInstance.getWorkflowDefinition(), actionDefinition, workflowInstance.getWorkflowData());
			if(action.isPresent()) {
				actions.add(action.get());
			}
		}
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
					if(workflowInstance.isTerminate()) {
						return;
					}
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
			filterActionDataOutput(action.getActionDefinition(), workflowInstance.getWorkflowData(), action.getWorkflowData());
		}
	}
	
	private List<WorkflowError> getErrors(final WorkflowDefinition workflowDefinition, final ErrorDefinition errorDefinition) {
		final WorkflowError[] errors = workflowDefinition.getErrors();
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
		return Arrays.asList();
	}
	
	private WorkflowDefinition getSubWorkflowDefinition(final Object subFlowRef) {
		if(subFlowRef instanceof String) {
			return workflowRepository.find((String)subFlowRef);
		}
		else {
			final SubFlowRefDefinition subFlowRefDefinition = (SubFlowRefDefinition) subFlowRef;
			return workflowRepository.find(subFlowRefDefinition.getWorkflowId(), subFlowRefDefinition.getVersion());
		}
	}
}
