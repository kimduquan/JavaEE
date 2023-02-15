package epf.workflow;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
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
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.util.MapUtil;
import epf.workflow.schema.ActionDefinition;
import epf.workflow.schema.ActionMode;
import epf.workflow.schema.CallbackState;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.ErrorDefinition;
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
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.WorkflowError;
import epf.workflow.schema.State;

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
			startState(workflowDefinition, startState, workflowData);
		}
	}
	
	private void startState(final WorkflowDefinition workflowDefinition, final State state, final WorkflowData workflowData) throws Exception {
		switch(state.getType()) {
			case Event:
				final EventState eventState = (EventState)state;
				startEventState(workflowDefinition, eventState, Instant.now(), workflowData);
				break;
			case Operation:
				final OperationState operationState = (OperationState) state;
				startOperationState(workflowDefinition, operationState, workflowData);
				break;
			case Switch:
				final SwitchState switchState = (SwitchState) state;
				startSwitchState(workflowDefinition, switchState, workflowData);
				break;
			case Sleep:
				final SleepState sleepState = (SleepState) state;
				startSleepState(workflowDefinition, sleepState, workflowData);
				break;
			case Parallel:
				final ParallelState parallelState = (ParallelState) state;
				startParallelState(workflowDefinition, parallelState, workflowData);
				break;
			case Inject:
				final InjectState injectState = (InjectState) state;
				startInjectState(workflowDefinition, injectState, workflowData);
				break;
			case ForEach:
				final ForEachState forEachState = (ForEachState) state;
				startForEachState(workflowDefinition, forEachState, workflowData);
				break;
			case Callback:
				final CallbackState callbackState = (CallbackState) state;
				startCallbackState(workflowDefinition, callbackState, workflowData);
				break;
			default:
				break;
		}
	}
	
	private void startEventState(final WorkflowDefinition workflowDefinition, final EventState eventState, final Instant time, final WorkflowData workflowData) {
		if(workflowDefinition.getEvents() instanceof EventDefinition[]) {
			onEvents.add(new OnEvents(workflowDefinition, eventState, time, workflowData));
		}
	}
	
	private void startOperationState(final WorkflowDefinition workflowDefinition, final OperationState operationState, final WorkflowData workflowData) throws Exception {
		try {
			final Duration actionExecTimeout = Timeouts.getActionExecTimeout(workflowDefinition, operationState);
			performActions(workflowDefinition, operationState.getActionMode(), operationState.getActions(), actionExecTimeout);
			if(operationState.getEnd() != null) {
				end(workflowDefinition, operationState, operationState.getEnd(), workflowData);
			}
			else {
				transition(workflowDefinition, operationState, operationState.getTransition(), workflowData);
			}
		}
		catch(WorkflowException ex) {
			if(operationState.getOnErrors() != null) {
				onErrors(workflowDefinition, operationState, operationState.getOnErrors(), ex, workflowData);
			}
		}
	}
	
	private void startSwitchState(final WorkflowDefinition workflowDefinition, final SwitchState switchState, final WorkflowData workflowData) throws Exception {
		boolean evaluate = false;
		if(switchState.getDataConditions() != null) {
			try {
				for(SwitchStateDataConditions condition : switchState.getDataConditions()) {
					if(evaluateCondition(condition)) {
						endCondition(workflowDefinition, switchState, condition, workflowData);
						evaluate = true;
						break;
					}
				}
			}
			catch(WorkflowException ex) {
				if(switchState.getOnErrors() != null) {
					onErrors(workflowDefinition, switchState, switchState.getOnErrors(), ex, workflowData);
				}
			}
		}
		else if(switchState.getEventConditions() != null) {
			try {
				for(SwitchStateEventConditions condition : switchState.getEventConditions()) {
					if(evaluateCondition(condition)) {
						endCondition(workflowDefinition, switchState, condition, workflowData);
						evaluate = true;
						break;
					}
				}
			}
			catch(WorkflowException ex) {
				if(switchState.getOnErrors() != null) {
					onErrors(workflowDefinition, switchState, switchState.getOnErrors(), ex, workflowData);
				}
			}
		}
		if(!evaluate) {
			if(switchState.getDefaultCondition() instanceof TransitionDefinition) {
				transition(workflowDefinition, switchState, switchState.getDefaultCondition(), workflowData);
			}
			else if(switchState.getDefaultCondition() instanceof EndDefinition) {
				end(workflowDefinition, switchState, switchState.getDefaultCondition(), workflowData);
			}
		}
	}
	
	private void startSleepState(final WorkflowDefinition workflowDefinition, final SleepState sleepState, final WorkflowData workflowData) throws Exception {
		final Duration duration = Duration.parse(sleepState.getDuration());
		Thread.currentThread();
		Thread.sleep(duration.getSeconds() * 1000);
		if(sleepState.getEnd() != null) {
			end(workflowDefinition, sleepState, sleepState.getEnd(), workflowData);
		}
		else if(sleepState.getTransition() != null) {
			transition(workflowDefinition, sleepState, sleepState.getTransition(), workflowData);
		}
	}
	
	private void startParallelState(final WorkflowDefinition workflowDefinition, final ParallelState parallelState, final WorkflowData workflowData) throws Exception {
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
				end(workflowDefinition, parallelState, parallelState.getEnd(), workflowData);
			}
			else if(parallelState.getTransition() != null) {
				transition(workflowDefinition, parallelState, parallelState.getTransition(), workflowData);
			}
		}
		catch(ExecutionException ex) {
			if(parallelState.getOnErrors() != null && ex.getCause() instanceof WorkflowException) {
				onErrors(workflowDefinition, parallelState, parallelState.getOnErrors(), (WorkflowException)ex.getCause(), workflowData);
			}
		}
	}
	
	private void startInjectState(final WorkflowDefinition workflowDefinition, final InjectState injectState, final WorkflowData workflowData) throws Exception {
		if(injectState.getEnd() != null) {
			end(workflowDefinition, injectState, injectState.getEnd(), workflowData);
		}
		else if(injectState.getTransition() != null) {
			transition(workflowDefinition, injectState, injectState.getTransition(), workflowData);
		}
	}
	
	private void startForEachState(final WorkflowDefinition workflowDefinition, final ForEachState forEachState, final WorkflowData workflowData) throws Exception {
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
				end(workflowDefinition, forEachState, forEachState.getEnd(), workflowData);
			}
			else if(forEachState.getTransition() != null) {
				transition(workflowDefinition, forEachState, forEachState.getTransition(), workflowData);
			}
		}
		catch(WorkflowException ex) {
			if(forEachState.getOnErrors() != null) {
				onErrors(workflowDefinition, forEachState, forEachState.getOnErrors(), ex, workflowData);
			}
		}
	}
	
	private void startCallbackState(final WorkflowDefinition workflowDefinition, final CallbackState callbackState, final WorkflowData workflowData) throws Exception {
		try {
			final Action action = new Action(workflowDefinition, callbackState.getAction());
			action.call();
			callbacks.add(new Callback(workflowDefinition, callbackState, workflowData));
		}
		catch(WorkflowException ex) {
			if(callbackState.getOnErrors() != null) {
				onErrors(workflowDefinition, callbackState, callbackState.getOnErrors(), ex, workflowData);
			}
		}
	}
	
	private void callback(final WorkflowDefinition workflowDefinition, final CallbackState callbackState, final WorkflowData workflowData) throws Exception {
		if(callbackState.getEnd() != null) {
			end(workflowDefinition, callbackState, callbackState.getEnd(), workflowData);
		}
		else if(callbackState.getTransition() != null) {
			transition(workflowDefinition, callbackState, callbackState.getTransition(), workflowData);
		}
	}
	
	private void endCondition(final WorkflowDefinition workflowDefinition, final SwitchState switchState, final SwitchStateConditions condition, final WorkflowData workflowData) throws Exception {
		if(condition.getEnd() != null) {
			end(workflowDefinition, switchState, condition.getEnd(), workflowData);
		}
		else if(condition.getTransition() != null) {
			transition(workflowDefinition, switchState, condition.getTransition(), workflowData);
		}
	}
	
	private boolean evaluateCondition(final SwitchStateDataConditions dataConditions) {
		return dataConditions.getCondition() != null;
	}
	
	private boolean evaluateCondition(final SwitchStateEventConditions eventConditions) {
		return eventConditions.getEventRef() != null;
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
		final List<Callback> calls = new ArrayList<>();
		for(Callback callback : callbacks) {
			if(callback.getWorkflowDefinition().getEvents() instanceof EventDefinition[]) {
				final EventDefinition[] events = (EventDefinition[]) callback.getWorkflowDefinition().getEvents();
				final Optional<EventDefinition> eventDefinition = Arrays.asList(events).stream().filter(e -> e.getName().equals(callback.getCallbackState().getEventRef())).findFirst();
				if(eventDefinition.isPresent() && isEvent(eventDefinition.get(), event)) {
					calls.add(callback);
				}
			}
		}
		callbacks.removeAll(calls);
		for(Callback callback : calls) {
			callback(callback.getWorkflowDefinition(), callback.getCallbackState(), callback.getWorkflowData());
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
		for(EventDefinition eventRef : eventRefs) {
			if(isEvent(eventRef, event)) {
				if(eventState.isExclusive()) {
					isEvent = true;
					break;
				}
				onEvents.addEvent(event);
			}
		}
		if(!isEvent) {
			isEvent = true;
			for(EventDefinition eventRef : eventRefs) {
				if(isEvent) {
					for(Event ev : onEvents.getEvents()) {
						if(!isEvent(eventRef, ev)) {
							isEvent = false;
							break;
						}
					}
				}
			}
		}
		if(isEvent) {
			try {
				final Duration actionExecTimeout = Timeouts.getActionExecTimeout(onEvents.getWorkflowDefinition(), onEvents.getEventState());
				performActions(onEvents.getWorkflowDefinition(), onEvent.getActionMode(), onEvent.getActions(), actionExecTimeout);
				if(onEvents.getEventState().getEnd() != null) {
					end(onEvents.getWorkflowDefinition(), onEvents.getEventState(), onEvents.getEventState().getEnd(), onEvents.getWorkflowData());
				}
				else {
					transition(onEvents.getWorkflowDefinition(), onEvents.getEventState(), onEvents.getEventState().getTransition(), onEvents.getWorkflowData());
				}
			}
			catch(WorkflowException ex) {
				if(onEvents.getEventState().getOnErrors() != null) {
					onErrors(onEvents.getWorkflowDefinition(), onEvents.getEventState(), onEvents.getEventState().getOnErrors(), ex, onEvents.getWorkflowData());
				}
			}
		}
	}
	
	private void onErrors(final WorkflowDefinition workflowDefinition, final State state, final ErrorDefinition[] onErrors, final WorkflowException exception, final WorkflowData workflowData) throws Exception {
		for(ErrorDefinition errorDefinition : onErrors) {
			final List<WorkflowError> errors = getErrors(workflowDefinition, errorDefinition);
			final Optional<WorkflowError> error = errors.stream().filter(err -> err.equals(exception.getWorkflowError())).findFirst();
			if(error.isPresent()) {
				if(errorDefinition.getEnd() != null) {
					end(workflowDefinition, state, errorDefinition.getEnd(), workflowData);
				}
				else if(errorDefinition.getTransition() != null) {
					transition(workflowDefinition, state, errorDefinition.getTransition(), workflowData);
				}
				return;
			}
		}
		throw exception;
	}
	
	private void compensate(final WorkflowDefinition workflowDefinition, final State state, final WorkflowData workflowData) throws Exception {
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
			for(State compensatedByState : workflowDefinition.getStates()) {
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
						startState(workflowDefinition, state, workflowData);
					}
					break;
				}
			}
		}
	}
	
	private void end(final WorkflowDefinition workflowDefinition, final State state, final Object end, final WorkflowData workflowData) throws Exception {
		if(end instanceof EndDefinition) {
			final EndDefinition endDefinition = (EndDefinition) end;
			if(endDefinition.isCompensate()) {
				compensate(workflowDefinition, state, workflowData);
			}
			if(endDefinition.isTerminate()) {
				return;
			}
			if(endDefinition.getProduceEvents() != null) {
				produceEvents(workflowDefinition, endDefinition.getProduceEvents());
			}
		}
	}
	
	private void transition(final WorkflowDefinition workflowDefinition, final State state, final Object transition, final WorkflowData workflowData) throws Exception {
		if(transition instanceof String) {
			for(State nextState : workflowDefinition.getStates()) {
				if(nextState.getName().equals((String)transition)) {
					startState(workflowDefinition, nextState, workflowData);
					return;
				}
			}
		}
		else if(transition instanceof TransitionDefinition) {
			final TransitionDefinition transitionDef = (TransitionDefinition) transition;
			if(transitionDef.isCompensate()) {
				compensate(workflowDefinition, state, workflowData);
			}
			produceEvents(workflowDefinition, transitionDef.getProduceEvents());
			for(State nextState : workflowDefinition.getStates()) {
				if(nextState.getName().equals(transitionDef.getNextState())) {
					startState(workflowDefinition, nextState, workflowData);
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
	
	private void performActions(final WorkflowDefinition workflowDefinition, final ActionMode mode, final ActionDefinition[] actionDefs, final Duration actionExecTimeout) throws Exception {
		final List<Action> actions = Arrays.asList(actionDefs).stream().map(actionDef -> new Action(workflowDefinition, actionDef)).collect(Collectors.toList());
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
