package epf.workflow;

import java.io.InputStream;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonValue;
import jakarta.validation.Validator;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.Forget;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA.Type;
import org.eclipse.microprofile.lra.annotation.ws.rs.Leave;
import epf.event.schema.Event;
import epf.naming.Naming;
import epf.nosql.schema.BooleanOrObject;
import epf.nosql.schema.Either;
import epf.nosql.schema.EitherUtil;
import epf.nosql.schema.StringOrObject;
import epf.util.MapUtil;
import epf.util.json.ext.JsonUtil;
import epf.workflow.client.Internal;
import epf.workflow.client.Workflow;
import epf.workflow.compensation.WorkflowCompensation;
import epf.workflow.data.ActionDataFilters;
import epf.workflow.data.DataMerging;
import epf.workflow.data.EventDataFilters;
import epf.workflow.data.StateDataFilters;
import epf.workflow.error.WorkflowException;
import epf.workflow.expressions.WorkflowExpressions;
import epf.workflow.functions.WorkflowFunctions;
import epf.workflow.instance.WorkflowInstance;
import epf.workflow.model.Instance;
import epf.workflow.model.WorkflowData;
import epf.workflow.retries.ActionRetries;
import epf.workflow.schema.ContinueAs;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.SubFlowRefDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.action.Mode;
import epf.workflow.schema.event.EventDefinition;
import epf.workflow.schema.event.Kind;
import epf.workflow.schema.event.OnEventsDefinition;
import epf.workflow.schema.function.FunctionDefinition;
import epf.workflow.schema.state.CallbackState;
import epf.workflow.schema.state.EventState;
import epf.workflow.schema.state.ForEachState;
import epf.workflow.schema.state.InjectState;
import epf.workflow.schema.state.OperationState;
import epf.workflow.schema.state.ParallelState;
import epf.workflow.schema.state.ParallelStateBranch;
import epf.workflow.schema.state.SleepState;
import epf.workflow.schema.state.State;
import epf.workflow.schema.state.StateDataFilter;
import epf.workflow.util.LinkBuilder;
import epf.workflow.util.ResponseBuilder;
import io.smallrye.common.annotation.RunOnVirtualThread;
import epf.workflow.schema.state.SwitchState;
import epf.workflow.schema.state.SwitchStateDataConditions;
import epf.workflow.schema.state.SwitchStateEventConditions;
import epf.workflow.states.WorkflowStates;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.WORKFLOW)
public class WorkflowRuntime implements Workflow, Internal {
	
	/**
	 * 
	 */
	@Inject
	@Readiness
	transient WorkflowInstance workflowInstance;
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowStates workflowStates;
	
	/**
	 * 
	 */
	@Inject
	transient DataMerging dataMerging;
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowExpressions workflowExpressions;
	
	/**
	 * 
	 */
	@Inject
	transient StateDataFilters stateDataFilters;

	/**
	 * 
	 */
	@Inject
	transient WorkflowFunctions workflowFunctions;
	
	/**
	 * 
	 */
	@Inject
	transient ActionRetries actionRetries;
	
	/**
	 * 
	 */
	@Inject
	transient ActionDataFilters actionDataFilters;
	
	/**
	 * 
	 */
	@Inject
	transient EventDataFilters eventDataFilters;
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowCompensation workflowCompensation;
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowEvents workflowEvents;
	
	/**
	 * 
	 */
	@Inject
	transient Validator validator;
	
	private ResponseBuilder transitionLink(final ResponseBuilder response, final String workflow, final Optional<String> version, final String nextState, final Boolean compensate) {
		final LinkBuilder builder = new LinkBuilder();
		final Link transitionLink = Internal.transitionLink(workflow, version, nextState, compensate);
		builder.link(transitionLink).at(response.getSize());
		return response.links(builder.build());
	}
	
	private WorkflowDefinition getSubWorkflowDefinition(final StringOrObject<SubFlowRefDefinition> subFlowRef) {
		if(subFlowRef.isLeft()) {
			return workflowInstance.get(subFlowRef.getLeft());
		}
		else if(subFlowRef.isRight()) {
			final SubFlowRefDefinition subFlowRefDefinition = subFlowRef.getRight();
			return workflowInstance.get(subFlowRefDefinition.getWorkflowId(), subFlowRefDefinition.getVersion());
		}
		return null;
	}
	
	private ResponseBuilder subFlow(final ResponseBuilder response) {
		return response;
	}
	
	private ResponseBuilder action(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final String state, final ActionDefinition actionDefinition, final URI instance, final WorkflowData workflowData) throws Exception {
		if(!actionDefinition.getFunctionRef().isNull()) {
			final FunctionDefinition functionDefinition = workflowFunctions.getFunctionDefinition(workflowDefinition, actionDefinition.getFunctionRef());
			try {
				workflowFunctions.function(response, workflowDefinition, actionDefinition, functionDefinition, workflowData);
			}
			catch(WorkflowException ex) {
				actionRetries.retry(workflowDefinition, actionDefinition, ex);
			}
		}
		else if(actionDefinition.getEventRef() != null) {
			workflowEvents.eventRef(response, workflowDefinition, state, actionDefinition, actionDefinition.getEventRef(), instance, workflowData);
		}
		else if(!actionDefinition.getSubFlowRef().isNull()) {
			getSubWorkflowDefinition(actionDefinition.getSubFlowRef());
			if(actionDefinition.getSubFlowRef().isRight()) {
				actionDefinition.getSubFlowRef().getRight();
			}
			subFlow(response);
		}
		return response;
	}
	
	private ResponseBuilder batches(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final String state, final Mode mode, final int batchSize, final JsonArray inputCollection) {
		final JsonArrayBuilder batches = Json.createArrayBuilder();
		JsonArrayBuilder batch = null;
		final Iterator<JsonValue> iterator = inputCollection.iterator();
		int index = 0;
		while(iterator.hasNext()) {
			final JsonValue value = iterator.next();
			if(index % batchSize == 0) {
				if(index > 0) {
					batches.add(batch);
				}
				batch = Json.createArrayBuilder();
			}
			batch.add(value);
			index++;
		}
		final Link batchLink = Internal.batchLink(workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), state);
		final LinkBuilder builder = new LinkBuilder();
		final JsonArray batchesArray = batches.build();
		final Iterator<JsonValue> batchIt = batchesArray.iterator();
		final List<Link> batchLinks = new ArrayList<>();
		index = 0;
		while(batchIt.hasNext()) {
			final Link link = builder.link(batchLink).at(response.getSize()).build();
			batchLinks.add(link);
			batchIt.next();
			index++;
		}
		return workflowStates.partial(response, batchesArray, batchLinks.toArray(new Link[0]));
	}
	
	private ResponseBuilder branches(final ResponseBuilder response, final String workflow, final String version, final String state, final List<ParallelStateBranch> branches, final WorkflowData workflowData) {
		final JsonArrayBuilder branchesArray = Json.createArrayBuilder();
		final List<Link> branchLinks = new ArrayList<>();
		final LinkBuilder builder = new LinkBuilder();
		int index = 0;
		final Iterator<ParallelStateBranch> branchIt = branches.iterator();
		while(branchIt.hasNext()) {
			final Link branchLink = Internal.branchLink(workflow, Optional.ofNullable(version), state, index);
			final Link link = builder.link(branchLink).at(response.getSize()).build();
			branchLinks.add(link);
			branchesArray.add(workflowData.getInput());
			branchIt.next();
			index++;
		}
		return workflowStates.partial(response, branchesArray.build(), branchLinks.toArray(new Link[0]));
	}
	
	private ResponseBuilder transitionOrEnd(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final String state, final StringOrObject<TransitionDefinition> transition, final BooleanOrObject<EndDefinition> end, final URI instance, final WorkflowData workflowData) throws Exception {
		if(transition != null && !transition.isNull()) {
			return transition(response, workflowDefinition, transition, instance, workflowData);
		}
		else if(end != null && !end.isNull()) {
			return end(response, workflowDefinition, end, instance, workflowData);
		}
		throw new BadRequestException();
	}
	
	private ResponseBuilder end(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final Either<Boolean, EndDefinition> end, final URI instance, final WorkflowData workflowData) throws Exception {
		final LinkBuilder builder = new LinkBuilder();
		if(end.isRight()) {
			final EndDefinition endDefinition = end.getRight();
			final Link endLink = Internal.endLink(workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), endDefinition.isTerminate(), endDefinition.isCompensate(), endDefinition.getContinueAs().getLeft());
			builder.link(endLink).at(response.getSize());
			response.links(builder.build());
			if(endDefinition.getProduceEvents() != null) {
				workflowEvents.produceEvents(response, workflowDefinition, endDefinition.getProduceEvents(), instance, workflowData);
			}
			if(endDefinition.getContinueAs() != null) {
				continueAs(response, endDefinition.getContinueAs(), instance, workflowData);
			}
		}
		return response;
	}
	
	private ResponseBuilder startLink(final ResponseBuilder response, final String workflow, final Optional<String> version, final URI parentInstance) {
		final LinkBuilder builder = new LinkBuilder();
		final Link startLink = Workflow.startLink(workflow, version);
		builder.link(startLink).at(response.getSize());
		response.links(builder.build());
		if(parentInstance != null) {
			response.header(LRA.LRA_HTTP_PARENT_CONTEXT_HEADER, parentInstance);
		}
		return response;
	}
	
	private ResponseBuilder continueAs(final ResponseBuilder response, final StringOrObject<ContinueAs> continueAs, final URI instance, final WorkflowData workflowData) throws Exception {
		if(continueAs.isLeft()) {
			final WorkflowDefinition workflowDefinition = workflowInstance.get(continueAs.getLeft());
			return startLink(response, workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), instance);
		}
		else if(continueAs.isRight()) {
			final ContinueAs continueAsDef = continueAs.getRight();
			final WorkflowDefinition workflowDefinition = workflowInstance.get(continueAsDef.getWorkflowId(), continueAsDef.getVersion());
			if(continueAsDef.getData() != null) {
				JsonValue output = null;
				if(continueAsDef.getData().isLeft()) {
					final String continueAsData = continueAsDef.getData().getLeft();
					output = workflowExpressions.getValue(continueAsData, workflowData.getOutput());
				}
				else if(continueAsDef.getData().isRight()) {
					output = continueAsDef.getData().getRight();
				}
				workflowData.setOutput(output);
			}
			return startLink(response, workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), instance);
		}
		throw new BadRequestException();
	}
	
	private ResponseBuilder transition(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final Either<String, TransitionDefinition> transition, final URI instance, final WorkflowData workflowData) throws Exception {
		if(transition.isLeft()) {
			final String nextState = transition.getLeft();
			return transitionLink(response, workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), nextState, false);
		}
		else if(transition.isRight()) {
			final TransitionDefinition transitionDef = transition.getRight();
			if(transitionDef.getProduceEvents() != null) {
				workflowEvents.produceEvents(response, workflowDefinition, transitionDef.getProduceEvents(), instance, workflowData);
			}
			return transitionLink(response, workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), transitionDef.getNextState(), transitionDef.isCompensate());
		}
		throw new BadRequestException();
	}
	
	private ResponseBuilder transitionCallbackState(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final CallbackState callbackState, final URI instance, final WorkflowData workflowData) throws Exception {
		final EventDefinition eventDefinition = workflowEvents.getEventDefinition(workflowDefinition, callbackState.getEventRef());
		final Map<String, Object> ext = new HashMap<>();
		final Event event = workflowEvents.newEvent(eventDefinition, instance, ext);
		final Link callbackLink = Internal.callbackLink(workflowDefinition.getId(), callbackState.getName(), Optional.ofNullable(workflowDefinition.getVersion()));
		return consumesLink(response, event, callbackLink, ext);
	}
	
	private ResponseBuilder transitionEventState(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final EventState eventState, final URI instance) throws Exception {
		final Map<String, EventDefinition> eventDefs = new HashMap<>();
		final List<EventDefinition> eventDefinitions = EitherUtil.getArray(workflowDefinition.getEvents());
		MapUtil.putAll(eventDefs, eventDefinitions.iterator(), EventDefinition::getName);
		final List<Map<String, Object>> events = new ArrayList<>();
		final List<Link> eventLinks = new ArrayList<>();
		final Link producesLink = epf.event.client.Event.producesLink();
		final Link observesLink = Internal.observesLink(workflowDefinition.getId(), eventState.getName(), Optional.ofNullable(workflowDefinition.getVersion()));
		final Link consumesLink = epf.event.client.Event.consumesLink(observesLink);
		final LinkBuilder builder = new LinkBuilder();
		for(OnEventsDefinition onEventsDef : eventState.getOnEvents()) {
			for(String eventRef : onEventsDef.getEventRefs()) {
				final EventDefinition eventDef = eventDefs.get(eventRef);
				final Map<String, Object> ext = new HashMap<>();
				final Event event = workflowEvents.newEvent(eventDef, instance, ext);
				Link eventLink = null;
				if(Kind.consumed.equals(eventDef.getKind())) {
					eventLink = builder.link(consumesLink).at(response.getSize()).build();
				}
				else if(Kind.produced.equals(eventDef.getKind())){
					eventLink = builder.link(producesLink).at(response.getSize()).build();
				}
				if(eventLink != null) {
					final Map<String, Object> map = event.toMap(ext);
					events.add(map);
					eventLinks.add(eventLink);
				}
			}
		}
		return workflowStates.partial(response, events, eventLinks.toArray(new Link[0]));
	}
	
	private ResponseBuilder transitionOperationState(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final OperationState operationState, final URI instance, final WorkflowData workflowData) throws Exception {
		actions(response, workflowDefinition.getId(), workflowDefinition.getVersion(), operationState.getName(), operationState.getActions(), workflowData);
		return transitionOrEnd(response, workflowDefinition, operationState.getName(), operationState.getTransition(), operationState.getEnd(), instance, workflowData);
	}
	
	private ResponseBuilder transitionSwitchState(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final SwitchState switchState, final URI instance, final WorkflowData workflowData) throws Exception {
		if(switchState.getDataConditions() != null) {
			for(SwitchStateDataConditions condition : switchState.getDataConditions()) {
				if(workflowExpressions.evaluateCondition(workflowData.getOutput(), condition.getCondition())) {
					return transitionOrEnd(response, workflowDefinition, switchState.getName(), condition.getTransition(), condition.getEnd(), instance, workflowData);
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
					return transitionOrEnd(response, workflowDefinition, switchState.getName(), condition.getTransition(), condition.getEnd(), instance, workflowData);
				}
			}
		}
		if(switchState.getDefaultCondition().isLeft()) {
			return transition(response, workflowDefinition, switchState.getDefaultCondition().getLeft(), instance, workflowData);
		}
		else if(switchState.getDefaultCondition().isRight()) {
			return end(response, workflowDefinition, switchState.getDefaultCondition().getRight(), instance, workflowData);
		}
		throw new BadRequestException();
	}
	
	private ResponseBuilder transitionSleepState(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final SleepState sleepState, final URI instance, final WorkflowData workflowData) throws Exception {
		final Duration duration = Duration.parse(sleepState.getDuration());
		Thread.currentThread();
		Thread.sleep(duration.getSeconds() * 1000);
		return transitionOrEnd(response, workflowDefinition, sleepState.getName(), sleepState.getTransition(), sleepState.getEnd(), instance, workflowData);
	}
	
	private ResponseBuilder transitionParallelState(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final ParallelState parallelState, final URI instance, final WorkflowData workflowData) throws Exception {
		branches(response, workflowDefinition.getId(), workflowDefinition.getVersion(), parallelState.getName(), parallelState.getBranches(), workflowData);
		return transitionOrEnd(response, workflowDefinition, parallelState.getName(), parallelState.getTransition(), parallelState.getEnd(), instance, workflowData);
	}
	
	private ResponseBuilder transitionInjectState(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final InjectState injectState, final URI instance, final WorkflowData workflowData) throws Exception {
		final JsonValue output = dataMerging.mergeStateDataOutput(workflowData.getOutput(), JsonUtil.toJsonValue(injectState.getData()));
		workflowData.setOutput(output);
		return transitionOrEnd(response, workflowDefinition, injectState.getName(), injectState.getTransition(), injectState.getEnd(), instance, workflowData);
	}
	
	private ResponseBuilder transitionForEachState(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final ForEachState forEachState, final URI instance, final WorkflowData workflowData) throws Exception {
		final JsonArray inputCollection = workflowExpressions.getValue(forEachState.getInputCollection(), workflowData.getInput()).asJsonArray();
		int batchSize = inputCollection.size();
		if(forEachState.getBatchSize().isLeft()) {
			batchSize = Integer.valueOf(forEachState.getBatchSize().getLeft());
		}
		else if(forEachState.getBatchSize().isRight()) {
			batchSize = forEachState.getBatchSize().getRight().intValue();
		}
		batches(response, workflowDefinition, forEachState.getName(), forEachState.getMode(), batchSize, inputCollection);
		return transitionOrEnd(response, workflowDefinition, forEachState.getName(), forEachState.getTransition(), forEachState.getEnd(), instance, workflowData);
	}
	
	private WorkflowDefinition getWorkflowDefinition(final String workflow, final String version) {
		Optional<WorkflowDefinition> workflowDefinition = Optional.empty();
		if(version != null) {
			workflowDefinition = Optional.ofNullable(workflowInstance.get(workflow, version));
		}
		else {
			workflowDefinition = Optional.ofNullable(workflowInstance.get(workflow));
		}
		return workflowDefinition.orElseThrow(NotFoundException::new);
	}
	
	private ResponseBuilder onEvents(final ResponseBuilder response, final Map<String, EventDefinition> eventDefinitions, final WorkflowDefinition workflowDefinition, final EventState eventState, URI uri, WorkflowData workflowData, final List<Map<String, Object>> eventDatas) throws Exception {
		final List<ActionDefinition> actions = new ArrayList<>();
		final JsonArrayBuilder actionDatas = Json.createArrayBuilder();
		boolean all = true;
		boolean any = false;
		Instance instance = null;
		for(OnEventsDefinition onEventsDefinition : eventState.getOnEvents()) {
			final List<epf.event.schema.Event> events = new ArrayList<>();
			if(workflowEvents.onEvents(workflowDefinition, eventState, onEventsDefinition, eventDefinitions, eventDatas, events)) {
				any = true;
				actions.addAll(onEventsDefinition.getActions());
				if(uri == null) {
					uri = new URI(events.get(0).getSource());
				}
				if(workflowData == null) {
					instance = workflowInstance.getInstance(uri);
					workflowData = instance.getState().getWorkflowData();
				}
				final JsonValue eventData = JsonUtil.toJsonValue(events.get(0).getData());
				eventDataFilters.filterEventDataOutput(onEventsDefinition.getEventDataFilter(), workflowData, eventData);
				actionDatas.add(eventData);
			}
			else {
				all = false;
			}
		}
		final boolean exclusive = !Boolean.FALSE.equals(eventState.isExclusive());
		boolean transition = exclusive ? any : all;
		actions(response, workflowDefinition.getId(), workflowDefinition.getVersion(), eventState.getName(), actions, workflowData);
		if(transition) {
			return transitionOrEnd(response, workflowDefinition, eventState.getName(), eventState.getTransition(), eventState.getEnd(), uri, workflowData);
		}
		return response;
	}
	
	private ResponseBuilder consumesLink(final ResponseBuilder response, final Event event, final Link eventLink, final Map<String, Object> ext) throws Exception {
		final Map<String, Object> map = event.toMap(ext);
		final LinkBuilder builder = new LinkBuilder();
		final Link consumesLink = epf.event.client.Event.consumesLink(eventLink);
		builder.link(consumesLink).at(response.getSize());
		return response.entity(map).links(builder.build());
	}
	
	private ResponseBuilder onEventsLink(final ResponseBuilder response, final URI instance, final List<Map<String, Object>> events) {
		final LinkBuilder builder = new LinkBuilder();
		final Link observesLink = epf.event.client.Event.observesLink();
		builder.link(observesLink).at(response.getSize());
		return response.entity(events);
	}
	
	private ResponseBuilder transitionState(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final State state, final URI instance, final WorkflowData workflowData) throws Exception {
		switch(state.getType_()) {
			case event:
				final EventState eventState = (EventState) state;
				workflowData.setInput(stateDataFilters.filterStateDataInput(eventState.getStateDataFilter(), workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
				transitionEventState(response, workflowDefinition, eventState, instance);
				break;
			case operation:
				final OperationState operationState = (OperationState) state;
				workflowData.setInput(stateDataFilters.filterStateDataInput(operationState.getStateDataFilter(), workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
				transitionOperationState(response, workflowDefinition, operationState, instance, workflowData);
				break;
			case Switch:
				final SwitchState switchState = (SwitchState) state;
				workflowData.setInput(stateDataFilters.filterStateDataInput(switchState.getStateDataFilter(), workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
				transitionSwitchState(response, workflowDefinition, switchState, instance, workflowData);
				workflowData.setOutput(stateDataFilters.filterStateDataOutput(switchState.getStateDataFilter(), workflowData.getOutput()));
				workflowStates.output(response, workflowData);
				break;
			case sleep:
				final SleepState sleepState = (SleepState) state;
				workflowData.setOutput(workflowData.getInput());
				transitionSleepState(response, workflowDefinition, sleepState, instance, workflowData);
				workflowStates.output(response, workflowData);
				break;
			case parallel:
				final ParallelState parallelState = (ParallelState) state;
				workflowData.setInput(stateDataFilters.filterStateDataInput(parallelState.getStateDataFilter(), workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
				transitionParallelState(response, workflowDefinition, parallelState, instance, workflowData);
				break;
			case inject:
				final InjectState injectState = (InjectState) state;
				workflowData.setInput(stateDataFilters.filterStateDataInput(injectState.getStateDataFilter(), workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
				transitionInjectState(response, workflowDefinition, injectState, instance, workflowData);
				workflowData.setOutput(stateDataFilters.filterStateDataOutput(injectState.getStateDataFilter(), workflowData.getOutput()));
				workflowStates.output(response, workflowData);
				break;
			case foreach:
				final ForEachState forEachState = (ForEachState) state;
				workflowData.setInput(stateDataFilters.filterStateDataInput(forEachState.getStateDataFilter(), workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
				transitionForEachState(response, workflowDefinition, forEachState, instance, workflowData);
				break;
			case callback:
				final CallbackState callbackState = (CallbackState) state;
				workflowData.setInput(stateDataFilters.filterStateDataInput(callbackState.getStateDataFilter(), workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
				transitionCallbackState(response, workflowDefinition, callbackState, instance, workflowData);
				break;
			default:
				throw new BadRequestException();
		}
		return response;
	}
	
	@Complete
	@Compensate
	@AfterLRA
	@RunOnVirtualThread
	@Override
	public Response end(final URI instance) throws Exception {
		workflowInstance.removeInstance(instance);
		return Response.ok().build();
	}

	@Forget
	@Leave
	@RunOnVirtualThread
	@Override
	public Response terminate(final URI uri) throws Exception {
		final Instance instance = workflowInstance.removeInstance(uri);
		final ResponseBuilder response = new ResponseBuilder();
		if(instance != null) {
			workflowStates.output(uri, response, instance.getState().getWorkflowData());
		}
		return response.build();
	}

	@LRA(value = Type.NESTED, end = false)
	@RunOnVirtualThread
	@Override
	public Response start(final String workflow, final String version, final URI instance, final InputStream body) throws Exception {
		WorkflowDefinition workflowDefinition = getWorkflowDefinition(workflow, version);
		String startState = null;
		if(workflowDefinition.getStart().isLeft()) {
			startState = workflowDefinition.getStart().getLeft();
		}
		else if(workflowDefinition.getStart().isRight()) {
			final StartDefinition startDef = workflowDefinition.getStart().getRight();
			startState = startDef.getStateName();
		}
		else {
			startState = workflowDefinition.getStates().get(0).getName();
		}
		final WorkflowData workflowData = workflowStates.input(body);
		workflowInstance.putInstance(instance, new Instance());
		final ResponseBuilder response = new ResponseBuilder();
		transitionLink(response, workflow, Optional.ofNullable(version), startState, false);
		return workflowStates.output(instance, response, workflowData);
	}
	
	@LRA(value = Type.MANDATORY, end = true)
	@RunOnVirtualThread
	@Override
	public Response end(final String workflow, final String version, final Boolean terminate, final Boolean compensate, final String continueAs, final URI uri, final InputStream body) throws Exception {
		final ResponseBuilder response = new ResponseBuilder();
		final Instance instance = workflowInstance.getInstance(uri);
		if(instance != null) {
			if(Boolean.TRUE.equals(compensate)) {
				workflowCompensation.compensates(response, workflow, Optional.ofNullable(version), instance);
			}
			return workflowStates.output(uri, response, body);
		}
		else {
			response.status(Status.NO_CONTENT);
		}
		return response.build();
	}

	@LRA(value = Type.MANDATORY, end = false, cancelOn = {Response.Status.RESET_CONTENT})
	@RunOnVirtualThread
	@Override
	public Response transition(final String workflow, final String version, final String nextState, final Boolean compensate, final URI uri, final InputStream body) throws Exception {
		final Instance instance = workflowInstance.getInstance(uri);
		final ResponseBuilder response = new ResponseBuilder();
		if(instance != null) {
			if(Boolean.TRUE.equals(compensate)) {
				workflowCompensation.compensates(response, workflow, Optional.ofNullable(version), instance);
			}
			final WorkflowDefinition workflowDefinition = getWorkflowDefinition(workflow, version);
			final State state = workflowStates.getState(workflowDefinition, nextState);
			final WorkflowData workflowData = workflowStates.input(body);
			if(instance.getState() != null) {
				final State currentState = workflowStates.getState(workflowDefinition, instance.getState().getName());
				final StateDataFilter stateDataFilter = stateDataFilters.getStateDataFilter(currentState);
				workflowData.setInput(stateDataFilters.filterStateDataInput(stateDataFilter, workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
			}
			transitionState(response, workflowDefinition, state, uri, workflowData);
			workflowStates.putState(nextState, uri, workflowData);
			return workflowStates.output(uri, response);
		}
		else {
			response.status(Status.NO_CONTENT);
		}
		return response.build();
	}

	@LRA(value = Type.MANDATORY, end = false)
	@RunOnVirtualThread
	@Override
	public Response compensate(final String workflow, final String state, final String version, final URI instance, final InputStream body) throws Exception {
		final WorkflowDefinition workflowDefinition = getWorkflowDefinition(workflow, version);
		final State currentState = workflowStates.getState(workflowDefinition, state);
		final Optional<String> compensatedBy = workflowCompensation.getCompensatedBy(currentState);
		final ResponseBuilder response = new ResponseBuilder();
		if(compensatedBy.isPresent()) {
			transitionLink(response, workflow, Optional.ofNullable(version), compensatedBy.get(), false);
		}
		return workflowStates.output(instance, response, body);
	}
	
	@RunOnVirtualThread
	@Override
	public Response observes(final String workflow, final String state, final String version, final Map<String, Object> map) throws Exception {
		final Map<String, Object> ext = new HashMap<>();
		final Event event = Event.event(map, ext); 
		final URI uri = new URI(event.getSubject());
		final Instance instance = workflowInstance.getInstance(uri);
		final ResponseBuilder response = new ResponseBuilder();
		if(instance != null) {
			final WorkflowDefinition workflowDefinition = getWorkflowDefinition(workflow, version);
			final State currentState = workflowStates.getState(workflowDefinition, state);
			final EventState eventState = (EventState)currentState;
			final Map<String, EventDefinition> eventDefinitions = workflowEvents.getEvents(workflowDefinition);
			if(Boolean.TRUE.equals(eventState.isExclusive())) {
				final List<Map<String, Object>> events = new ArrayList<>();
				events.add(map);
				onEvents(response, eventDefinitions, workflowDefinition, eventState, uri, instance.getState().getWorkflowData(), events);
			}
			else {
				final List<Map<String, Object>> events = workflowEvents.events(eventState, eventDefinitions, uri);
				onEventsLink(response, uri, events);
			}
			return workflowStates.output(uri, response);
		}
		else {
			response.status(Status.NO_CONTENT);
		}
		return response.build();
	}
	
	final ResponseBuilder actions(final ResponseBuilder response, final String workflow, final String state, final String version, final List<ActionDefinition> actionDefinitions, final WorkflowData workflowData) throws Exception {
		final JsonArrayBuilder array = Json.createArrayBuilder();
		final List<Link> actionLinks = new ArrayList<>();
		final LinkBuilder builder = new LinkBuilder();
		for(ActionDefinition actionDefinition : actionDefinitions) {
			final JsonValue actionData = actionDataFilters.filterActionDataInput(actionDefinition.getActionDataFilter(), workflowData.getInput());
			final Link actionLink = Internal.actionLink(workflow, Optional.ofNullable(version), state, actionDefinition.getName());
			final Link link = builder.link(actionLink).at(response.getSize()).build();
			actionLinks.add(link);
			array.add(actionData);
		}
		return workflowStates.partial(response, array.build(), actionLinks.toArray(new Link[0]));
	}
	
	@LRA(value = Type.MANDATORY, end = false)
	@RunOnVirtualThread
	@Override
	public Response iteration(final String workflow, final String state, final String version, final URI instance, final InputStream body) throws Exception {
		final WorkflowData workflowData = workflowStates.input(body);
		final WorkflowDefinition workflowDefinition = getWorkflowDefinition(workflow, version);
		final ForEachState forEachState = (ForEachState) workflowStates.getState(workflowDefinition, state);
		final List<ActionDefinition> actionDefinitions = forEachState.getActions();
		final ResponseBuilder response = new ResponseBuilder();;
		actions(response, workflow, version, state, actionDefinitions, workflowData);
		return workflowStates.output(instance, response);
	}
	
	@LRA(value = Type.MANDATORY, end = false)
	@RunOnVirtualThread
	@Override
	public Response branch(final String workflow, final String state, final String version, final int at, final URI instance, final InputStream body) throws Exception {
		final WorkflowDefinition workflowDefinition = getWorkflowDefinition(workflow, version);
		final ParallelState parallelState = (ParallelState) workflowStates.getState(workflowDefinition, state);
		final ParallelStateBranch branch = parallelState.getBranches().get(at);
		final ResponseBuilder response = new ResponseBuilder();
		final WorkflowData workflowData = workflowStates.input(body);
		actions(response, workflow, version, state, branch.getActions(), workflowData);
		return workflowStates.output(instance, response, workflowData);
	}

	@RunOnVirtualThread
	@Override
	public Response callback(final String workflow, final String state, final String version, final Map<String, Object> map) throws Exception {
		final Map<String, Object> ext = new HashMap<>();
		final epf.event.schema.Event event = epf.event.schema.Event.event(map, ext);
		final URI uri = new URI(event.getSubject());
		final Instance instance = workflowInstance.getInstance(uri);
		final ResponseBuilder response = new ResponseBuilder();
		if(instance != null) {
			final WorkflowDefinition workflowDefinition = getWorkflowDefinition(workflow, version);
			final CallbackState callbackState = (CallbackState) workflowStates.getState(workflowDefinition, state);
			final ActionDefinition actionDefinition = callbackState.getAction();
			final WorkflowData workflowData = workflowStates.input(event);
			action(response, workflowDefinition, state, actionDefinition, uri, workflowData);
			return workflowStates.output(uri, response);
		}
		else {
			response.status(Status.NO_CONTENT);
		}
		return response.build();
	}
	
	private ResponseBuilder iterations(final ResponseBuilder response, final String workflow, final String version, final String state, final boolean synchronized_, final JsonArray inputCollection) {
		final Link iterationLink = Internal.iterationLink(workflow, Optional.ofNullable(version), state);
		final LinkBuilder builder = new LinkBuilder();
		final Iterator<JsonValue> iterator = inputCollection.iterator();
		final List<Link> iterationLinks = new ArrayList<>();
		while(iterator.hasNext()) {
			final Link link = builder.link(iterationLink).at(response.getSize()).build();
			iterationLinks.add(link);
			iterator.next();
		}
		return workflowStates.partial(response, inputCollection, iterationLinks.toArray(new Link[0]));
	}

	@LRA(value = Type.MANDATORY, end = false)
	@RunOnVirtualThread
	@Override
	public Response batch(final String workflow, final String state, final String version, final URI instance, final InputStream body) throws Exception {
		final ResponseBuilder response = new ResponseBuilder();
		final JsonArray inputCollection = JsonUtil.readArray(body);
		iterations(response, workflow, version, state, false, inputCollection);
		return workflowStates.output(instance, response);
	}

	@RunOnVirtualThread
	@Override
	public Response observes(final String workflow, final String state, final String action, final String version, final Map<String, Object> map) throws Exception {
		final ResponseBuilder response = new ResponseBuilder();
		return response.build();
	}

	@RunOnVirtualThread
	@Override
	public Response onEvents(final String workflow, final String state, final String version, final List<Map<String, Object>> events) throws Exception {
		final ResponseBuilder response = new ResponseBuilder();
		final WorkflowDefinition workflowDefinition = getWorkflowDefinition(workflow, version);
		final State currentState = workflowStates.getState(workflowDefinition, state);
		final EventState eventState = (EventState)currentState;
		final Map<String, EventDefinition> eventDefinitions = workflowEvents.getEvents(workflowDefinition);
		onEvents(response, eventDefinitions, workflowDefinition, eventState, null, null, events);
		return response.build();
	}
}
