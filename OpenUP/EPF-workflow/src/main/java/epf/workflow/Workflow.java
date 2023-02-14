package epf.workflow;

import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.api.API;
import epf.api.Operation;
import epf.api.PathItem;
import epf.util.ListUtil;
import epf.util.MapUtil;
import epf.workflow.schema.ActionDefinition;
import epf.workflow.schema.ActionMode;
import epf.workflow.schema.CallbackState;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.ErrorDefinition;
import epf.workflow.schema.EventDefinition;
import epf.workflow.schema.EventState;
import epf.workflow.schema.ForEachState;
import epf.workflow.schema.FunctionDefinition;
import epf.workflow.schema.FunctionRefDefinition;
import epf.workflow.schema.InjectState;
import epf.workflow.schema.OnEventsDefinition;
import epf.workflow.schema.OperationState;
import epf.workflow.schema.ParallelState;
import epf.workflow.schema.ParallelStateBranch;
import epf.workflow.schema.ProducedEventDefinition;
import epf.workflow.schema.RetryDefinition;
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
		if(workflowDefinition.getStart() != null) {
			if(workflowDefinition.getStart() instanceof String) {
				final String start = (String)workflowDefinition.getStart();
				for(State state : workflowDefinition.getStates()) {
					if(start.equals(state.getName())) {
						startState(workflowDefinition, state, workflowData);
						break;
					}
				}
			}
			else if(workflowDefinition.getStart() instanceof StartDefinition) {
				final StartDefinition startDef = (StartDefinition) workflowDefinition.getStart();
				for(State state : workflowDefinition.getStates()) {
					if(startDef.getStateName().equals(state.getName())) {
						startState(workflowDefinition, state, workflowData);
						break;
					}
				}
			}
		}
		else {
			startState(workflowDefinition, workflowDefinition.getStates()[0], workflowData);
		}
	}
	
	private void startState(final WorkflowDefinition workflowDefinition, final State state, final WorkflowData workflowData) throws Exception {
		switch(state.getType()) {
			case State.Event:
				final EventState eventState = (EventState)state;
				startEventState(workflowDefinition, eventState, workflowData);
				break;
			case State.Operation:
				final OperationState operationState = (OperationState) state;
				startOperationState(workflowDefinition, operationState, workflowData);
				break;
			case State.Switch:
				final SwitchState switchState = (SwitchState) state;
				startSwitchState(workflowDefinition, switchState, workflowData);
				break;
			case State.Sleep:
				final SleepState sleepState = (SleepState) state;
				startSleepState(workflowDefinition, sleepState, workflowData);
				break;
			case State.Parallel:
				final ParallelState parallelState = (ParallelState) state;
				startParallelState(workflowDefinition, parallelState, workflowData);
				break;
			case State.Inject:
				final InjectState injectState = (InjectState) state;
				startInjectState(workflowDefinition, injectState, workflowData);
				break;
			case State.ForEach:
				final ForEachState forEachState = (ForEachState) state;
				startForEachState(workflowDefinition, forEachState, workflowData);
				break;
			case State.Callback:
				final CallbackState callbackState = (CallbackState) state;
				startCallbackState(workflowDefinition, callbackState, workflowData);
				break;
			default:
				break;
		}
	}
	
	private void startEventState(final WorkflowDefinition workflowDefinition, final EventState eventState, final WorkflowData workflowData) {
		if(!(workflowDefinition.getEvents() instanceof String)) {
			onEvents.add(new OnEvents(workflowDefinition, eventState, workflowData));
		}
	}
	
	private void startOperationState(final WorkflowDefinition workflowDefinition, final OperationState operationState, final WorkflowData workflowData) throws Exception {
		try {
			performActions(workflowDefinition, operationState.getActionMode(), operationState.getActions(), workflowData);
			if(operationState.getEnd() != null) {
				end(workflowDefinition, operationState.getEnd());
			}
			else {
				transition(workflowDefinition, operationState.getTransition(), workflowData);
			}
		}
		catch(WorkflowException ex) {
			if(operationState.getOnErrors() != null) {
				onErrors(workflowDefinition, operationState.getOnErrors(), ex, workflowData);
			}
		}
	}
	
	private void startSwitchState(final WorkflowDefinition workflowDefinition, final SwitchState switchState, final WorkflowData workflowData) throws Exception {
		boolean evaluate = false;
		if(switchState.getDataConditions() != null) {
			try {
				for(SwitchStateDataConditions condition : switchState.getDataConditions()) {
					if(evaluateCondition(condition)) {
						endCondition(workflowDefinition, condition, workflowData);
						evaluate = true;
						break;
					}
				}
			}
			catch(WorkflowException ex) {
				if(switchState.getOnErrors() != null) {
					onErrors(workflowDefinition, switchState.getOnErrors(), ex, workflowData);
				}
			}
		}
		else if(switchState.getEventConditions() != null) {
			try {
				for(SwitchStateEventConditions condition : switchState.getEventConditions()) {
					if(evaluateCondition(condition)) {
						endCondition(workflowDefinition, condition, workflowData);
						evaluate = true;
						break;
					}
				}
			}
			catch(WorkflowException ex) {
				if(switchState.getOnErrors() != null) {
					onErrors(workflowDefinition, switchState.getOnErrors(), ex, workflowData);
				}
			}
		}
		if(!evaluate) {
			if(switchState.getDefaultCondition() instanceof TransitionDefinition) {
				transition(workflowDefinition, switchState.getDefaultCondition(), workflowData);
			}
			else if(switchState.getDefaultCondition() instanceof EndDefinition) {
				end(workflowDefinition, switchState.getDefaultCondition());
			}
		}
	}
	
	private void startSleepState(final WorkflowDefinition workflowDefinition, final SleepState sleepState, final WorkflowData workflowData) throws Exception {
		final Duration duration = Duration.parse(sleepState.getDuration());
		Thread.currentThread();
		Thread.sleep(duration.getSeconds() * 1000);
		if(sleepState.getEnd() != null) {
			end(workflowDefinition, sleepState.getEnd());
		}
		else if(sleepState.getTransition() != null) {
			transition(workflowDefinition, sleepState.getTransition(), workflowData);
		}
	}
	
	private void startParallelState(final WorkflowDefinition workflowDefinition, final ParallelState parallelState, final WorkflowData workflowData) throws Exception {
		final List<Branch> branches = new ArrayList<>();
		for(ParallelStateBranch branch : parallelState.getBranches()) {
			branches.add(new Branch(branch));
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
				end(workflowDefinition, parallelState.getEnd());
			}
			else if(parallelState.getTransition() != null) {
				transition(workflowDefinition, parallelState.getTransition(), workflowData);
			}
		}
		catch(ExecutionException ex) {
			if(parallelState.getOnErrors() != null && ex.getCause() instanceof WorkflowException) {
				onErrors(workflowDefinition, parallelState.getOnErrors(), (WorkflowException)ex.getCause(), workflowData);
			}
		}
	}
	
	private void startInjectState(final WorkflowDefinition workflowDefinition, final InjectState injectState, final WorkflowData workflowData) throws Exception {
		if(injectState.getEnd() != null) {
			end(workflowDefinition, injectState.getEnd());
		}
		else if(injectState.getTransition() != null) {
			transition(workflowDefinition, injectState.getTransition(), workflowData);
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
				end(workflowDefinition, forEachState.getEnd());
			}
			else if(forEachState.getTransition() != null) {
				transition(workflowDefinition, forEachState.getTransition(), workflowData);
			}
		}
		catch(WorkflowException ex) {
			if(forEachState.getOnErrors() != null) {
				onErrors(workflowDefinition, forEachState.getOnErrors(), ex, workflowData);
			}
		}
	}
	
	private void startCallbackState(final WorkflowDefinition workflowDefinition, final CallbackState callbackState, final WorkflowData workflowData) throws Exception {
		try {
			performAction(workflowDefinition, callbackState.getAction(), workflowData);
			callbacks.add(new Callback(workflowDefinition, callbackState, workflowData));
		}
		catch(WorkflowException ex) {
			if(callbackState.getOnErrors() != null) {
				onErrors(workflowDefinition, callbackState.getOnErrors(), ex, workflowData);
			}
		}
	}
	
	private void callback(final WorkflowDefinition workflowDefinition, final CallbackState callbackState, final WorkflowData workflowData) throws Exception {
		if(callbackState.getEnd() != null) {
			end(workflowDefinition, callbackState.getEnd());
		}
		else if(callbackState.getTransition() != null) {
			transition(workflowDefinition, callbackState.getTransition(), workflowData);
		}
	}
	
	private void endCondition(final WorkflowDefinition workflowDefinition, final SwitchStateConditions condition, final WorkflowData workflowData) throws Exception {
		if(condition.getEnd() != null) {
			end(workflowDefinition, condition.getEnd());
		}
		else if(condition.getTransition() != null) {
			transition(workflowDefinition, condition.getTransition(), workflowData);
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
		for(OnEventsDefinition onEvent : eventState.getOnEvents()) {
			final List<EventDefinition> eventRefs = MapUtil.getAll(eventDefs, onEvent.getEventRefs());
			onEvents(onEvents, onEvent, eventRefs, eventState, event);
		}
	}
	
	private static boolean isEvent(final EventDefinition eventDefinition, final Event event) {
		return eventDefinition.getSource().equals(event.getSource().toString()) && eventDefinition.getType().equals(event.getType());
	}
	
	private void onEvents(final OnEvents onEvents, final OnEventsDefinition onEvent, List<EventDefinition> eventRefs, final EventState eventState, final Event event) throws Exception {
		for(EventDefinition eventRef : eventRefs) {
			if(isEvent(eventRef, event) && eventState.isExclusive()) {
				try {
					performActions(onEvents.getWorkflowDefinition(), onEvent.getActionMode(), onEvent.getActions(), onEvents.getWorkflowData());
					if(onEvents.getEventState().getEnd() != null) {
						end(onEvents.getWorkflowDefinition(), onEvents.getEventState().getEnd());
					}
					else {
						transition(onEvents.getWorkflowDefinition(), onEvents.getEventState().getTransition(), onEvents.getWorkflowData());
					}
				}
				catch(WorkflowException ex) {
					if(onEvents.getEventState().getOnErrors() != null) {
						onErrors(onEvents.getWorkflowDefinition(), onEvents.getEventState().getOnErrors(), ex, onEvents.getWorkflowData());
					}
				}
				break;
			}
		}
	}
	
	private void onErrors(final WorkflowDefinition workflowDefinition, final ErrorDefinition[] onErrors, final WorkflowException exception, final WorkflowData workflowData) throws Exception {
		for(ErrorDefinition errorDefinition : onErrors) {
			final List<WorkflowError> errors = getErrors(workflowDefinition, errorDefinition);
			final Optional<WorkflowError> error = errors.stream().filter(err -> err.equals(exception.getWorkflowError())).findFirst();
			if(error.isPresent()) {
				if(errorDefinition.getEnd() != null) {
					end(workflowDefinition, errorDefinition.getEnd());
				}
				else if(errorDefinition.getTransition() != null) {
					transition(workflowDefinition, errorDefinition.getTransition(), workflowData);
				}
				return;
			}
		}
	}
	
	private void end(final WorkflowDefinition workflowDefinition, final Object end) throws Exception {
		if(end instanceof EndDefinition) {
			final EndDefinition endDefinition = (EndDefinition) end;
			if(endDefinition.isTerminate()) {
				return;
			}
			if(endDefinition.getProduceEvents() != null) {
				produceEvents(workflowDefinition, endDefinition.getProduceEvents());
			}
		}
	}
	
	private void transition(final WorkflowDefinition workflowDefinition, final Object transition, final WorkflowData workflowData) throws Exception {
		if(transition instanceof String) {
			for(State state : workflowDefinition.getStates()) {
				if(state.getName().equals((String)transition)) {
					startState(workflowDefinition, state, workflowData);
					return;
				}
			}
		}
		else if(transition instanceof TransitionDefinition) {
			final TransitionDefinition transitionDef = (TransitionDefinition) transition;
			produceEvents(workflowDefinition, transitionDef.getProduceEvents());
			for(State state : workflowDefinition.getStates()) {
				if(state.getName().equals(transitionDef.getNextState())) {
					startState(workflowDefinition, state, workflowData);
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
	
	private void performActions(final WorkflowDefinition workflowDefinition, final ActionMode mode, final ActionDefinition[] actions, final WorkflowData workflowData) throws Exception {
		switch(mode) {
			case parallel:
				break;
			case sequential:
				for(ActionDefinition actionDef : actions) {
					performAction(workflowDefinition, actionDef, workflowData);
				}
				break;
			default:
				break;
			
		}
	}
	
	private void performAction(final WorkflowDefinition workflowDefinition, final ActionDefinition actionDef, final WorkflowData workflowData) throws Exception {
		if(actionDef.getCondition() == null && actionDef.getFunctionRef() != null) {
			if(actionDef.getFunctionRef() instanceof String) {
				final String functionRef = (String) actionDef.getFunctionRef();
				final Optional<FunctionDefinition> functionDef = getFunctionDefinition(workflowDefinition, functionRef);
				if(functionDef.isPresent()) {
					if(workflowDefinition.isAutoRetries()) {
						Optional<RetryDefinition> retryDefinition = Optional.empty();
						try {
							invokeFunction(workflowDefinition, functionDef.get(), null);
						}
						catch(WorkflowException ex) {
							if(actionDef.getNonRetryableErrors() != null) {
								final Optional<WorkflowError> nonRetryableError = Arrays.asList(actionDef.getNonRetryableErrors()).stream().filter(err -> err.equals(ex.getWorkflowError())).findFirst();
								if(!nonRetryableError.isPresent() && actionDef.getRetryRef() != null) {
									retryDefinition = getRetryDefinition(workflowDefinition, actionDef.getRetryRef());
								}
							}
						}
					}
					else {
						invokeFunction(workflowDefinition, functionDef.get(), null);
					}
				}
			}
			else if(actionDef.getFunctionRef() instanceof FunctionRefDefinition) {
				final FunctionRefDefinition functionRef = (FunctionRefDefinition) actionDef.getFunctionRef();
				final Optional<FunctionDefinition> functionDef = getFunctionDefinition(workflowDefinition, functionRef.getRefName());
				if(functionDef.isPresent()) {
					if(workflowDefinition.isAutoRetries()) {
						try {
							invokeFunction(workflowDefinition, functionDef.get(), functionRef);
						}
						catch(Exception ex) {
							
						}
					}
					else {
						invokeFunction(workflowDefinition, functionDef.get(), null);
					}
				}
			}
		}
	}
	
	private Optional<FunctionDefinition> getFunctionDefinition(final WorkflowDefinition workflowDefinition, final String functionRef) {
		if(!(workflowDefinition.getFunctions() instanceof String)) {
			final FunctionDefinition[] functionDefs = (FunctionDefinition[]) workflowDefinition.getFunctions();
			return ListUtil.findFirst(functionDefs, f -> f.getName().equals(functionRef));
		}
		return Optional.empty();
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
	
	private Optional<RetryDefinition> getRetryDefinition(final WorkflowDefinition workflowDefinition, final String retryRef) {
		if(workflowDefinition.getRetries() instanceof RetryDefinition[]) {
			return Arrays.asList((RetryDefinition[])workflowDefinition.getRetries()).stream().filter(retry -> retry.getName().equals(retryRef)).findFirst();
		}
		return Optional.empty();
	}
	
	private void invokeFunction(final WorkflowDefinition workflowDefinition, final FunctionDefinition functionDef, final FunctionRefDefinition functionRef) throws Exception {
		if(functionDef.getType() == Type.rest) {
			final int index = functionDef.getOperation().lastIndexOf("#");
			final String uri = functionDef.getOperation().substring(0, index);
			final String operationId = functionDef.getOperation().substring(index + 1);
			final Client client = ClientBuilder.newClient();
			final API api = client.target(uri).request().accept(MediaType.APPLICATION_JSON).get(API.class);
			for(Entry<String, PathItem> entry : api.getPaths().getPathItems().entrySet()) {
				final PathItem pathItem = entry.getValue();
				final Optional<Operation> operation = pathItem.getOperations().values().stream().filter(o -> o.getOperationId().equals(operationId)).findFirst();
				if(operation.isPresent()) {
					final RestFunction restFunction = new RestFunction(workflowDefinition, functionDef, api, entry.getKey(), pathItem, operation.get(), functionRef);
					restFunction.invoke();
					break;
				}
			}
			client.close();
		}
	}
}
