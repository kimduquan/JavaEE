package epf.workflow;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import epf.api.API;
import epf.api.Operation;
import epf.api.PathItem;
import epf.util.ListUtil;
import epf.util.MapUtil;
import epf.workflow.schema.ActionDefinition;
import epf.workflow.schema.ActionMode;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.EventDefinition;
import epf.workflow.schema.EventState;
import epf.workflow.schema.FunctionDefinition;
import epf.workflow.schema.FunctionRefDefinition;
import epf.workflow.schema.OnEventsDefinition;
import epf.workflow.schema.OperationState;
import epf.workflow.schema.ProducedEventDefinition;
import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.SwitchState;
import epf.workflow.schema.SwitchStateConditions;
import epf.workflow.schema.SwitchStateDataConditions;
import epf.workflow.schema.SwitchStateEventConditions;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.Type;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.WorkflowState;

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
	private final List<WorkflowInstance> instances = new CopyOnWriteArrayList<>();
	
	/**
	 * 
	 */
	@Inject
	javax.enterprise.event.Event<Event> producedEvent;

	/**
	 * @param workflowDefinition
	 * @throws Exception 
	 */
	public void start(final WorkflowDefinition workflowDefinition) throws Exception {
		if(workflowDefinition.getStart() != null) {
			if(workflowDefinition.getStart() instanceof String) {
				final String start = (String)workflowDefinition.getStart();
				for(WorkflowState state : workflowDefinition.getStates()) {
					if(start.equals(state.getName())) {
						start(workflowDefinition, state);
						break;
					}
				}
			}
			else if(workflowDefinition.getStart() instanceof StartDefinition) {
				final StartDefinition startDef = (StartDefinition) workflowDefinition.getStart();
				for(WorkflowState state : workflowDefinition.getStates()) {
					if(startDef.getStateName().equals(state.getName())) {
						start(workflowDefinition, state);
						break;
					}
				}
			}
		}
		else {
			start(workflowDefinition, workflowDefinition.getStates()[0]);
		}
	}
	
	private void start(final WorkflowDefinition workflowDefinition, final WorkflowState state) throws Exception {
		switch(state.getType()) {
			case WorkflowState.Event:
				final EventState eventState = (EventState)state;
				startEventState(workflowDefinition, eventState);
				break;
			case WorkflowState.Operation:
				final OperationState operationState = (OperationState) state;
				startOperationState(workflowDefinition, operationState);
				if(operationState.getEnd() != null) {
					end(workflowDefinition, operationState.getEnd());
				}
				else {
					transition(workflowDefinition, operationState.getTransition());
				}
				break;
			case WorkflowState.Switch:
				final SwitchState switchState = (SwitchState) state;
				startSwitchState(workflowDefinition, switchState);
				break;
			case WorkflowState.Sleep:
				break;
			case WorkflowState.Parallel:
				break;
			case WorkflowState.Inject:
				break;
			case WorkflowState.ForEach:
				break;
			case WorkflowState.Callback:
				break;
			default:
				break;
		}
	}
	
	private void startEventState(final WorkflowDefinition workflowDefinition, final EventState eventState) {
		if(workflowDefinition.getEvents() instanceof String) {
			
		}
		else {
			onEvents.add(new OnEvents(workflowDefinition, eventState));
		}
	}
	
	private void startOperationState(final WorkflowDefinition workflowDefinition, final OperationState operationState) throws Exception {
		final WorkflowInstance workflowInstance = newWorkflowInstance(workflowDefinition);
		this.performActions(operationState.getActionMode(), operationState.getActions(), workflowInstance);
	}
	
	private void startSwitchState(final WorkflowDefinition workflowDefinition, final SwitchState switchState) throws Exception {
		if(switchState.getDataConditions() != null) {
			for(SwitchStateDataConditions condition : switchState.getDataConditions()) {
				if(evaluateCondition(condition)) {
					endCondition(workflowDefinition, condition);
					break;
				}
			}
		}
		else if(switchState.getEventConditions() != null) {
			for(SwitchStateEventConditions condition : switchState.getEventConditions()) {
				if(evaluateCondition(condition)) {
					endCondition(workflowDefinition, condition);
					break;
				}
			}
		}
		else {
			if(switchState.getDefaultCondition() instanceof TransitionDefinition) {
				transition(workflowDefinition, switchState.getDefaultCondition());
			}
			else if(switchState.getDefaultCondition() instanceof EndDefinition) {
				end(workflowDefinition, switchState.getDefaultCondition());
			}
		}
	}
	
	private void endCondition(final WorkflowDefinition workflowDefinition, final SwitchStateConditions condition) throws Exception {
		if(condition.getEnd() != null) {
			end(workflowDefinition, condition.getEnd());
		}
		else if(condition.getTransition() != null) {
			transition(workflowDefinition, condition.getTransition());
		}
	}
	
	private boolean evaluateCondition(final SwitchStateDataConditions dataConditions) {
		return true;
	}
	
	private boolean evaluateCondition(final SwitchStateEventConditions eventConditions) {
		return true;
	}
	
	/**
	 * @param event
	 */
	public void onEvent(@Observes final Event event) {
		onEvents.forEach(onEvents -> {
			final WorkflowDefinition workflowDefinition = onEvents.getWorkflowDefinition();
			if(workflowDefinition.getEvents() instanceof String) {
				
			}
			else {
				try {
					onEvents(onEvents, event);
					if(onEvents.getEventState().getEnd() != null) {
						end(workflowDefinition, onEvents.getEventState().getEnd());
					}
					else {
						transition(workflowDefinition, onEvents.getEventState().getTransition());
					}
				} 
				catch (Exception e) {
					
				}
			}
		});
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
	
	private WorkflowInstance newWorkflowInstance(final WorkflowDefinition workflowDefinition) {
		final WorkflowInstance workflowInstance = new WorkflowInstance(workflowDefinition);
		workflowInstance.setActive(workflowDefinition.isKeepActive());
		instances.add(workflowInstance);
		return workflowInstance;
	}
	
	private void onEvents(final OnEvents onEvents, final OnEventsDefinition onEvent, List<EventDefinition> eventRefs, final EventState eventState, final Event event) throws Exception {
		for(EventDefinition eventRef : eventRefs) {
			if(eventRef.getSource().equals(event.getSource().toString()) && eventRef.getType().equals(event.getType())) {
				if(eventState.isExclusive()) {
					if(onEvents.getWorkflowInstance() == null) {
						onEvents.setWorkflowInstance(newWorkflowInstance(onEvents.getWorkflowDefinition()));
					}
					performActions(onEvent.getActionMode(), onEvent.getActions(), onEvents.getWorkflowInstance());
					break;
				}
			}
		}
	}
	
	private void end(final WorkflowDefinition workflowDefinition, final Object end) throws Exception {
		if(end instanceof Boolean) {
		}
		else if(end instanceof EndDefinition) {
			final EndDefinition endDefinition = (EndDefinition) end;
			if(endDefinition.isTerminate()) {
				return;
			}
			if(endDefinition.getProduceEvents() != null) {
				produceEvents(workflowDefinition, endDefinition.getProduceEvents());
			}
		}
	}
	
	private void endEventState(final WorkflowDefinition workflowDefinition, final EventState eventState) throws Exception {
		
	}
	
	private void transition(final WorkflowDefinition workflowDefinition, final Object transition) throws Exception {
		if(transition instanceof String) {
			for(WorkflowState state : workflowDefinition.getStates()) {
				if(state.getName().equals((String)transition)) {
					start(workflowDefinition, state);
					return;
				}
			}
		}
		else if(transition instanceof TransitionDefinition) {
			final TransitionDefinition transitionDef = (TransitionDefinition) transition;
			produceEvents(workflowDefinition, transitionDef.getProduceEvents());
			for(WorkflowState state : workflowDefinition.getStates()) {
				if(state.getName().equals(transitionDef.getNextState())) {
					start(workflowDefinition, state);
					return;
				}
			}
		}
	}
	
	private void produceEvents(final WorkflowDefinition workflowDefinition, final ProducedEventDefinition[] produceEvents) throws Exception {
		if(workflowDefinition.getEvents() instanceof String) {
			
		}
		else {
			final Map<String, EventDefinition> eventDefs = new HashMap<>();
			MapUtil.putAll(eventDefs, (EventDefinition[])workflowDefinition.getEvents(), EventDefinition::getName);
			for(ProducedEventDefinition produceEventDef : produceEvents) {
				final EventDefinition eventDef = eventDefs.get(produceEventDef.getEventRef());
				final Event event = new Event();
				event.setSource(new URI(eventDef.getSource()));
				event.setType(eventDef.getType());
				if(produceEventDef.getData() instanceof String) {
					
				}
				else {
					event.setData(produceEventDef.getData());
				}
				producedEvent.fire(event);
			}
		}
	}
	
	private void performActions(final ActionMode mode, final ActionDefinition[] actions, final WorkflowInstance workflowInstance) throws Exception {
		if(mode == ActionMode.sequential) {
			for(ActionDefinition actionDef : actions) {
				performAction(actionDef, workflowInstance);
			}
		}
	}
	
	private void performAction(final ActionDefinition actionDef, final WorkflowInstance workflowInstance) throws Exception {
		if(actionDef.getCondition() == null && actionDef.getFunctionRef() != null) {
			if(actionDef.getFunctionRef() instanceof String) {
				final String functionRef = (String) actionDef.getFunctionRef();
				final Optional<FunctionDefinition> functionDef = getFunctionDefinition(workflowInstance.getWorkflowDefinition(), functionRef);
				if(functionDef.isPresent()) {
					invokeFunction(functionDef.get(), workflowInstance, null);
				}
			}
			else if(actionDef.getFunctionRef() instanceof FunctionRefDefinition) {
				final FunctionRefDefinition functionRef = (FunctionRefDefinition) actionDef.getFunctionRef();
				final Optional<FunctionDefinition> functionDef = getFunctionDefinition(workflowInstance.getWorkflowDefinition(), functionRef.getRefName());
				if(functionDef.isPresent()) {
					invokeFunction(functionDef.get(), workflowInstance, functionRef);
				}
			}
		}
	}
	
	private Optional<FunctionDefinition> getFunctionDefinition(final WorkflowDefinition workflowDefinition, final String functionRef) {
		if(workflowDefinition.getFunctions() instanceof String) {
		}
		else {
			final FunctionDefinition[] functionDefs = (FunctionDefinition[]) workflowDefinition.getFunctions();
			return ListUtil.findFirst(functionDefs, f -> f.getName().equals(functionRef));
		}
		return Optional.empty();
	}
	
	private void invokeFunction(final FunctionDefinition functionDef, final WorkflowInstance workflowInstance, final FunctionRefDefinition functionRef) throws Exception {
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
					final RestFunction restFunction = new RestFunction(functionDef, api, entry.getKey(), pathItem, operation.get(), functionRef);
					restFunction.invoke(workflowInstance);
					break;
				}
			}
			client.close();
		}
	}
}
