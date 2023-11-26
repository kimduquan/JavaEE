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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.util.MapUtil;
import epf.util.json.ext.JsonUtil;
import epf.util.logging.LogManager;
import epf.workflow.action.Action;
import epf.workflow.action.SubflowAction;
import epf.workflow.event.Event;
import epf.workflow.event.ProduceEventAction;
import epf.workflow.event.persistence.CallbackStateEvent;
import epf.workflow.event.persistence.EventStateActionEvent;
import epf.workflow.event.persistence.EventStateEvent;
import epf.workflow.function.FunctionAction;
import epf.workflow.schema.ContinueAs;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.ErrorDefinition;
import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.WorkflowData;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.WorkflowError;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.action.Mode;
import epf.workflow.schema.event.EventDataFilters;
import epf.workflow.schema.event.EventDefinition;
import epf.workflow.schema.event.OnEventsDefinition;
import epf.workflow.schema.event.ProducedEventDefinition;
import epf.workflow.schema.state.CallbackState;
import epf.workflow.schema.state.EventState;
import epf.workflow.schema.state.ForEachState;
import epf.workflow.schema.state.InjectState;
import epf.workflow.schema.state.OperationState;
import epf.workflow.schema.state.ParallelState;
import epf.workflow.schema.state.ParallelStateBranch;
import epf.workflow.schema.state.SleepState;
import epf.workflow.schema.state.State;
import epf.workflow.schema.state.StateDataFilters;
import epf.workflow.schema.state.SwitchState;
import epf.workflow.schema.state.SwitchStateConditions;
import epf.workflow.schema.state.SwitchStateDataConditions;
import epf.workflow.schema.state.SwitchStateEventConditions;
import epf.workflow.schema.state.Type;
import epf.workflow.schema.util.Either;
import epf.workflow.state.Branch;
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
	private transient static final Logger LOGGER = LogManager.getLogger(WorkflowRuntime.class.getName());
	
	/**
	 * 
	 */
	@Inject
	transient jakarta.enterprise.event.Event<Event> producedEvent;
	
	/**
	 * 
	 */
	@Inject
	transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowPersistence workflowPersistence;
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowEventStore workflowEventStore;

	/**
	 * @param workflowDefinition
	 * @param workflowDataInput
	 * @throws Exception
	 */
	public void start(final WorkflowDefinition workflowDefinition, final WorkflowData workflowData, final URI uri) throws Exception {
		String startState = null;
		if(workflowDefinition.getStart() != null) {
			if(workflowDefinition.getStart().isLeft()) {
				startState = workflowDefinition.getStart().getLeft();
			}
			else if(workflowDefinition.getStart().isRight()) {
				final StartDefinition startDef = workflowDefinition.getStart().getRight();
				startState = startDef.getStateName();
			}
		}
		else {
			startState = workflowDefinition.getStates().get(0).getName();
		}
		if(startState != null) {
			transitionState(workflowDefinition, workflowData, uri, startState);
		}
	}
	
	public void transitionState(final WorkflowDefinition workflowDefinition, final WorkflowData workflowData, final URI uri, final String stateName) throws Exception {
		State state = getState(workflowDefinition, stateName);
		if(state != null) {
			final WorkflowInstance workflowInstance = newWorkflowInstance(workflowDefinition, workflowData, uri);
			workflowInstance.start(state);
			workflowPersistence.merge(workflowInstance);
			transitionState(workflowDefinition, state, workflowInstance);
		}
	}
	
	private WorkflowInstance newWorkflowInstance(final WorkflowDefinition workflowDefinition, final WorkflowData workflowData, final URI uri) {
		final WorkflowInstance workflowInstance = new WorkflowInstance();
		workflowInstance.setWorkflowDefinition(workflowDefinition.getId());
		workflowInstance.setWorkflowData(workflowData);
		workflowInstance.setUri(uri);
		return workflowPersistence.persist(workflowInstance);
	}
	
	private void transitionState(final WorkflowDefinition workflowDefinition, final State state, final WorkflowInstance workflowInstance) throws Exception {
		if(workflowInstance.isTerminate()) {
			return;
		}
		switch(state.getType_()) {
			case event:
				final EventState eventState = (EventState)state;
				transitionEventState(workflowDefinition, eventState, workflowInstance);
				break;
			case operation:
				final OperationState operationState = (OperationState) state;
				transitionOperationState(workflowDefinition, operationState, workflowInstance);
				break;
			case Switch:
				final SwitchState switchState = (SwitchState) state;
				transitionSwitchState(workflowDefinition, switchState, workflowInstance);
				break;
			case sleep:
				final SleepState sleepState = (SleepState) state;
				transitionSleepState(workflowDefinition, sleepState, workflowInstance);
				break;
			case parallel:
				final ParallelState parallelState = (ParallelState) state;
				transitionParallelState(workflowDefinition, parallelState, workflowInstance);
				break;
			case inject:
				final InjectState injectState = (InjectState) state;
				transitionInjectState(workflowDefinition, injectState, workflowInstance);
				break;
			case foreach:
				final ForEachState forEachState = (ForEachState) state;
				transitionForEachState(workflowDefinition, forEachState, workflowInstance);
				break;
			case callback:
				final CallbackState callbackState = (CallbackState) state;
				transitionCallbackState(workflowDefinition, callbackState, workflowInstance);
				break;
			default:
				break;
		}
	}
	
	private void transitionEventState(final WorkflowDefinition workflowDefinition, final EventState eventState, final WorkflowInstance workflowInstance) {
		Map<String, EventDefinition> eventDefs = new HashMap<>();
		MapUtil.putAll(eventDefs, workflowDefinition.getEvents().iterator(), EventDefinition::getName);
		int index = 0;
		for(OnEventsDefinition onEventsDef : eventState.getOnEvents()) {
			for(String eventRef : onEventsDef.getEventRefs()) {
				final EventDefinition eventDef = eventDefs.get(eventRef);
				
				final EventStateEvent event = new EventStateEvent();
				event.setWorkflowDefinition(workflowDefinition.getId());
				event.setEventDefinition(eventRef);
				event.setState(eventState.getName());
				event.setOnEventsDefinition(index);
				
				event.setSource(eventDef.getSource());
				event.setType(eventDef.getType());
				event.setSubject(workflowInstance.getId());
				
				workflowEventStore.persist(event);
			}
			index++;
		}
	}
	
	private void transitionOperationState(final WorkflowDefinition workflowDefinition, final OperationState operationState, final WorkflowInstance workflowInstance) throws Exception {
		try {
			filterStateDataInput(operationState.getStateDataFilter(), workflowInstance.getWorkflowData());
			final Duration actionExecTimeout = TimeoutUtil.getActionExecTimeout(workflowDefinition, operationState);
			performActions(workflowDefinition, operationState.getActionMode(), operationState.getActions(), actionExecTimeout, workflowInstance);
			if(workflowInstance.isTerminate()) {
				return;
			}
			if(operationState.getTransition() != null) {
				filterStateDataOutput(operationState.getStateDataFilter(), workflowInstance.getWorkflowData());
				transition(workflowDefinition, operationState.getTransition(), workflowInstance);
			}
			else if(operationState.getEnd() != null) {
				end(workflowDefinition, operationState.getEnd(), workflowInstance);
			}
		}
		catch(WorkflowException ex) {
			if(operationState.getOnErrors() != null) {
				onErrors(workflowDefinition, operationState.getOnErrors(), ex, workflowInstance);
			}
		}
	}
	
	private void transitionSwitchState(final WorkflowDefinition workflowDefinition, final SwitchState switchState, final WorkflowInstance workflowInstance) throws Exception {
		filterStateDataInput(switchState.getStateDataFilter(), workflowInstance.getWorkflowData());
		boolean evaluate = false;
		if(switchState.getDataConditions() != null) {
			try {
				for(SwitchStateDataConditions condition : switchState.getDataConditions()) {
					if(workflowInstance.isTerminate()) {
						return;
					}
					if(ELUtil.evaluateCondition(workflowInstance.getWorkflowData().getInput(), condition.getCondition())) {
						endCondition(workflowDefinition, switchState, condition, workflowInstance);
						evaluate = true;
						break;
					}
				}
			}
			catch(WorkflowException ex) {
				if(switchState.getOnErrors() != null) {
					onErrors(workflowDefinition, switchState.getOnErrors(), ex, workflowInstance);
				}
			}
		}
		else if(switchState.getEventConditions() != null) {
			try {
				final Map<String, EventDefinition> eventDefinitions = new HashMap<>();
				MapUtil.putAll(eventDefinitions, workflowDefinition.getEvents().iterator(), EventDefinition::getName);
				for(SwitchStateEventConditions condition : switchState.getEventConditions()) {
					if(workflowInstance.isTerminate()) {
						return;
					}
					final EventDefinition eventDefinition = eventDefinitions.get(condition.getEventRef());
					boolean isEvent = false;
					isEvent = eventDefinition != null;
					if(isEvent) {
						evaluate = true;
						endCondition(workflowDefinition, switchState, condition, workflowInstance);
						break;
					}
				}
			}
			catch(WorkflowException ex) {
				if(switchState.getOnErrors() != null) {
					onErrors(workflowDefinition, switchState.getOnErrors(), ex, workflowInstance);
				}
			}
		}
		if(workflowInstance.isTerminate()) {
			return;
		}
		if(!evaluate) {
			if(switchState.getDefaultCondition().isLeft()) {
				filterStateDataOutput(switchState.getStateDataFilter(), workflowInstance.getWorkflowData());
				transition(workflowDefinition, switchState.getDefaultCondition().getLeft(), workflowInstance);
			}
			else if(switchState.getDefaultCondition().isRight()) {
				end(workflowDefinition, switchState.getDefaultCondition().getRight(), workflowInstance);
			}
		}
	}
	
	private void transitionSleepState(final WorkflowDefinition workflowDefinition, final SleepState sleepState, final WorkflowInstance workflowInstance) throws Exception {
		final Duration duration = Duration.parse(sleepState.getDuration());
		Thread.currentThread();
		Thread.sleep(duration.getSeconds() * 1000);
		if(sleepState.getTransition() != null) {
			transition(workflowDefinition, sleepState.getTransition(), workflowInstance);
		}
		else if(sleepState.getEnd() != null) {
			end(workflowDefinition, sleepState.getEnd(), workflowInstance);
		}
	}
	
	private void transitionParallelState(final WorkflowDefinition workflowDefinition, final ParallelState parallelState, final WorkflowInstance workflowInstance) throws Exception {
		filterStateDataInput(parallelState.getStateDataFilter(), workflowInstance.getWorkflowData());
		final List<Branch> branches = new ArrayList<>();
		for(ParallelStateBranch branch : parallelState.getBranches()) {
			branches.add(newParallelBranch(workflowDefinition, branch, workflowInstance));
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
				transition(workflowDefinition, parallelState.getTransition(), workflowInstance);
			}
			else if(parallelState.getEnd() != null) {
				end(workflowDefinition, parallelState.getEnd(), workflowInstance);
			}
		}
		catch(ExecutionException ex) {
			if(parallelState.getOnErrors() != null && ex.getCause() instanceof WorkflowException) {
				onErrors(workflowDefinition, parallelState.getOnErrors(), (WorkflowException)ex.getCause(), workflowInstance);
			}
		}
	}
	
	private void transitionInjectState(final WorkflowDefinition workflowDefinition, final InjectState injectState, final WorkflowInstance workflowInstance) throws Exception {
		filterStateDataInput(injectState.getStateDataFilter(), workflowInstance.getWorkflowData());
		StateUtil.mergeStateDataOutput(workflowInstance.getWorkflowData(), injectState.getData());
		if(injectState.getTransition() != null) {
			filterStateDataOutput(injectState.getStateDataFilter(), workflowInstance.getWorkflowData());
			transition(workflowDefinition, injectState.getTransition(), workflowInstance);
		}
		else if(injectState.getEnd() != null) {
			end(workflowDefinition, injectState.getEnd(), workflowInstance);
		}
	}
	
	private Branch newBranch(final WorkflowDefinition workflowDefinition, final List<ActionDefinition> actionDefinitions, final WorkflowData workflowData, final WorkflowInstance workflowInstance) throws Exception {
		final List<Action> actions = new ArrayList<>();
		for(ActionDefinition actionDefinition : actionDefinitions) {
			final Action action = newAction(workflowDefinition, actionDefinition, workflowData, workflowInstance.getUri());
			actions.add(action);
		}
		return new Branch(actions.toArray(new Action[0]), workflowData, workflowInstance);
	}
	
	private Branch newForEachBranch(final WorkflowDefinition workflowDefinition, final ForEachState forEachState, final WorkflowInstance workflowInstance, final Object value) throws Exception {
		final WorkflowData newWorkflowData = new WorkflowData();
		newWorkflowData.setInput(workflowInstance.getWorkflowData().getOutput());
		ELUtil.setValue(forEachState.getIterationParam(), newWorkflowData.getInput(), value);
		return newBranch(workflowDefinition, forEachState.getActions(), newWorkflowData, workflowInstance);
	}
	
	private Branch newParallelBranch(final WorkflowDefinition workflowDefinition, final ParallelStateBranch branch, final WorkflowInstance workflowInstance) throws Exception {
		final WorkflowData newWorkflowData = new WorkflowData();
		newWorkflowData.setInput(workflowInstance.getWorkflowData().getOutput());
		return newBranch(workflowDefinition, branch.getActions(), newWorkflowData, workflowInstance);
	}
	
	private void transitionForEachState(final WorkflowDefinition workflowDefinition, final ForEachState forEachState, final WorkflowInstance workflowInstance) throws Exception {
		filterStateDataInput(forEachState.getStateDataFilter(), workflowInstance.getWorkflowData());
		final List<?> inputCollection = (List<?>) ELUtil.getValue(forEachState.getInputCollection(), workflowInstance.getWorkflowData().getInput());
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
						final List<?> batchInputs = inputCollection.subList(index, Math.min(index + batchSize, inputCollection.size()));
						if(batchInputs.isEmpty()) {
							break;
						}
						final List<Branch> batch = new ArrayList<>();
						for(Object value : batchInputs) {
							batch.add(newForEachBranch(workflowDefinition, forEachState, workflowInstance, value));
						}
						executor.invokeAll(batch);
						index += batchInputs.size();
					}
					break;
				case sequential:
					iterations = new ArrayList<>();
					for(Object value : inputCollection) {
						iterations.add( newForEachBranch(workflowDefinition, forEachState, workflowInstance, value));
					}
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
				transition(workflowDefinition, forEachState.getTransition(), workflowInstance);
			}
			else if(forEachState.getEnd() != null) {
				end(workflowDefinition, forEachState.getEnd(), workflowInstance);
			}
		}
		catch(WorkflowException ex) {
			if(forEachState.getOnErrors() != null) {
				onErrors(workflowDefinition, forEachState.getOnErrors(), ex, workflowInstance);
			}
		}
	}
	
	private void transitionCallbackState(final WorkflowDefinition workflowDefinition, final CallbackState callbackState, final WorkflowInstance workflowInstance) throws Exception {
		filterStateDataInput(callbackState.getStateDataFilter(), workflowInstance.getWorkflowData());
		try {
			final Action action = newAction(workflowDefinition, callbackState.getAction(), workflowInstance.getWorkflowData(), workflowInstance.getUri());
			action.call();
			if(workflowInstance.isTerminate()) {
				return;
			}
			final EventDefinition eventDefinition = getEventDefinition(workflowDefinition, callbackState.getEventRef());
			
			final CallbackStateEvent event = new CallbackStateEvent();
			event.setWorkflowDefinition(workflowDefinition.getId());
			event.setEventDefinition(callbackState.getEventRef());
			event.setState(callbackState.getName());
			
			event.setSource(eventDefinition.getSource());
			event.setType(eventDefinition.getType());
			event.setSubject(workflowInstance.getId());
			
			workflowEventStore.persist(event);
		}
		catch(WorkflowException ex) {
			if(callbackState.getOnErrors() != null) {
				onErrors(workflowDefinition, callbackState.getOnErrors(), ex, workflowInstance);
			}
		}
	}
	
	private void callback(final WorkflowDefinition workflowDefinition, final CallbackState callbackState, final EventDefinition eventDefinition, final WorkflowInstance workflowInstance, final Event event) throws Exception {
		filterEventData(eventDefinition, callbackState.getEventDataFilter(), workflowInstance.getWorkflowData(), event);
		if(callbackState.getTransition() != null) {
			filterStateDataOutput(callbackState.getStateDataFilter(), workflowInstance.getWorkflowData());
			transition(workflowDefinition, callbackState.getTransition(), workflowInstance);
		}
		else if(callbackState.getEnd() != null) {
			end(workflowDefinition, callbackState.getEnd(), workflowInstance);
		}
	}
	
	private void endCondition(final WorkflowDefinition workflowDefinition, final SwitchState switchState, final SwitchStateConditions condition, final WorkflowInstance workflowInstance) throws Exception {
		if(condition.getTransition() != null) {
			filterStateDataOutput(switchState.getStateDataFilter(), workflowInstance.getWorkflowData());
			transition(workflowDefinition, condition.getTransition(), workflowInstance);
		}
		else if(condition.getEnd() != null) {
			end(workflowDefinition, condition.getEnd(), workflowInstance);
		}
	}
	
	public void onEvent(final Event event) {
		try {
			consume(event);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "onEvent", ex);
		}
	}
	
	/**
	 * @param event
	 * @throws Exception 
	 */
	public void consume(@Observes final Event event) throws Exception {
		final Stream<EventStateEvent> eventStateEvents = workflowEventStore.findEventStateEvent(event);
		eventStateEvents.forEach(eventStateEvent -> {
			final WorkflowDefinition workflowDefinition = workflowPersistence.find(eventStateEvent.getWorkflowDefinition()).get();
			final State state = getState(workflowDefinition, eventStateEvent.getState());
			if(state.getType_() == Type.event) {
				final EventState eventState = (EventState) state;
				if(eventStateEvent.getOnEventsDefinition() >= 0 && eventStateEvent.getOnEventsDefinition() < eventState.getOnEvents().size()) {
					final OnEventsDefinition onEventsDefinition = eventState.getOnEvents().get(eventStateEvent.getOnEventsDefinition());
					final EventDefinition eventDefinition = getEventDefinition(workflowDefinition, eventStateEvent.getEventDefinition());
					if(consumeEventStateEvent(workflowDefinition, eventState, onEventsDefinition, eventDefinition, eventStateEvent, event)) {
						workflowEventStore.remove(eventStateEvent);
					}
				}
			}
		});
		final Stream<EventStateActionEvent> eventStateActionEventStream = workflowEventStore.findEventStateActionEvent(event);
		eventStateActionEventStream.forEach(eventStateActionEvent -> {
			final WorkflowDefinition workflowDefinition = workflowPersistence.find(eventStateActionEvent.getWorkflowDefinition()).get();
			final State state = getState(workflowDefinition, eventStateActionEvent.getState());
			if(state.getType_() == Type.event) {
				boolean isEventConsumed = false;
				final EventState eventState = (EventState) state;
				if(eventStateActionEvent.getOnEventsDefinition() >= 0 && eventStateActionEvent.getOnEventsDefinition() < eventState.getOnEvents().size()) {
					final OnEventsDefinition onEventsDefinition = eventState.getOnEvents().get(eventStateActionEvent.getOnEventsDefinition());
					if(eventStateActionEvent.getActionDefinition() >= 0 && eventStateActionEvent.getActionDefinition() < onEventsDefinition.getActions().size()) {
						final ActionDefinition actionDefinition = onEventsDefinition.getActions().get(eventStateActionEvent.getActionDefinition());
						if(actionDefinition.getEventRef() != null && actionDefinition.getEventRef().getConsumeEventRef() != null) {
							final EventDefinition eventDefinition = getEventDefinition(workflowDefinition, actionDefinition.getEventRef().getConsumeEventRef());
							final WorkflowInstance workflowInstance = workflowPersistence.getInstance(eventStateActionEvent.getSubject());
							try {
								consumeActionEvent(workflowDefinition, actionDefinition, eventDefinition, workflowInstance);
								isEventConsumed = true;
							} 
							catch (Exception e) {
								LOGGER.log(Level.SEVERE, "consume", e);
							}
						}
					}
				}
				if(isEventConsumed) {
					workflowEventStore.remove(eventStateActionEvent);
				}
			}
		});
		final Stream<CallbackStateEvent> callbackStateEvents = workflowEventStore.findCallbackStateEvent(event);
		callbackStateEvents.forEach(callbackStateEvent -> {
			final WorkflowDefinition workflowDefinition = workflowPersistence.find(callbackStateEvent.getWorkflowDefinition()).get();
			final State state = getState(workflowDefinition, callbackStateEvent.getState());
			if(state.getType_() == Type.callback) {
				final CallbackState callbackState = (CallbackState)state;
				final EventDefinition eventDefinition = getEventDefinition(workflowDefinition, callbackState.getEventRef());
				final WorkflowInstance workflowInstance = workflowPersistence.getInstance(callbackStateEvent.getSubject());
				try {
					callback(workflowDefinition, callbackState, eventDefinition, workflowInstance, event);
				} 
				catch (Exception e) {
					LOGGER.log(Level.SEVERE, "consume", e);
				}
				workflowEventStore.remove(callbackStateEvent);
			}
		});
	}
	
	private boolean consumeEventStateEvent(final WorkflowDefinition workflowDefinition, final EventState eventState, final OnEventsDefinition onEventsDefinition, final EventDefinition eventDefinition, final EventStateEvent eventStateEvent, final Event event) {
		boolean isEventConsumed = false;
		final Map<String, EventDefinition> events = new HashMap<>();
		MapUtil.putAll(events, workflowDefinition.getEvents().iterator(), EventDefinition::getName);
		for(String eventRef : onEventsDefinition.getEventRefs()) {
			if(eventRef.equals(eventDefinition.getName())) {
				if(eventState.isExclusive()) {
					isEventConsumed = true;
					break;
				}
				else {
					boolean isEventsConsumed = true;
					for(String consumeEventRef : onEventsDefinition.getEventRefs()) {
						if(!consumeEventRef.equals(eventDefinition.getName())){
							final EventDefinition consumeEventDefinition = events.get(consumeEventRef);
							if(workflowEventStore.count(consumeEventDefinition) == 0) {
								isEventsConsumed = false;
								break;
							}
						}
					}
					if(isEventsConsumed) {
						isEventConsumed = true;
					}
				}
			}
		}
		if(isEventConsumed) {
			final WorkflowInstance workflowInstance = workflowPersistence.getInstance(eventStateEvent.getSubject());
			executor.submit(() -> {
				try {
					onEvents(workflowDefinition, eventState, onEventsDefinition, eventDefinition, workflowInstance, event);
				} 
				catch (Exception e) {
					LOGGER.log(Level.SEVERE, "consume", e);
				}
			});
		}
		return isEventConsumed;
	}
	
	private void onEvents(final WorkflowDefinition workflowDefinition, final EventState eventState, final OnEventsDefinition onEventsDefinition, final EventDefinition eventDefinition, final WorkflowInstance workflowInstance, final Event event) throws Exception {
		if(workflowInstance.isTerminate()) {
			return;
		}
		try {
			filterStateDataInput(eventState.getStateDataFilter(), workflowInstance.getWorkflowData());
			filterEventData(eventDefinition, onEventsDefinition.getEventDataFilter(), workflowInstance.getWorkflowData(), event);
			final Duration actionExecTimeout = TimeoutUtil.getActionExecTimeout(workflowDefinition, eventState);
			performActions(workflowDefinition, onEventsDefinition.getActionMode(), onEventsDefinition.getActions(), actionExecTimeout, workflowInstance);
			if(workflowInstance.isTerminate()) {
				return;
			}
			if(eventState.getTransition() != null) {
				filterStateDataOutput(eventState.getStateDataFilter(), workflowInstance.getWorkflowData());
				transition(workflowDefinition, eventState.getTransition(), workflowInstance);
			}
			if(eventState.getEnd() != null) {
				end(workflowDefinition, eventState.getEnd(), workflowInstance);
			}
		}
		catch(WorkflowException ex) {
			if(eventState.getOnErrors() != null) {
				onErrors(workflowDefinition, eventState.getOnErrors(), ex, workflowInstance);
			}
		}
	}
	
	private void consumeActionEvent(final WorkflowDefinition workflowDefinition, final ActionDefinition actionDefinition, final EventDefinition eventDefinition, final WorkflowInstance workflowInstance) throws Exception {
		final WorkflowData actionData = new WorkflowData();
		filterActionDataInput(actionDefinition, workflowInstance.getWorkflowData(), actionData);
		final ProduceEventAction eventAction = new ProduceEventAction(workflowDefinition, actionDefinition, eventDefinition, producedEvent, actionData);
		eventAction.call();
	}
	
	private void filterStateDataInput(final StateDataFilters stateDataFilters, final WorkflowData workflowData) throws Exception {
		if(stateDataFilters != null && stateDataFilters.getInput() != null) {
			final Map<String, Object> newInput = ELUtil.getValue(stateDataFilters.getInput(), workflowData.getInput());
			workflowData.setInput(newInput);
		}
	}
	
	private void filterStateDataOutput(final StateDataFilters stateDataFilters, final WorkflowData workflowData) throws Exception {
		if(stateDataFilters != null && stateDataFilters.getOutput() != null) {
			final Map<String, Object> newOutput = ELUtil.getValue(stateDataFilters.getOutput(), workflowData.getOutput());
			workflowData.setOutput(newOutput);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void filterEventData(final EventDefinition eventDefinition, final EventDataFilters eventDataFilters, final WorkflowData WorkflowData, final Event event) throws Exception {
		if(eventDataFilters != null && eventDataFilters.isUseData()) {
			Map<String, Object> eventData = null;
			if(eventDefinition.isDataOnly()) {
				if(event.getData() instanceof Map) {
					eventData = (Map<String, Object>) event.getData();
				}
				else if(event.getData() instanceof String) {
					eventData = JsonUtil.asMap(JsonUtil.readObject((String)event.getData()));
				}
				else {
					eventData = JsonUtil.toMap(event.getData());
				}
			}
			else {
				eventData = JsonUtil.toMap(event);
			}
			Map<String, Object> data = eventData;
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
	
	private void onErrors(final WorkflowDefinition workflowDefinition, final List<ErrorDefinition> onErrors, final WorkflowException exception, final WorkflowInstance workflowInstance) throws Exception {
		for(ErrorDefinition errorDefinition : onErrors) {
			final List<WorkflowError> errors = getErrors(workflowDefinition, errorDefinition);
			final Optional<WorkflowError> error = errors.stream().filter(err -> err.equals(exception.getWorkflowError())).findFirst();
			if(error.isPresent()) {
				if(workflowInstance.isTerminate()) {
					return;
				}
				if(errorDefinition.getTransition() != null) {
					transition(workflowDefinition, errorDefinition.getTransition(), workflowInstance);
				}
				else if(errorDefinition.getEnd() != null) {
					end(workflowDefinition, errorDefinition.getEnd(), workflowInstance);
				}
				return;
			}
		}
		throw exception;
	}
	
	private void compensateState(final WorkflowDefinition workflowDefinition, final State state, final WorkflowInstance workflowInstance) throws Exception {
		String compensatedBy = null;
		switch(state.getType_()) {
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
			final State compensatedState = getState(workflowDefinition, compensatedBy);
			transitionState(workflowDefinition, compensatedState, workflowInstance);
		}
	}
	
	private void compensate(final WorkflowDefinition workflowDefinition, final WorkflowInstance workflowInstance) throws Exception {
		final List<String> compensateStates = new ArrayList<>(workflowInstance.getStates());
		Collections.reverse(compensateStates);
		final Map<String, State> states = new HashMap<>();
		MapUtil.putAll(states, workflowDefinition.getStates().iterator(), State::getName);
		for(String compensateState : compensateStates) {
			if(workflowInstance.isTerminate()) {
				return;
			}
			compensateState(workflowDefinition, states.get(compensateState), workflowInstance);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void continueAs(final Object continueAs, final WorkflowData workflowData, final URI uri) throws Exception {
		if(continueAs instanceof String) {
			final WorkflowDefinition workflowDefinition = workflowPersistence.find((String)continueAs).get();
			final WorkflowData newWorkflowData = new WorkflowData();
			newWorkflowData.setInput(workflowData.getOutput());
			start(workflowDefinition, newWorkflowData, uri);
		}
		else if(continueAs instanceof ContinueAs) {
			final ContinueAs continueAsDef = (ContinueAs) continueAs;
			final WorkflowDefinition workflowDefinition = workflowPersistence.find(continueAsDef.getWorkflowId(), continueAsDef.getVersion()).get();
			final WorkflowData newWorkflowData = new WorkflowData();
			if(continueAsDef.getData() != null) {
				Map<String, Object> input = null;
				if(continueAsDef.getData() instanceof String) {
					final String data = (String)continueAsDef.getData();
					input = ELUtil.getValue(data, workflowData.getOutput());
				}
				else if(continueAsDef.getData() instanceof Map) {
					input = (Map<String, Object>) continueAsDef.getData();
				}
				else {
					input = JsonUtil.toMap(continueAsDef.getData());
				}
				newWorkflowData.setInput(input);
			}
			else {
				newWorkflowData.setInput(workflowData.getOutput());
			}
			start(workflowDefinition, newWorkflowData, uri);
		}
	}
	
	private void termiateSubFlows(final WorkflowInstance workflowInstance) {
		for(Future<?> subFlow : workflowInstance.getSubFlows()) {
			subFlow.cancel(true);
		}
	}
	
	private void termiate(final WorkflowInstance workflowInstance) {
		workflowInstance.terminate();
		workflowPersistence.remove(workflowInstance);
		termiateSubFlows(workflowInstance);
	}
	
	private void end(final WorkflowDefinition workflowDefinition, final Object end, final WorkflowInstance workflowInstance) throws Exception {
		if(end instanceof EndDefinition) {
			final EndDefinition endDefinition = (EndDefinition) end;
			if(endDefinition.isCompensate()) {
				compensate(workflowDefinition, workflowInstance);
			}
			if(Boolean.TRUE.equals(endDefinition.isTerminate())) {
				termiate(workflowInstance);
				return;
			}
			if(endDefinition.getProduceEvents() != null) {
				produceEvents(workflowDefinition, endDefinition.getProduceEvents());
			}
			termiateSubFlows(workflowInstance);
			if(endDefinition.getContinueAs() != null) {
				continueAs(endDefinition.getContinueAs(), workflowInstance.getWorkflowData(), workflowInstance.getUri());
			}
		}
		else {
			termiateSubFlows(workflowInstance);
		}
	}
	
	private void transition(final WorkflowDefinition workflowDefinition, final Either<String, TransitionDefinition> transition, final WorkflowInstance workflowInstance) throws Exception {
		if(transition.isLeft()) {
			final State nextState = getState(workflowDefinition, transition.getLeft());
			workflowInstance.transition(nextState);
			transitionState(workflowDefinition, nextState, workflowInstance);
		}
		else if(transition.isRight()) {
			final TransitionDefinition transitionDef = transition.getRight();
			if(transitionDef.isCompensate()) {
				compensate(workflowDefinition, workflowInstance);
			}
			produceEvents(workflowDefinition, transitionDef.getProduceEvents());
			final State nextState = getState(workflowDefinition, transitionDef.getNextState());
			workflowInstance.transition(nextState);
			transitionState(workflowDefinition, nextState, workflowInstance);
		}
	}
	
	private void produceEvent(final EventDefinition eventDefinition, final ProducedEventDefinition producedEventDefinition) throws Exception {
		final Event event = new Event();
		event.setSource(eventDefinition.getSource());
		event.setType(eventDefinition.getType());
		if(!(producedEventDefinition.getData() instanceof String)) {
			event.setData(producedEventDefinition.getData());
		}
		producedEvent.fire(event);
	}
	
	private void produceEvents(final WorkflowDefinition workflowDefinition, final List<ProducedEventDefinition> produceEvents) throws Exception {
		final Map<String, EventDefinition> eventDefinitions = new HashMap<>();
		MapUtil.putAll(eventDefinitions, workflowDefinition.getEvents().iterator(), EventDefinition::getName);
		for(ProducedEventDefinition producedEventDefinition : produceEvents) {
			final EventDefinition eventDefinition = eventDefinitions.get(producedEventDefinition.getEventRef());
			produceEvent(eventDefinition, producedEventDefinition);
		}
	}
	
	private void filterActionDataInput(final ActionDefinition actionDefinition, final WorkflowData workflowData, final WorkflowData actionData) throws Exception {
		if(actionDefinition.getActionDataFilter() != null && actionDefinition.getActionDataFilter().getFromStateData() != null) {
			final Map<String, Object> input = ELUtil.getValue(actionDefinition.getActionDataFilter().getFromStateData(), workflowData.getInput());
			actionData.setInput(input);
		}
	}
	
	private void filterActionDataOutput(final ActionDefinition actionDefinition, final WorkflowData workflowData, final WorkflowData actionData) throws Exception {
		if(actionDefinition.getActionDataFilter() != null && actionDefinition.getActionDataFilter().isUseResults() && actionDefinition.getActionDataFilter().getResults() != null) {
			if(actionDefinition.getActionDataFilter().getToStateData() != null) {
				StateUtil.mergeStateDataOutput(actionDefinition.getActionDataFilter().getToStateData(), workflowData, actionData.getOutput());
			}
			else {
				StateUtil.mergeStateDataOutput(workflowData, actionData.getOutput());
			}
		}
	}
	
	private Action newAction(final WorkflowDefinition workflowDefinition, final ActionDefinition actionDefinition, final WorkflowData workflowData, final URI uri) throws Exception {
		Action action = null;
		final WorkflowData actionData = new WorkflowData();
		filterActionDataInput(actionDefinition, workflowData, actionData);
		if(actionDefinition.getFunctionRef() != null) {
			action = new FunctionAction(workflowDefinition, actionDefinition, actionData);
		}
		else if(actionDefinition.getEventRef() != null) {
			final Map<String, EventDefinition> events = new HashMap<>();
			MapUtil.putAll(events, workflowDefinition.getEvents().iterator(), EventDefinition::getName);
			final EventDefinition eventDefinition = events.get(actionDefinition.getEventRef().getProduceEventRef());
			action = new ProduceEventAction(workflowDefinition, actionDefinition, eventDefinition, producedEvent, actionData);
		}
		else if(actionDefinition.getSubFlowRef() != null) {
			final WorkflowDefinition subWorkflowDefinition = getSubWorkflowDefinition(actionDefinition.getSubFlowRef());
			SubFlowRefDefinition subFlowRefDefinition = null;
			if(actionDefinition.getSubFlowRef().isRight()) {
				subFlowRefDefinition = actionDefinition.getSubFlowRef().getRight();
			}
			action = new SubflowAction(workflowDefinition, actionDefinition, this, subFlowRefDefinition, subWorkflowDefinition, actionData, uri);
		}
		return action;
	}
	
	private void performActions(final WorkflowDefinition workflowDefinition, final Mode mode, final List<ActionDefinition> actionDefinitions, final Duration actionExecTimeout, final WorkflowInstance workflowInstance) throws Exception {
		final List<Action> actions = new CopyOnWriteArrayList<>();
		for(ActionDefinition actionDefinition : actionDefinitions) {
			final Action action = newAction(workflowDefinition, actionDefinition, workflowInstance.getWorkflowData(), workflowInstance.getUri());
			actions.add(action);
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
		final List<WorkflowError> errors = workflowDefinition.getErrors();
		if(errorDefinition.getErrorRefs() != null) {
			final Map<String, WorkflowError> map = new HashMap<>();
			MapUtil.putAll(map, errors.iterator(), WorkflowError::getName);
			return MapUtil.getAll(map, errorDefinition.getErrorRefs().iterator());
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
	
	private EventDefinition getEventDefinition(final WorkflowDefinition workflowDefinition, final String eventRef) {
		return workflowDefinition.getEvents().stream().filter(eventDef -> eventDef.getName().equals(eventRef)).findFirst().get();
	}
	
	private State getState(final WorkflowDefinition workflowDefinition, final String name) {
		return workflowDefinition.getStates().stream().filter(state -> state.getName().equals(name)).findFirst().get();
	}
	
	private WorkflowDefinition getSubWorkflowDefinition(final Object subFlowRef) {
		if(subFlowRef instanceof String) {
			return workflowPersistence.find((String)subFlowRef).get();
		}
		else {
			final SubFlowRefDefinition subFlowRefDefinition = (SubFlowRefDefinition) subFlowRef;
			return workflowPersistence.find(subFlowRefDefinition.getWorkflowId(), subFlowRefDefinition.getVersion()).get();
		}
	}
}
