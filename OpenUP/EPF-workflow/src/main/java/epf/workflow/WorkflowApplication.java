package epf.workflow;

import java.io.InputStream;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.POST;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.validation.Validator;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA.Type;
import epf.naming.Naming;
import epf.util.MapUtil;
import epf.workflow.action.Action;
import epf.workflow.action.SubflowAction;
import epf.workflow.event.Event;
import epf.workflow.event.ProduceEventAction;
import epf.workflow.event.persistence.CallbackStateEvent;
import epf.workflow.event.persistence.EventStateEvent;
import epf.workflow.function.FunctionAction;
import epf.workflow.schema.ContinueAs;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.SubFlowRefDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.WorkflowData;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.action.Mode;
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
import epf.workflow.state.Branch;
import epf.workflow.state.util.StateUtil;
import epf.workflow.util.ELUtil;
import epf.workflow.util.EitherUtil;
import epf.workflow.util.TimeoutUtil;
import io.smallrye.common.annotation.RunOnVirtualThread;
import epf.workflow.schema.state.SwitchState;
import epf.workflow.schema.state.SwitchStateConditions;
import epf.workflow.schema.state.SwitchStateDataConditions;
import epf.workflow.schema.state.SwitchStateEventConditions;
import epf.workflow.schema.util.Either;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.WORKFLOW)
public class WorkflowApplication  {
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowRuntime runtime;
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowPersistence persistence;
	
	/**
	 * 
	 */
	@Inject
	@Readiness
	transient WorkflowCache cache;
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowEventStore eventStore;
	
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
	transient Validator validator;
	
	private Response transitionLink(final String workflow, final String version, final String state, final URI instance, final WorkflowData workflowData) {
		return Response.ok(workflowData)
				.links(WorkflowLink.transitionLink(workflow, version, state))
				.header(LRA.LRA_HTTP_CONTEXT_HEADER, instance)
				.build();
	}
	
	private Response endLink(final String workflow, final String version, final String state, final URI instance, final WorkflowData workflowData) {
		return Response.ok(workflowData)
				.links(WorkflowLink.endLink(workflow, version, state))
				.header(LRA.LRA_HTTP_CONTEXT_HEADER, instance)
				.build();
	}
	
	private Response scheduleLink(final StartDefinition startDefinition, final WorkflowDefinition workflowDefinition) {
		Link scheduleLink = null;
		String path =  "/" + workflowDefinition.getName();
		String recurringTimeInterval = null;
		if(startDefinition.getSchedule().isLeft()) {
			recurringTimeInterval = startDefinition.getSchedule().getLeft();
		}
		else if(startDefinition.getSchedule().isRight()) {
			recurringTimeInterval = startDefinition.getSchedule().getRight().getInterval();
		}
		if(workflowDefinition.getVersion() != null) {
			scheduleLink = WorkflowLink.scheduleLink(Naming.WORKFLOW, HttpMethod.PUT, path + "?version=" + workflowDefinition.getVersion(), recurringTimeInterval);
		}
		else {
			scheduleLink = WorkflowLink.scheduleLink(Naming.WORKFLOW, HttpMethod.PUT, path, recurringTimeInterval);
		}
		return Response.ok()
				.links(scheduleLink)
				.build();
	}
	
	private Response getWorkflowDefinitionLink(final WorkflowDefinition workflowDefinition) {
		final Link link = WorkflowLink.getWorkflowDefinitionLink(workflowDefinition.getId(), workflowDefinition.getVersion());
		return Response.ok()
				.links(link)
				.build();
	}
	
	private void cacheState(final String state, final URI instance, final WorkflowData workflowData) {
		final WorkflowState workflowState = cache.getState(instance);
		final WorkflowState newWorkflowState = new WorkflowState();
		newWorkflowState.setPreviousState(workflowState);
		newWorkflowState.setName(state);
		newWorkflowState.setWorkflowData(workflowData);
		cache.putState(instance, newWorkflowState);
	}
	
	private State getState(final WorkflowDefinition workflowDefinition, final String name) {
		return workflowDefinition.getStates().stream().filter(state -> state.getName().equals(name)).findFirst().orElseThrow(BadRequestException::new);
	}
	
	private void filterStateDataInput(final StateDataFilters stateDataFilters, final WorkflowData workflowData) throws Exception {
		if(stateDataFilters != null && stateDataFilters.getInput() != null) {
			final Map<String, Object> newInput = ELUtil.getValue(stateDataFilters.getInput(), workflowData.getInput());
			workflowData.setInput(newInput);
		}
	}
	
	private void filterActionDataInput(final ActionDefinition actionDefinition, final WorkflowData workflowData, final WorkflowData actionData) throws Exception {
		if(actionDefinition.getActionDataFilter() != null && actionDefinition.getActionDataFilter().getFromStateData() != null) {
			final Map<String, Object> input = ELUtil.getValue(actionDefinition.getActionDataFilter().getFromStateData(), workflowData.getInput());
			actionData.setInput(input);
		}
	}
	
	private EventDefinition getEventDefinition(final WorkflowDefinition workflowDefinition, final String event) {
		final List<EventDefinition> events = EitherUtil.getArray(workflowDefinition.getEvents());
		return events.stream().filter(eventDef -> eventDef.getName().equals(event)).findFirst().get();
	}
	
	private WorkflowDefinition getSubWorkflowDefinition(final Object subFlowRef) {
		if(subFlowRef instanceof String) {
			return cache.get((String)subFlowRef);
		}
		else {
			final SubFlowRefDefinition subFlowRefDefinition = (SubFlowRefDefinition) subFlowRef;
			return cache.get(subFlowRefDefinition.getWorkflowId(), subFlowRefDefinition.getVersion());
		}
	}
	
	private Action newAction(final WorkflowDefinition workflowDefinition, final ActionDefinition actionDefinition, final URI instance, final WorkflowData workflowData) throws Exception {
		Action action = null;
		final WorkflowData actionData = new WorkflowData();
		filterActionDataInput(actionDefinition, workflowData, actionData);
		if(actionDefinition.getFunctionRef() != null) {
			action = new FunctionAction(workflowDefinition, actionDefinition, actionData);
		}
		else if(actionDefinition.getEventRef() != null) {
			final EventDefinition eventDefinition = getEventDefinition(workflowDefinition, actionDefinition.getEventRef().getProduceEventRef());
			action = new ProduceEventAction(workflowDefinition, actionDefinition, eventDefinition, producedEvent, actionData);
		}
		else if(actionDefinition.getSubFlowRef() != null) {
			final WorkflowDefinition subWorkflowDefinition = getSubWorkflowDefinition(actionDefinition.getSubFlowRef());
			SubFlowRefDefinition subFlowRefDefinition = null;
			if(actionDefinition.getSubFlowRef().isRight()) {
				subFlowRefDefinition = actionDefinition.getSubFlowRef().getRight();
			}
			action = new SubflowAction(workflowDefinition, actionDefinition, null, subFlowRefDefinition, subWorkflowDefinition, actionData, instance);
		}
		return action;
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
	
	private void filterStateDataOutput(final StateDataFilters stateDataFilters, final WorkflowData workflowData) throws Exception {
		if(stateDataFilters != null && stateDataFilters.getOutput() != null) {
			final Map<String, Object> newOutput = ELUtil.getValue(stateDataFilters.getOutput(), workflowData.getOutput());
			workflowData.setOutput(newOutput);
		}
	}
	
	private void performActions(final WorkflowDefinition workflowDefinition, final Mode mode, final List<ActionDefinition> actionDefinitions, final Duration actionExecTimeout, final URI instance, final WorkflowData workflowData) throws Exception {
		final List<Action> actions = new CopyOnWriteArrayList<>();
		for(ActionDefinition actionDefinition : actionDefinitions) {
			final Action action = newAction(workflowDefinition, actionDefinition, instance, workflowData);
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
	
	private void produceEvent(final EventDefinition eventDefinition, final ProducedEventDefinition producedEventDefinition, final URI instance) throws Exception {
		final Event event = new Event();
		event.setSource(eventDefinition.getSource());
		event.setType(eventDefinition.getType());
		event.setSubject(instance.toString());
		if(!(producedEventDefinition.getData() instanceof String)) {
			event.setData(producedEventDefinition.getData());
		}
		producedEvent.fire(event);
	}
	
	private void produceEvents(final WorkflowDefinition workflowDefinition, final List<ProducedEventDefinition> produceEvents, final URI instance) throws Exception {
		final Map<String, EventDefinition> eventDefinitions = new HashMap<>();
		final List<EventDefinition> events = EitherUtil.getArray(workflowDefinition.getEvents());
		MapUtil.putAll(eventDefinitions, events.iterator(), EventDefinition::getName);
		for(ProducedEventDefinition producedEventDefinition : produceEvents) {
			final EventDefinition eventDefinition = eventDefinitions.get(producedEventDefinition.getEventRef());
			produceEvent(eventDefinition, producedEventDefinition, instance);
		}
	}
	
	private Response end(final WorkflowDefinition workflowDefinition, final Either<Boolean, EndDefinition> end, final URI instance, final WorkflowData workflowData) throws Exception {
		if(end.isRight()) {
			final EndDefinition endDefinition = end.getRight();
			if(endDefinition.isCompensate()) {
				return compensateLink(workflowDefinition.getId(), workflowDefinition.getVersion(), "", instance, workflowData);
			}
			if(Boolean.TRUE.equals(endDefinition.isTerminate())) {
				return endLink(workflowDefinition.getId(), workflowDefinition.getVersion(), "", instance, workflowData);
			}
			if(endDefinition.getProduceEvents() != null) {
				produceEvents(workflowDefinition, endDefinition.getProduceEvents(), instance);
			}
			if(endDefinition.getContinueAs() != null) {
				return continueAs(endDefinition.getContinueAs(), instance, workflowData);
			}
		}
		return Response.ok(workflowData.getOutput()).build();
	}
	
	private Response startLink(final String workflow, final String version, final URI parentInstance, final WorkflowData workflowData) {
		ResponseBuilder builder = Response.ok(workflowData).links(WorkflowLink.startLink(workflow, version));
		if(parentInstance != null) {
			builder = builder.header(LRA.LRA_HTTP_PARENT_CONTEXT_HEADER, parentInstance);
		}
		return builder.build();
	}
	
	private Response compensateLink(final String workflow, final String version, final String state, final URI instance, final WorkflowData workflowData) {
		return Response.ok(workflowData)
				.links(WorkflowLink.compensateLink(workflow, version, state))
				.header(LRA.LRA_HTTP_CONTEXT_HEADER, instance)
				.build();
	}
	
	private Response endCondition(final WorkflowDefinition workflowDefinition, final SwitchState switchState, final SwitchStateConditions condition, final URI instance, final WorkflowData workflowData) throws Exception {
		if(condition.getTransition() != null) {
			filterStateDataOutput(switchState.getStateDataFilter(), workflowData);
			return transitionState(workflowDefinition, condition.getTransition(), instance, workflowData);
		}
		else if(condition.getEnd() != null) {
			return end(workflowDefinition, condition.getEnd(), instance, workflowData);
		}
		throw new BadRequestException();
	}
	
	@SuppressWarnings("unchecked")
	private Response continueAs(final Object continueAs, final URI instance, final WorkflowData workflowData) throws Exception {
		if(continueAs instanceof String) {
			final WorkflowDefinition workflowDefinition = cache.get((String)continueAs);
			final WorkflowData newWorkflowData = new WorkflowData();
			newWorkflowData.setInput(workflowData.getOutput());
			return startLink(workflowDefinition.getId(), workflowDefinition.getVersion(), instance, newWorkflowData);
		}
		else if(continueAs instanceof ContinueAs) {
			final ContinueAs continueAsDef = (ContinueAs) continueAs;
			final WorkflowDefinition workflowDefinition = cache.get(continueAsDef.getWorkflowId(), continueAsDef.getVersion());
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
				newWorkflowData.setInput(input);
			}
			else {
				newWorkflowData.setInput(workflowData.getOutput());
			}
			return startLink(workflowDefinition.getId(), workflowDefinition.getVersion(), instance, newWorkflowData);
		}
		throw new BadRequestException();
	}
	
	private Branch newBranch(final WorkflowDefinition workflowDefinition, final List<ActionDefinition> actionDefinitions, final URI instance, final WorkflowData workflowData) throws Exception {
		final List<Action> actions = new ArrayList<>();
		for(ActionDefinition actionDefinition : actionDefinitions) {
			final Action action = newAction(workflowDefinition, actionDefinition, instance, workflowData);
			actions.add(action);
		}
		return new Branch(actions.toArray(new Action[0]), workflowData, null);
	}
	
	private Branch newForEachBranch(final WorkflowDefinition workflowDefinition, final ForEachState forEachState, final URI instance, final WorkflowData workflowData, final Object value) throws Exception {
		final WorkflowData newWorkflowData = new WorkflowData();
		newWorkflowData.setInput(workflowData.getOutput());
		ELUtil.setValue(forEachState.getIterationParam(), newWorkflowData.getInput(), value);
		return newBranch(workflowDefinition, forEachState.getActions(), instance, newWorkflowData);
	}
	
	private Branch newParallelBranch(final WorkflowDefinition workflowDefinition, final ParallelStateBranch branch, final URI instance, final WorkflowData workflowData) throws Exception {
		final WorkflowData newWorkflowData = new WorkflowData();
		newWorkflowData.setInput(workflowData.getOutput());
		return newBranch(workflowDefinition, branch.getActions(), instance, newWorkflowData);
	}
	
	private String getCompensatedBy(final State state) {
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
			return compensatedBy;
		}
		throw new BadRequestException();
	}
	
	private Response transitionState(final WorkflowDefinition workflowDefinition, final Either<String, TransitionDefinition> transition, final URI instance, final WorkflowData workflowData) throws Exception {
		if(transition.isLeft()) {
			final String state = transition.getLeft();
			return transitionLink(workflowDefinition.getId(), workflowDefinition.getVersion(), state, instance, workflowData);
		}
		else if(transition.isRight()) {
			final TransitionDefinition transitionDef = transition.getRight();
			if(transitionDef.isCompensate()) {
				return compensateLink(workflowDefinition.getId(), workflowDefinition.getVersion(), transitionDef.getNextState(), instance, workflowData);
			}
			produceEvents(workflowDefinition, transitionDef.getProduceEvents(), instance);
			return transitionLink(workflowDefinition.getId(), workflowDefinition.getVersion(), transitionDef.getNextState(), instance, workflowData);
		}
		throw new BadRequestException();
	}
	
	private Response transitionCallbackState(final WorkflowDefinition workflowDefinition, final CallbackState callbackState, final URI instance, final WorkflowData workflowData) throws Exception {
		filterStateDataInput(callbackState.getStateDataFilter(), workflowData);
		final EventDefinition eventDefinition = getEventDefinition(workflowDefinition, callbackState.getEventRef());
		
		final CallbackStateEvent event = new CallbackStateEvent();
		event.setWorkflowDefinition(workflowDefinition.getId());
		event.setEventDefinition(callbackState.getEventRef());
		event.setState(callbackState.getName());
		
		event.setSource(eventDefinition.getSource());
		event.setType(eventDefinition.getType());
		event.setSubject(instance.toString());
		
		eventStore.persist(event);
		
		final Action action = newAction(workflowDefinition, callbackState.getAction(), instance, workflowData);
		action.call();
		return Response.ok().build();
	}
	
	private Response transitionEventState(final WorkflowDefinition workflowDefinition, final EventState eventState, final URI instance) {
		final Map<String, EventDefinition> eventDefs = new HashMap<>();
		final List<EventDefinition> events = EitherUtil.getArray(workflowDefinition.getEvents());
		MapUtil.putAll(eventDefs, events.iterator(), EventDefinition::getName);
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
				event.setSubject(instance.toString());
				
				eventStore.persist(event);
			}
			index++;
		}
		return Response.ok().build();
	}
	
	private Response transitionOperationState(final WorkflowDefinition workflowDefinition, final OperationState operationState, final URI instance, final WorkflowData workflowData) throws Exception {
		filterStateDataInput(operationState.getStateDataFilter(), workflowData);
		final Duration actionExecTimeout = TimeoutUtil.getActionExecTimeout(workflowDefinition, operationState);
		performActions(workflowDefinition, operationState.getActionMode(), operationState.getActions(), actionExecTimeout, instance, workflowData);
		if(operationState.getTransition() != null) {
			filterStateDataOutput(operationState.getStateDataFilter(), workflowData);
			return transitionState(workflowDefinition, operationState.getTransition(), instance, workflowData);
		}
		else if(operationState.getEnd() != null) {
			return end(workflowDefinition, operationState.getEnd(), instance, workflowData);
		}
		throw new BadRequestException();
	}
	
	private Response transitionSwitchState(final WorkflowDefinition workflowDefinition, final SwitchState switchState, final URI instance, final WorkflowData workflowData) throws Exception {
		filterStateDataInput(switchState.getStateDataFilter(), workflowData);
		if(switchState.getDataConditions() != null) {
			for(SwitchStateDataConditions condition : switchState.getDataConditions()) {
				if(ELUtil.evaluateCondition(workflowData.getInput(), condition.getCondition())) {
					return endCondition(workflowDefinition, switchState, condition, instance, workflowData);
				}
			}
		}
		else if(switchState.getEventConditions() != null) {
			final Map<String, EventDefinition> eventDefinitions = new HashMap<>();
			final List<EventDefinition> events = EitherUtil.getArray(workflowDefinition.getEvents());
			MapUtil.putAll(eventDefinitions, events.iterator(), EventDefinition::getName);
			for(SwitchStateEventConditions condition : switchState.getEventConditions()) {
				final EventDefinition eventDefinition = eventDefinitions.get(condition.getEventRef());
				boolean isEvent = false;
				isEvent = eventDefinition != null;
				if(isEvent) {
					return endCondition(workflowDefinition, switchState, condition, instance, workflowData);
				}
			}
		}
		if(switchState.getDefaultCondition().isLeft()) {
			filterStateDataOutput(switchState.getStateDataFilter(), workflowData);
			return transitionState(workflowDefinition, switchState.getDefaultCondition().getLeft(), instance, workflowData);
		}
		else if(switchState.getDefaultCondition().isRight()) {
			return end(workflowDefinition, switchState.getDefaultCondition().getRight(), instance, workflowData);
		}
		throw new BadRequestException();
	}
	
	private Response transitionSleepState(final WorkflowDefinition workflowDefinition, final SleepState sleepState, final URI instance, final WorkflowData workflowData) throws Exception {
		final Duration duration = Duration.parse(sleepState.getDuration());
		Thread.currentThread();
		Thread.sleep(duration.getSeconds() * 1000);
		if(sleepState.getTransition() != null) {
			return transitionState(workflowDefinition, sleepState.getTransition(), instance, workflowData);
		}
		else if(sleepState.getEnd() != null) {
			return end(workflowDefinition, sleepState.getEnd(), instance, workflowData);
		}
		throw new BadRequestException();
	}
	
	private Response transitionParallelState(final WorkflowDefinition workflowDefinition, final ParallelState parallelState, final URI instance, final WorkflowData workflowData) throws Exception {
		filterStateDataInput(parallelState.getStateDataFilter(), workflowData);
		final List<Branch> branches = new ArrayList<>();
		for(ParallelStateBranch branch : parallelState.getBranches()) {
			branches.add(newParallelBranch(workflowDefinition, branch, instance, workflowData));
		}
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
		if(parallelState.getTransition() != null) {
			filterStateDataOutput(parallelState.getStateDataFilter(), workflowData);
			return transitionState(workflowDefinition, parallelState.getTransition(), instance, workflowData);
		}
		else if(parallelState.getEnd() != null) {
			return end(workflowDefinition, parallelState.getEnd(), instance, workflowData);
		}
		throw new BadRequestException();
	}
	
	private Response transitionInjectState(final WorkflowDefinition workflowDefinition, final InjectState injectState, final URI instance, final WorkflowData workflowData) throws Exception {
		filterStateDataInput(injectState.getStateDataFilter(), workflowData);
		StateUtil.mergeStateDataOutput(workflowData, injectState.getData());
		if(injectState.getTransition() != null) {
			filterStateDataOutput(injectState.getStateDataFilter(), workflowData);
			return transitionState(workflowDefinition, injectState.getTransition(), instance, workflowData);
		}
		else if(injectState.getEnd() != null) {
			return end(workflowDefinition, injectState.getEnd(), instance, workflowData);
		}
		throw new BadRequestException();
	}
	
	private Response transitionForEachState(final WorkflowDefinition workflowDefinition, final ForEachState forEachState, final URI instance, final WorkflowData workflowData) throws Exception {
		filterStateDataInput(forEachState.getStateDataFilter(), workflowData);
		final List<?> inputCollection = (List<?>) ELUtil.getValue(forEachState.getInputCollection(), workflowData.getInput());
		int batchSize = inputCollection.size();
		if(forEachState.getBatchSize().isLeft()) {
			batchSize = Integer.valueOf(forEachState.getBatchSize().getLeft());
		}
		else if(forEachState.getBatchSize().isRight()) {
			batchSize = forEachState.getBatchSize().getRight().intValue();
		}
		List<Branch> iterations = null;
		switch(forEachState.getMode()) {
			case parallel:
				int index = 0;
				while(true) {
					final List<?> batchInputs = inputCollection.subList(index, Math.min(index + batchSize, inputCollection.size()));
					if(batchInputs.isEmpty()) {
						break;
					}
					final List<Branch> batch = new ArrayList<>();
					for(Object value : batchInputs) {
						batch.add(newForEachBranch(workflowDefinition, forEachState, instance, workflowData, value));
					}
					executor.invokeAll(batch);
					index += batchInputs.size();
				}
				break;
			case sequential:
				iterations = new ArrayList<>();
				for(Object value : inputCollection) {
					iterations.add(newForEachBranch(workflowDefinition, forEachState, instance, workflowData, value));
				}
				for(Branch forEach : iterations) {
					forEach.call();
				}
				break;
			default:
				break;
		}
		for(Branch forEach : iterations) {
			for(Action action : forEach.getActions()) {
				filterActionDataOutput(action.getActionDefinition(), forEach.getWorkflowData(), action.getWorkflowData());
			}
		}
		for(Branch forEach : iterations) {
			ELUtil.setValue(forEachState.getOutputCollection(), workflowData.getOutput(), forEach.getWorkflowData().getOutput());
		}
		if(forEachState.getTransition() != null) {
			filterStateDataOutput(forEachState.getStateDataFilter(), workflowData);
			return transitionState(workflowDefinition, forEachState.getTransition(), instance, workflowData);
		}
		else if(forEachState.getEnd() != null) {
			return end(workflowDefinition, forEachState.getEnd(), instance, workflowData);
		}
		throw new BadRequestException();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response newWorkflowDefinition(final InputStream body) throws Exception {
		WorkflowDefinition workflowDefinition = null;
		try(Jsonb jsonb = JsonbBuilder.create()){
			workflowDefinition = jsonb.fromJson(body, WorkflowDefinition.class);
		}
		catch(Exception ex) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if(!validator.validate(workflowDefinition).isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		final WorkflowDefinition newWorkflowDefinition = persistence.persist(workflowDefinition);
		if(workflowDefinition.getVersion() != null) {
			cache.put(newWorkflowDefinition.getId(), workflowDefinition.getVersion(), workflowDefinition);
		}
		else {
			cache.put(newWorkflowDefinition.getId(), workflowDefinition);
		}
		if(workflowDefinition.getStart().isRight()) {
			return scheduleLink(workflowDefinition.getStart().getRight(), workflowDefinition);
		}
		return getWorkflowDefinitionLink(newWorkflowDefinition);
	}

	@PUT
	@Path("{workflow}")
	@LRA(value = Type.NESTED, end = false)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response start(final String workflow, final String version, final URI instance, final Map<String, Object> input) throws Exception {
		WorkflowDefinition workflowDefinition = findWorkflowDefinition(workflow, version);
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
		final WorkflowData workflowData = new WorkflowData();
		workflowData.setInput(input);
		workflowData.setOutput(new HashMap<>());
		return transitionLink(workflow, version, startState, instance, workflowData);
	}
	
	private WorkflowDefinition findWorkflowDefinition(final String workflow, final String version) {
		Optional<WorkflowDefinition> workflowDefinition = Optional.empty();
		if(version != null) {
			workflowDefinition = Optional.ofNullable(cache.get(workflow, version));
			if(!workflowDefinition.isPresent()) {
				workflowDefinition = persistence.find(workflow, version);
			}
		}
		else {
			workflowDefinition = Optional.ofNullable(cache.get(workflow));
			if(!workflowDefinition.isPresent()) {
				workflowDefinition = persistence.find(workflow);
			}
		}
		return workflowDefinition.orElseThrow(NotFoundException::new);
	}

	@GET
	@Path("{workflow}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response getWorkflowDefinition(final String workflow, final String version) throws Exception {
		final WorkflowDefinition workflowDefinition = findWorkflowDefinition(workflow, version);
		try(Jsonb jsonb = JsonbBuilder.create()){
			return Response.ok(jsonb.toJson(workflowDefinition), MediaType.APPLICATION_JSON).build();
		}
	}

	@PUT
	@Path("{workflow}/{state}")
	@LRA(value = Type.MANDATORY, end = false)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response transition(final String workflow, final String version, final String state, final URI instance, final WorkflowData workflowData) throws Exception {
		final WorkflowDefinition workflowDefinition = findWorkflowDefinition(workflow, version);
		final State nextState = getState(workflowDefinition, state);
		cacheState(state, instance, workflowData);
		switch(nextState.getType_()) {
			case event:
				final EventState eventState = (EventState)nextState;
				return transitionEventState(workflowDefinition, eventState, instance);
			case operation:
				final OperationState operationState = (OperationState) nextState;
				return transitionOperationState(workflowDefinition, operationState, instance, workflowData);
			case Switch:
				final SwitchState switchState = (SwitchState) nextState;
				return transitionSwitchState(workflowDefinition, switchState, instance, workflowData);
			case sleep:
				final SleepState sleepState = (SleepState) nextState;
				return transitionSleepState(workflowDefinition, sleepState, instance, workflowData);
			case parallel:
				final ParallelState parallelState = (ParallelState) nextState;
				return transitionParallelState(workflowDefinition, parallelState, instance, workflowData);
			case inject:
				final InjectState injectState = (InjectState) nextState;
				return transitionInjectState(workflowDefinition, injectState, instance, workflowData);
			case foreach:
				final ForEachState forEachState = (ForEachState) nextState;
				return transitionForEachState(workflowDefinition, forEachState, instance, workflowData);
			case callback:
				final CallbackState callbackState = (CallbackState) nextState;
				return transitionCallbackState(workflowDefinition, callbackState, instance, workflowData);
			default:
				throw new BadRequestException();
		}
	}

	@PUT
	@Path("{workflow}/{state}/end")
	@LRA(value = Type.MANDATORY, end = true)
	@Consumes(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response end(final String workflow, final String version, final String state, final URI instance, final WorkflowData workflowData) throws Exception {
		cache.removeState(instance);
		return null;
	}

	@PUT
	@Path("{workflow}/{state}/compensate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Compensate
	@RunOnVirtualThread
	public Response compensate(final String workflow, final String version, final String state, final URI instance, final WorkflowData workflowData) throws Exception {
		final WorkflowDefinition workflowDefinition = findWorkflowDefinition(workflow, version);
		final State currentState = getState(workflowDefinition, state);
		final String compensatedBy = getCompensatedBy(currentState);
		getState(workflowDefinition, compensatedBy);
		WorkflowState workflowState = cache.getState(instance);
		while(workflowState != null) {
			workflowState = workflowState.getPreviousState();
		}
		return null;
	}
}
