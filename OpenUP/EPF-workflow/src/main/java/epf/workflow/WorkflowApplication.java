package epf.workflow;

import java.io.InputStream;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.POST;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.validation.Validator;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Forget;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA.Type;
import org.eclipse.microprofile.lra.annotation.ws.rs.Leave;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.PathItem;
import org.eclipse.microprofile.openapi.models.parameters.Parameter;
import org.eclipse.microprofile.openapi.models.servers.Server;
import epf.naming.Naming;
import epf.util.MapUtil;
import epf.util.json.ext.JsonUtil;
import epf.workflow.action.Action;
import epf.workflow.event.Event;
import epf.workflow.event.persistence.CallbackStateEvent;
import epf.workflow.event.persistence.EventStateEvent;
import epf.workflow.function.openapi.OpenAPIUtil;
import epf.workflow.schema.ContinueAs;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.SubFlowRefDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.action.ActionDataFilters;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.auth.AuthDefinition;
import epf.workflow.schema.auth.BearerPropertiesDefinition;
import epf.workflow.schema.event.EventDefinition;
import epf.workflow.schema.event.OnEventsDefinition;
import epf.workflow.schema.event.ProducedEventDefinition;
import epf.workflow.schema.function.FunctionDefinition;
import epf.workflow.schema.function.FunctionRefDefinition;
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
import epf.workflow.schema.util.StringOrObject;

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
	private transient Map<String, OpenAPI> openAPIs = new ConcurrentHashMap<>();
	
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
	
	private ResponseBuilder transitionLink(final String workflow, final Optional<String> version, final String state, final URI instance, final Link[] compensateLinks) {
		return Response.ok()
				.links(compensateLinks)
				.links(WorkflowLink.transitionLink(compensateLinks.length, workflow, version, state))
				.header(LRA.LRA_HTTP_CONTEXT_HEADER, instance);
	}
	
	private ResponseBuilder operationLink(final String workflow, final String version, final String state, final URI instance) {
		return Response.ok()
				.links(WorkflowLink.getActionsLink(0, workflow, Optional.ofNullable(version), state))
				.links(WorkflowLink.actionsLink(1, workflow, Optional.ofNullable(version), state))
				.header(LRA.LRA_HTTP_CONTEXT_HEADER, instance);
	}
	
	private ResponseBuilder endLink(final String workflow, final Optional<String> version, final URI instance, final Link[] compensateLinks) {
		return Response.ok()
				.links(compensateLinks)
				.links(WorkflowLink.endLink(compensateLinks.length, workflow, version))
				.header(LRA.LRA_HTTP_CONTEXT_HEADER, instance);
	}
	
	private ResponseBuilder terminateLink(final String workflow, final Optional<String> version, final URI instance) {
		return Response.ok()
				.links(WorkflowLink.terminateLink(0, workflow, version))
				.header(LRA.LRA_HTTP_CONTEXT_HEADER, instance);
	}
	
	private ResponseBuilder scheduleLink(final StartDefinition startDefinition, final WorkflowDefinition workflowDefinition) {
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
			scheduleLink = WorkflowLink.scheduleLink(0, Naming.WORKFLOW, HttpMethod.PUT, path + "?version=" + workflowDefinition.getVersion(), recurringTimeInterval);
		}
		else {
			scheduleLink = WorkflowLink.scheduleLink(0, Naming.WORKFLOW, HttpMethod.PUT, path, recurringTimeInterval);
		}
		return Response.ok().links(scheduleLink);
	}
	
	private ResponseBuilder getWorkflowLink(final WorkflowDefinition workflowDefinition) {
		final Link link = WorkflowLink.getWorkflowLink(0, workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()));
		return Response.ok().links(link);
	}
	
	private void putState(final String state, final URI instance, final WorkflowData workflowData) {
		final WorkflowInstance workflowInstance = cache.getInstance(instance);
		final WorkflowState workflowState = workflowInstance.getState();
		final WorkflowState newWorkflowState = new WorkflowState();
		newWorkflowState.setPreviousState(workflowState);
		newWorkflowState.setName(state);
		newWorkflowState.setWorkflowData(workflowData);
		workflowInstance.setState(newWorkflowState);
		cache.replaceInstance(instance, workflowInstance);
	}
	
	private State getState(final WorkflowDefinition workflowDefinition, final String name) {
		return workflowDefinition.getStates().stream().filter(state -> state.getName().equals(name)).findFirst().orElseThrow(NotFoundException::new);
	}
	
	private JsonValue filterStateDataInput(final StateDataFilters stateDataFilters, final JsonValue input) throws Exception {
		if(stateDataFilters != null && stateDataFilters.getInput() != null) {
			return ELUtil.getValue(stateDataFilters.getInput(), input);
		}
		return input;
	}
	
	private JsonValue filterActionDataInput(final ActionDataFilters actionDataFilters, final JsonValue input) throws Exception {
		if(actionDataFilters != null && actionDataFilters.getFromStateData() != null) {
			return ELUtil.getValue(actionDataFilters.getFromStateData(), input);
		}
		return input;
	}
	
	private EventDefinition getEventDefinition(final WorkflowDefinition workflowDefinition, final String event) {
		final List<EventDefinition> events = EitherUtil.getArray(workflowDefinition.getEvents());
		return events.stream().filter(eventDef -> eventDef.getName().equals(event)).findFirst().get();
	}
	
	private ActionDefinition getActionDefinition(final State state, final String action) {
		Optional<ActionDefinition> actionDefinition = Optional.empty();
		List<ActionDefinition> actionDefinitions = null;
		switch(state.getType_()) {
			case Switch:
				break;
			case callback:
				break;
			case event:
				break;
			case foreach:
				final ForEachState forEachState = (ForEachState) state;
				actionDefinitions = forEachState.getActions();
				break;
			case inject:
				break;
			case operation:
				final OperationState operationState = (OperationState) state;
				actionDefinitions = operationState.getActions();
				break;
			case parallel:
				break;
			case sleep:
				break;
			default:
				break;
		}
		if(actionDefinitions != null) {
			actionDefinition = actionDefinitions.stream().filter(actionDef -> actionDef.getName().equals(action)).findFirst();
		}
		return actionDefinition.orElseThrow(NotFoundException::new);
	}
	
	private WorkflowDefinition getSubWorkflowDefinition(final StringOrObject<SubFlowRefDefinition> subFlowRef) {
		if(subFlowRef.isLeft()) {
			return cache.get(subFlowRef.getLeft());
		}
		else if(subFlowRef.isRight()) {
			final SubFlowRefDefinition subFlowRefDefinition = subFlowRef.getRight();
			return cache.get(subFlowRefDefinition.getWorkflowId(), subFlowRefDefinition.getVersion());
		}
		return null;
	}
	
	private Action newAction(final WorkflowDefinition workflowDefinition, final ActionDefinition actionDefinition, final WorkflowData workflowData) throws Exception {
		Action action = null;
		filterActionDataInput(actionDefinition.getActionDataFilter(), workflowData.getInput());
		if(!actionDefinition.getFunctionRef().isNull()) {
		}
		else if(actionDefinition.getEventRef() != null) {
			getEventDefinition(workflowDefinition, actionDefinition.getEventRef().getProduceEventRef());
		}
		else if(!actionDefinition.getSubFlowRef().isNull()) {
			getSubWorkflowDefinition(actionDefinition.getSubFlowRef());
			if(actionDefinition.getSubFlowRef().isRight()) {
				actionDefinition.getSubFlowRef().getRight();
			}
		}
		return action;
	}
	
	private Response invoke(final WorkflowDefinition workflowDefinition, final FunctionDefinition functionDefinition, final JsonValue data, final OpenAPI openAPI, final String path, final PathItem pathItem, final org.eclipse.microprofile.openapi.models.PathItem.HttpMethod httpMethod, final Operation operation) throws Exception {
		Server server = openAPI.getServers().iterator().next();
		if(operation.getServers() != null && !operation.getServers().isEmpty()) {
			server = operation.getServers().iterator().next();
		}
		else if(pathItem.getServers() != null && !pathItem.getServers().isEmpty()) {
			server = pathItem.getServers().iterator().next();
		}
		final JsonObject input = data.asJsonObject();
		try(Client client = ClientBuilder.newClient()){
			WebTarget target = client.target(server.getUrl()).path(path);
			for(Parameter parameter : operation.getParameters()) {
				switch(parameter.getIn()) {
					case COOKIE:
						break;
					case HEADER:
						break;
					case PATH:
						final Object pathValue = JsonUtil.asValue(input.get(parameter.getName()));
						target = target.resolveTemplate("{" + parameter.getName() + "}", pathValue);
						break;
					case QUERY:
						final Object queryValue = JsonUtil.asValue(input.get(parameter.getName()));
						target = target.queryParam(parameter.getName(), queryValue);
						break;
					default:
						break;
				}
			}
			Builder builder = target.request();
			if(functionDefinition.getAuthRef() != null) {
				final List<AuthDefinition> authDefs = EitherUtil.getArray(workflowDefinition.getAuth());
				for(AuthDefinition authDef : authDefs) {
					if(authDef.getName().equals(functionDefinition.getAuthRef())) {
						switch(authDef.getScheme()) {
							case basic:
								break;
							case bearer:
								final BearerPropertiesDefinition bearerDefinition = (BearerPropertiesDefinition) authDef.getProperties();
								builder = builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + bearerDefinition.getToken());
								break;
							case oauth2:
								break;
							default:
								break;
						}
						break;
					}
				}
			}
			for(Parameter parameter : operation.getParameters()) {
				switch(parameter.getIn()) {
					case COOKIE:
						final String cookieValue = input.getString(parameter.getName());
						builder = builder.cookie(parameter.getName(), cookieValue);
						break;
					case HEADER:
						final Object headerValue = JsonUtil.asValue(input.get(parameter.getName()));
						builder = builder.header(parameter.getName(), headerValue);
						break;
					case PATH:
						break;
					case QUERY:
						break;
					default:
						break;
				}
			}
			Entity<?> entity = null;
			if(operation.getRequestBody() != null) {
				final String[] mediaTypes = operation.getRequestBody().getContent().getMediaTypes().keySet().toArray(new String[0]);
				builder = builder.accept(mediaTypes);
				entity = Entity.entity(data.toString(), mediaTypes[0]);
			}
			Invocation invoke;
			if(entity != null) {
				invoke = builder.build(httpMethod.name(), entity);
			}
			else {
				invoke = builder.build(httpMethod.name());
			}
			return invoke.invoke();
		}
	}
	
	private Response openapi(final WorkflowDefinition workflowDefinition, final FunctionDefinition functionDefinition, final JsonValue data) throws Exception {
		final int index = functionDefinition.getOperation().indexOf("#");
		final String pathToOpenAPIDefinition = functionDefinition.getOperation().substring(0, index);
		final String operationId = functionDefinition.getOperation().substring(index + 1);
		if(!openAPIs.containsKey(pathToOpenAPIDefinition)) {
			final URI uri = URI.create(pathToOpenAPIDefinition);
			final OpenAPI openAPI = OpenAPIUtil.read(uri.toURL());
			openAPIs.put(pathToOpenAPIDefinition, openAPI);
		}
		final OpenAPI openAPI = openAPIs.get(pathToOpenAPIDefinition);
		for(Entry<String, PathItem> pathItemEntry : openAPI.getPaths().getPathItems().entrySet()) {
			final String path = pathItemEntry.getKey();
			final PathItem pathItem = pathItemEntry.getValue();
			for(Entry<org.eclipse.microprofile.openapi.models.PathItem.HttpMethod, Operation> operationEntry : pathItem.getOperations().entrySet()) {
				final org.eclipse.microprofile.openapi.models.PathItem.HttpMethod httpMethod = operationEntry.getKey();
				final Operation operation = operationEntry.getValue();
				if(operation.getOperationId().equals(operationId)) {
					return invoke(workflowDefinition, functionDefinition, data, openAPI, path, pathItem, httpMethod, operation);
				}
			}
		}
		throw new BadRequestException();
	}
	
	private Response output(final ResponseBuilder response, final WorkflowData workflowData) {
		return response.entity(workflowData.getOutput()).type(MediaType.APPLICATION_JSON).build();
	}
	
	private Response output(final ResponseBuilder response, final InputStream body) {
		return response.entity(body).type(MediaType.APPLICATION_JSON).build();
	}
	
	private ResponseBuilder function(final WorkflowDefinition workflowDefinition, final ActionDefinition actionDefinition, final FunctionDefinition functionDefinition, final WorkflowData workflowData) throws Exception {
		switch(functionDefinition.getType()) {
			case asyncapi:
				break;
			case custom:
				break;
			case expression:
				break;
			case graphql:
				break;
			case odata:
				break;
			case openapi:
				final Response respone = openapi(workflowDefinition, functionDefinition, workflowData.getInput());
				return Response.fromResponse(respone);
			case rest:
				break;
			case rpc:
				break;
			default:
				break;
		}
		return Response.ok();
	}
	
	private ResponseBuilder event(final ActionDefinition actionDefinition, final EventDefinition eventDefinition, final JsonValue input) throws Exception {
		Object data = null;
		if(actionDefinition.getEventRef().getData() != null) {
			if(actionDefinition.getEventRef().getData().isLeft()) {
				data = ELUtil.getValue(actionDefinition.getEventRef().getData().getLeft(), input);
			}
			else {
				data = input;
			}
		}
		final Event event = new Event();
		event.setSource(eventDefinition.getSource());
		event.setType(eventDefinition.getType());
		event.setData(data);
		producedEvent.fire(event);
		return Response.ok();
	}
	
	private ResponseBuilder subFlow() {
		return null;
	}
	
	private ResponseBuilder action(final WorkflowDefinition workflowDefinition, final ActionDefinition actionDefinition, final WorkflowData workflowData) throws Exception {
		ResponseBuilder response = null;
		if(!actionDefinition.getFunctionRef().isNull()) {
			final FunctionDefinition functionDefinition = getFunctionDefinition(workflowDefinition, actionDefinition.getFunctionRef());
			response = function(workflowDefinition, actionDefinition, functionDefinition, workflowData);
		}
		else if(actionDefinition.getEventRef() != null) {
			final EventDefinition eventDefinition = getEventDefinition(workflowDefinition, actionDefinition.getEventRef().getProduceEventRef());
			response = event(actionDefinition, eventDefinition, workflowData.getInput());
		}
		else if(!actionDefinition.getSubFlowRef().isNull()) {
			getSubWorkflowDefinition(actionDefinition.getSubFlowRef());
			if(actionDefinition.getSubFlowRef().isRight()) {
				actionDefinition.getSubFlowRef().getRight();
			}
			response = subFlow();
		}
		return response;
	}
	
	private JsonValue filterActionDataOutput(final ActionDataFilters actionDataFilters, final WorkflowData workflowData, final WorkflowData actionData) throws Exception {
		JsonValue output = workflowData.getOutput();
		if(actionDataFilters != null && actionDataFilters.isUseResults() && actionDataFilters.getResults() != null) {
			if(actionDataFilters.getToStateData() != null) {
				output = StateUtil.mergeStateDataOutput(actionDataFilters.getToStateData(), workflowData.getOutput(), actionData.getOutput());
			}
			else {
				output = StateUtil.mergeStateDataOutput(workflowData.getOutput(), actionData.getOutput());
			}
		}
		return output;
	}
	
	private JsonValue filterStateDataOutput(final StateDataFilters stateDataFilters, final JsonValue output) throws Exception {
		if(stateDataFilters != null && stateDataFilters.getOutput() != null) {
			return ELUtil.getValue(stateDataFilters.getOutput(), output);
		}
		return output;
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
	
	private Link[] compensateLinks(final String workflow, final Optional<String> version, final URI instance){
		final List<Link> compensateLinks = new ArrayList<>();
		final WorkflowInstance workflowInstance = cache.getInstance(instance);
		WorkflowState workflowState = workflowInstance.getState();
		int index = 0;
		while(workflowState != null) {
			final Link compensateLink = WorkflowLink.compensateLink(index, workflow, version, workflowState.getName());
			compensateLinks.add(compensateLink);
			workflowState = workflowState.getPreviousState();
		}
		return compensateLinks.toArray(new Link[0]);
	}
	
	private Link[] actionLinks(final WorkflowDefinition workflowDefinition, final String state, final List<ActionDefinition> actionDefinitions, final Optional<Duration> actionExecTimeout, final boolean useResults){
		final List<Link> actionLinks = new ArrayList<>();
		int index = 0;
		for(ActionDefinition actionDefinition : actionDefinitions) {
			final Link actionLink = WorkflowLink.actionLink(index, workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), state, actionDefinition.getName(), actionExecTimeout, useResults);
			actionLinks.add(actionLink);
			index++;
		}
		return actionLinks.toArray(new Link[0]);
	}
	
	private ResponseBuilder actionsLink(final URI instance, final Link[] actionLinks) {
		return Response
				.status(Status.PARTIAL_CONTENT)
				.links(actionLinks)
				.header(LRA.LRA_HTTP_CONTEXT_HEADER, instance);
	}
	
	private ResponseBuilder end(final WorkflowDefinition workflowDefinition, final Either<Boolean, EndDefinition> end, final URI instance, final WorkflowData workflowData) throws Exception {
		if(end.isRight()) {
			final EndDefinition endDefinition = end.getRight();
			if(endDefinition.isCompensate()) {
				final Link[] compensateLinks = compensateLinks(workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), instance);
				return endLink(workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), instance, compensateLinks);
			}
			if(Boolean.TRUE.equals(endDefinition.isTerminate())) {
				return terminateLink(workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), instance);
			}
			if(endDefinition.getProduceEvents() != null) {
				produceEvents(workflowDefinition, endDefinition.getProduceEvents(), instance);
			}
			if(endDefinition.getContinueAs() != null) {
				return continueAs(endDefinition.getContinueAs(), instance, workflowData);
			}
		}
		return endLink(workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), instance, new Link[0]);
	}
	
	private ResponseBuilder startLink(final String workflow, final Optional<String> version, final URI parentInstance) {
		ResponseBuilder builder = Response.ok().links(WorkflowLink.startLink(0, workflow, version));
		if(parentInstance != null) {
			builder = builder.header(LRA.LRA_HTTP_PARENT_CONTEXT_HEADER, parentInstance);
		}
		return builder;
	}
	
	private ResponseBuilder endCondition(final WorkflowDefinition workflowDefinition, final SwitchState switchState, final SwitchStateConditions condition, final URI instance, final WorkflowData workflowData) throws Exception {
		if(condition.getTransition() != null) {
			return transition(workflowDefinition, condition.getTransition(), instance);
		}
		else if(condition.getEnd() != null) {
			return end(workflowDefinition, condition.getEnd(), instance, workflowData);
		}
		throw new BadRequestException();
	}
	
	private ResponseBuilder continueAs(final StringOrObject<ContinueAs> continueAs, final URI instance, final WorkflowData workflowData) throws Exception {
		if(continueAs.isLeft()) {
			final WorkflowDefinition workflowDefinition = cache.get(continueAs.getLeft());
			return startLink(workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), instance);
		}
		else if(continueAs.isRight()) {
			final ContinueAs continueAsDef = continueAs.getRight();
			final WorkflowDefinition workflowDefinition = cache.get(continueAsDef.getWorkflowId(), continueAsDef.getVersion());
			if(continueAsDef.getData() != null) {
				JsonValue output = null;
				if(continueAsDef.getData().isLeft()) {
					final String continueAsData = continueAsDef.getData().getLeft();
					output = ELUtil.getValue(continueAsData, workflowData.getOutput());
				}
				else if(continueAsDef.getData().isRight()) {
					output = continueAsDef.getData().getRight();
				}
				workflowData.setOutput(output);
			}
			return startLink(workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), instance);
		}
		throw new BadRequestException();
	}
	
	private Branch newBranch(final WorkflowDefinition workflowDefinition, final List<ActionDefinition> actionDefinitions, final WorkflowData workflowData) throws Exception {
		final List<Action> actions = new ArrayList<>();
		for(ActionDefinition actionDefinition : actionDefinitions) {
			final Action action = newAction(workflowDefinition, actionDefinition, workflowData);
			actions.add(action);
		}
		return new Branch(actions.toArray(new Action[0]), workflowData, null);
	}
	
	private Branch newForEachBranch(final WorkflowDefinition workflowDefinition, final ForEachState forEachState, final URI instance, final WorkflowData workflowData, final JsonValue value) throws Exception {
		final WorkflowData newWorkflowData = new WorkflowData();
		newWorkflowData.setInput(workflowData.getOutput());
		ELUtil.setValue(forEachState.getIterationParam(), newWorkflowData.getInput(), value);
		return newBranch(workflowDefinition, forEachState.getActions(), newWorkflowData);
	}
	
	private Branch newParallelBranch(final WorkflowDefinition workflowDefinition, final ParallelStateBranch branch, final URI instance, final WorkflowData workflowData) throws Exception {
		final WorkflowData newWorkflowData = new WorkflowData();
		newWorkflowData.setInput(workflowData.getOutput());
		return newBranch(workflowDefinition, branch.getActions(), newWorkflowData);
	}
	
	private Optional<String> getCompensatedBy(final State state) {
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
		return Optional.ofNullable(compensatedBy);
	}
	
	private ResponseBuilder transition(final WorkflowDefinition workflowDefinition, final Either<String, TransitionDefinition> transition, final URI instance) throws Exception {
		if(transition.isLeft()) {
			final String state = transition.getLeft();
			return transitionLink(workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), state, instance, new Link[0]);
		}
		else if(transition.isRight()) {
			final TransitionDefinition transitionDef = transition.getRight();
			if(transitionDef.isCompensate()) {
				final Link[] compensateLinks = compensateLinks(workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), instance);
				return transitionLink(workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), transitionDef.getNextState(), instance, compensateLinks);
			}
			produceEvents(workflowDefinition, transitionDef.getProduceEvents(), instance);
			return transitionLink(workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), transitionDef.getNextState(), instance, new Link[0]);
		}
		throw new BadRequestException();
	}
	
	private ResponseBuilder transitionCallbackState(final WorkflowDefinition workflowDefinition, final CallbackState callbackState, final URI instance, final WorkflowData workflowData) throws Exception {
		final EventDefinition eventDefinition = getEventDefinition(workflowDefinition, callbackState.getEventRef());
		
		final CallbackStateEvent event = new CallbackStateEvent();
		event.setWorkflowDefinition(workflowDefinition.getId());
		event.setEventDefinition(callbackState.getEventRef());
		event.setState(callbackState.getName());
		
		event.setSource(eventDefinition.getSource());
		event.setType(eventDefinition.getType());
		event.setSubject(instance.toString());
		
		eventStore.persist(event);
		return Response.ok();
	}
	
	private ResponseBuilder transitionEventState(final WorkflowDefinition workflowDefinition, final EventState eventState, final URI instance) throws Exception {
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
		return Response.ok();
	}
	
	private ResponseBuilder transitionOperationState(final WorkflowDefinition workflowDefinition, final OperationState operationState, final URI instance, final WorkflowData workflowData) throws Exception {
		return operationLink(workflowDefinition.getId(), workflowDefinition.getVersion(), operationState.getName(), instance);
	}
	
	private ResponseBuilder transitionSwitchState(final WorkflowDefinition workflowDefinition, final SwitchState switchState, final URI instance, final WorkflowData workflowData) throws Exception {
		if(switchState.getDataConditions() != null) {
			for(SwitchStateDataConditions condition : switchState.getDataConditions()) {
				if(ELUtil.evaluateCondition(workflowData.getOutput(), condition.getCondition())) {
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
			return transition(workflowDefinition, switchState.getDefaultCondition().getLeft(), instance);
		}
		else if(switchState.getDefaultCondition().isRight()) {
			return end(workflowDefinition, switchState.getDefaultCondition().getRight(), instance, workflowData);
		}
		throw new BadRequestException();
	}
	
	private ResponseBuilder transitionSleepState(final WorkflowDefinition workflowDefinition, final SleepState sleepState, final URI instance, final WorkflowData workflowData) throws Exception {
		final Duration duration = Duration.parse(sleepState.getDuration());
		Thread.currentThread();
		Thread.sleep(duration.getSeconds() * 1000);
		if(!sleepState.getTransition().isNull()) {
			return transition(workflowDefinition, sleepState.getTransition(), instance);
		}
		else if(!sleepState.getEnd().isNull()) {
			return end(workflowDefinition, sleepState.getEnd(), instance, workflowData);
		}
		throw new BadRequestException();
	}
	
	private ResponseBuilder transitionParallelState(final WorkflowDefinition workflowDefinition, final ParallelState parallelState, final URI instance, final WorkflowData workflowData) throws Exception {
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
		if(!parallelState.getTransition().isNull()) {
			return transition(workflowDefinition, parallelState.getTransition(), instance);
		}
		else if(!parallelState.getEnd().isNull()) {
			return end(workflowDefinition, parallelState.getEnd(), instance, workflowData);
		}
		throw new BadRequestException();
	}
	
	private ResponseBuilder transitionInjectState(final WorkflowDefinition workflowDefinition, final InjectState injectState, final URI instance, final WorkflowData workflowData) throws Exception {
		final JsonValue output = StateUtil.mergeStateDataOutput(workflowData.getOutput(), JsonUtil.toJsonValue(injectState.getData()));
		workflowData.setOutput(output);
		if(!injectState.getTransition().isNull()) {
			return transition(workflowDefinition, injectState.getTransition(), instance);
		}
		else if(!injectState.getEnd().isNull()) {
			return end(workflowDefinition, injectState.getEnd(), instance, workflowData);
		}
		throw new BadRequestException();
	}
	
	private ResponseBuilder transitionForEachState(final WorkflowDefinition workflowDefinition, final ForEachState forEachState, final URI instance, final WorkflowData workflowData) throws Exception {
		final JsonArray inputCollection = ELUtil.getValue(forEachState.getInputCollection(), workflowData.getOutput()).asJsonArray();
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
					final List<JsonValue> batchInputs = inputCollection.subList(index, Math.min(index + batchSize, inputCollection.size()));
					if(batchInputs.isEmpty()) {
						break;
					}
					final List<Branch> batch = new ArrayList<>();
					for(JsonValue value : batchInputs) {
						batch.add(newForEachBranch(workflowDefinition, forEachState, instance, workflowData, value));
					}
					executor.invokeAll(batch);
					index += batchInputs.size();
				}
				break;
			case sequential:
				iterations = new ArrayList<>();
				for(JsonValue value : inputCollection) {
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
				filterActionDataOutput(action.getActionDefinition().getActionDataFilter(), forEach.getWorkflowData(), action.getWorkflowData());
			}
		}
		for(Branch forEach : iterations) {
			ELUtil.setValue(forEachState.getOutputCollection(), workflowData.getOutput(), forEach.getWorkflowData().getOutput());
		}
		if(!forEachState.getTransition().isNull()) {
			return transition(workflowDefinition, forEachState.getTransition(), instance);
		}
		else if(!forEachState.getEnd().isNull()) {
			return end(workflowDefinition, forEachState.getEnd(), instance, workflowData);
		}
		throw new BadRequestException();
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
	
	private FunctionDefinition getFunctionDefinition(final WorkflowDefinition workflowDefinition, final StringOrObject<FunctionRefDefinition> functionRef) {
		Optional<FunctionDefinition> functionDefinition = Optional.empty();
		final List<FunctionDefinition> functionDefinitions = EitherUtil.getArray(workflowDefinition.getFunctions());
		if(functionRef.isLeft()) {
			functionDefinition = functionDefinitions.stream().filter(func -> func.getName().equals(functionRef.getLeft())).findFirst();
		}
		else if(functionRef.isRight()) {
			final FunctionRefDefinition functionRefDefinition = functionRef.getRight();
			functionDefinition = functionDefinitions.stream().filter(func -> func.getName().equals(functionRefDefinition.getRefName())).findFirst();
		}
		return functionDefinition.orElseThrow(BadRequestException::new);
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
			throw new BadRequestException();
		}
		if(!validator.validate(workflowDefinition).isEmpty()) {
			throw new BadRequestException();
		}
		final WorkflowDefinition newWorkflowDefinition = persistence.persist(workflowDefinition);
		if(workflowDefinition.getVersion() != null) {
			cache.put(newWorkflowDefinition.getId(), workflowDefinition.getVersion(), workflowDefinition);
		}
		else {
			cache.put(newWorkflowDefinition.getId(), workflowDefinition);
		}
		if(workflowDefinition.getStart().isRight()) {
			return scheduleLink(workflowDefinition.getStart().getRight(), workflowDefinition).build();
		}
		return getWorkflowLink(newWorkflowDefinition).build();
	}

	@GET
	@Path("{workflow}")
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response getWorkflowDefinition(
			@PathParam("workflow")
			final String workflow, 
			@QueryParam("version")
			final String version) throws Exception {
		final WorkflowDefinition workflowDefinition = findWorkflowDefinition(workflow, version);
		try(Jsonb jsonb = JsonbBuilder.create()){
			return Response.ok(jsonb.toJson(workflowDefinition), MediaType.APPLICATION_JSON).build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Compensate
	@AfterLRA
	@RunOnVirtualThread
	public Response end(
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance) throws Exception {
		cache.removeInstance(instance);
		return Response.ok().build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Leave
	@Forget
	@RunOnVirtualThread
	public Response terminate(
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance) throws Exception {
		final WorkflowInstance workflowInstance = cache.removeInstance(instance);
		if(workflowInstance != null) {
			return output(Response.ok(), workflowInstance.getState().getWorkflowData());
		}
		else {
			return Response.ok().build();
		}
	}

	@POST
	@Path("{workflow}")
	@LRA(value = Type.NESTED, end = false)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response start(
			@PathParam("workflow")
			final String workflow, 
			@QueryParam("version")
			final String version, 
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance, 
			final InputStream body) throws Exception {
		WorkflowDefinition workflowDefinition = findWorkflowDefinition(workflow, version);
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
		cache.putInstance(instance, new WorkflowInstance());
		final ResponseBuilder response = transitionLink(workflow, Optional.ofNullable(version), startState, instance, new Link[0]);
		return output(response, body);
	}
	
	@PUT
	@Path("{workflow}")
	@LRA(value = Type.MANDATORY, end = true)
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response end(
			@PathParam("workflow")
			final String workflow,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final InputStream body) throws Exception {
		return output(Response.ok(), body);
	}

	@POST
	@Path("{workflow}/{state}")
	@LRA(value = Type.MANDATORY, end = false, cancelOn = {
			Response.Status.RESET_CONTENT
	})
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response transition(
			@PathParam("workflow")
			final String workflow,  
			@PathParam("state")
			final String state,
			@QueryParam("version")
			final String version,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance, 
			final InputStream body) throws Exception {
		final WorkflowInstance workflowInstance = cache.getInstance(instance);
		ResponseBuilder response;
		if(workflowInstance != null) {
			final WorkflowDefinition workflowDefinition = findWorkflowDefinition(workflow, version);
			final State currentState = getState(workflowDefinition, state);
			StateDataFilters stateDataFilters = null;
			final WorkflowData workflowData = new WorkflowData();
			workflowData.setInput(JsonUtil.readValue(body));
			switch(currentState.getType_()) {
				case event:
					final EventState eventState = (EventState)currentState;
					stateDataFilters = eventState.getStateDataFilter();
					workflowData.setInput(filterStateDataInput(stateDataFilters, workflowData.getInput()));
					workflowData.setOutput(workflowData.getInput());
					response = transitionEventState(workflowDefinition, eventState, instance);
					break;
				case operation:
					final OperationState operationState = (OperationState) currentState;
					stateDataFilters = operationState.getStateDataFilter();
					workflowData.setInput(filterStateDataInput(stateDataFilters, workflowData.getInput()));
					workflowData.setOutput(workflowData.getInput());
					response = transitionOperationState(workflowDefinition, operationState, instance, workflowData);
					break;
				case Switch:
					final SwitchState switchState = (SwitchState) currentState;
					stateDataFilters = switchState.getStateDataFilter();
					workflowData.setInput(filterStateDataInput(stateDataFilters, workflowData.getInput()));
					workflowData.setOutput(workflowData.getInput());
					response = transitionSwitchState(workflowDefinition, switchState, instance, workflowData);
					break;
				case sleep:
					final SleepState sleepState = (SleepState) currentState;
					workflowData.setOutput(workflowData.getInput());
					response = transitionSleepState(workflowDefinition, sleepState, instance, workflowData);
					break;
				case parallel:
					final ParallelState parallelState = (ParallelState) currentState;
					stateDataFilters = parallelState.getStateDataFilter();
					workflowData.setInput(filterStateDataInput(stateDataFilters, workflowData.getInput()));
					workflowData.setOutput(workflowData.getInput());
					response = transitionParallelState(workflowDefinition, parallelState, instance, workflowData);
					break;
				case inject:
					final InjectState injectState = (InjectState) currentState;
					stateDataFilters = injectState.getStateDataFilter();
					workflowData.setInput(filterStateDataInput(stateDataFilters, workflowData.getInput()));
					workflowData.setOutput(workflowData.getInput());
					response = transitionInjectState(workflowDefinition, injectState, instance, workflowData);
					break;
				case foreach:
					final ForEachState forEachState = (ForEachState) currentState;
					stateDataFilters = forEachState.getStateDataFilter();
					workflowData.setInput(filterStateDataInput(stateDataFilters, workflowData.getInput()));
					workflowData.setOutput(workflowData.getInput());
					response = transitionForEachState(workflowDefinition, forEachState, instance, workflowData);
					break;
				case callback:
					final CallbackState callbackState = (CallbackState) currentState;
					stateDataFilters = callbackState.getStateDataFilter();
					workflowData.setInput(filterStateDataInput(stateDataFilters, workflowData.getInput()));
					workflowData.setOutput(workflowData.getInput());
					response = transitionCallbackState(workflowDefinition, callbackState, instance, workflowData);
					break;
				default:
					throw new BadRequestException();
			}
			workflowData.setOutput(filterStateDataOutput(stateDataFilters, workflowData.getOutput()));
			putState(state, instance, workflowData);
			return output(response, workflowData);
		}
		else {
			response = Response.noContent();
		}
		return response.build();
	}

	@PUT
	@Path("{workflow}/{state}")
	@LRA(value = Type.MANDATORY, end = false)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response compensate(
			@PathParam("workflow")
			final String workflow, 
			@PathParam("state")
			final String state, 
			@QueryParam("version")
			final String version,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final InputStream body) throws Exception {
		ResponseBuilder response;
		final WorkflowDefinition workflowDefinition = findWorkflowDefinition(workflow, version);
		final State currentState = getState(workflowDefinition, state);
		final Optional<String> compensatedBy = getCompensatedBy(currentState);
		if(compensatedBy.isPresent()) {
			response = transitionLink(workflow, Optional.ofNullable(version), compensatedBy.get(), instance, new Link[0]);
		}
		else {
			response = Response.ok();
		}
		return output(response, body);
	}
	
	@HEAD
	@Path("{workflow}/{state}")
	@LRA(value = Type.MANDATORY, end = false)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response getActions(
			@PathParam("workflow")
			final String workflow, 
			@PathParam("state")
			final String state,
			@QueryParam("version")
			final String version,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance) throws Exception {
		final WorkflowDefinition workflowDefinition = findWorkflowDefinition(workflow, version);
		final State currentState = getState(workflowDefinition, state);
		final Optional<Duration> actionExecTimeout = TimeoutUtil.getActionExecTimeout(workflowDefinition, currentState);
		List<ActionDefinition> actions = new ArrayList<>();
		boolean useResults = false;
		switch(currentState.getType_()) {
			case Switch:
				break;
			case callback:
				break;
			case event:
				break;
			case foreach:
				break;
			case inject:
				break;
			case operation:
				final OperationState operationState = (OperationState) currentState;
				actions = operationState.getActions();
				useResults = true;
				break;
			case parallel:
				break;
			case sleep:
				break;
			default:
				break;
		}
		final Link[] actionLinks = actionLinks(workflowDefinition, currentState.getName(), actions, actionExecTimeout, useResults);
		return actionsLink(instance, actionLinks).build();
	}
	
	@PATCH
	@Path("{workflow}/{state}")
	@LRA(value = Type.MANDATORY, end = false)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RunOnVirtualThread
	public Response actions(
			@PathParam("workflow")
			final String workflow, 
			@PathParam("state")
			final String state,
			@QueryParam("version")
			final String version,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final InputStream body) throws Exception {
		final ResponseBuilder response = Response.ok().header(LRA.LRA_HTTP_CONTEXT_HEADER, instance);
		return output(response, body);
	}
	
	@POST
	@Path("{workflow}/{state}/{action}")
	@LRA(value = Type.MANDATORY, end = false)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Retry(maxRetries = -1, maxDuration = 0, jitter = 0, retryOn = {RetryableException.class}, abortOn = {NonRetryableException.class})
	@Fallback(fallbackMethod = "onErrors", applyOn = {WorkflowException.class})
	@RunOnVirtualThread
	public Response action(
			@PathParam("workflow")
			final String workflow, 
			@PathParam("state")
			final String state,
			@PathParam("action")
			final String action,
			@QueryParam("version")
			final String version,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final InputStream body) throws Exception {
		final WorkflowDefinition workflowDefinition = findWorkflowDefinition(workflow, version);
		final State currentState = getState(workflowDefinition, state);
		final ActionDefinition actionDefinition = getActionDefinition(currentState, action);
		final WorkflowData workflowData = new WorkflowData();
		workflowData.setInput(JsonUtil.readValue(body));
		workflowData.setOutput(workflowData.getInput());
		final WorkflowData actionData = new WorkflowData();
		actionData.setInput(filterActionDataInput(actionDefinition.getActionDataFilter(), workflowData.getInput()));
		actionData.setOutput(actionData.getInput());
		final ResponseBuilder response = action(workflowDefinition, actionDefinition, actionData);
		workflowData.setOutput(filterActionDataOutput(actionDefinition.getActionDataFilter(), workflowData, actionData));
		return output(response, workflowData);
	}

	@LRA(value = Type.MANDATORY, end = false)
	@RunOnVirtualThread
	public Response onErrors(
			final String workflow,
			final String state,
			final String action,
			final String version,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER)
			final URI instance,
			final Boolean useResults,
			final InputStream body) throws Exception {
		return Response.status(Status.RESET_CONTENT).entity(body).build();
	}
}
