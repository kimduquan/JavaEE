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
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.validation.Validator;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.lra.annotation.AfterLRA;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.Forget;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA.Type;
import org.eclipse.microprofile.lra.annotation.ws.rs.Leave;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.PathItem;
import org.eclipse.microprofile.openapi.models.parameters.Parameter;
import org.eclipse.microprofile.openapi.models.servers.Server;
import epf.concurrent.client.ext.Concurrent;
import epf.concurrent.client.ext.Synchronized;
import epf.event.schema.Event;
import epf.naming.Naming;
import epf.nosql.schema.BooleanOrObject;
import epf.nosql.schema.Either;
import epf.nosql.schema.StringOrObject;
import epf.util.MapUtil;
import epf.util.json.ext.JsonUtil;
import epf.util.logging.LogManager;
import epf.workflow.client.Internal;
import epf.workflow.client.Workflow;
import epf.workflow.data.EitherUtil;
import epf.workflow.data.StateUtil;
import epf.workflow.data.WorkflowData;
import epf.workflow.error.WorkflowException;
import epf.workflow.expressions.ELUtil;
import epf.workflow.functions.openapi.OpenAPIUtil;
import epf.workflow.instance.WorkflowCache;
import epf.workflow.instance.Instance;
import epf.workflow.retries.NonRetryableException;
import epf.workflow.retries.RetryableException;
import epf.workflow.schema.ContinueAs;
import epf.workflow.schema.CorrelationDefinition;
import epf.workflow.schema.EndDefinition;
import epf.workflow.schema.StartDefinition;
import epf.workflow.schema.SubFlowRefDefinition;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.WorkflowError;
import epf.workflow.schema.WorkflowTimeoutDefinition;
import epf.workflow.schema.action.ActionDataFilters;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.action.Mode;
import epf.workflow.schema.auth.AuthDefinition;
import epf.workflow.schema.auth.BearerPropertiesDefinition;
import epf.workflow.schema.event.EventDataFilters;
import epf.workflow.schema.event.EventDefinition;
import epf.workflow.schema.event.EventRefDefinition;
import epf.workflow.schema.event.Kind;
import epf.workflow.schema.event.OnEventsDefinition;
import epf.workflow.schema.event.ProducedEventDefinition;
import epf.workflow.schema.function.FunctionDefinition;
import epf.workflow.schema.function.FunctionRefDefinition;
import epf.workflow.schema.function.Invoke;
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
import epf.workflow.util.LinkBuilder;
import epf.workflow.util.ResponseBuilder;
import io.smallrye.common.annotation.RunOnVirtualThread;
import epf.workflow.schema.state.SwitchState;
import epf.workflow.schema.state.SwitchStateDataConditions;
import epf.workflow.schema.state.SwitchStateEventConditions;
import epf.workflow.states.WorkflowState;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.WORKFLOW)
public class WorkflowApplication implements Workflow, Internal {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(WorkflowApplication.class.getName());
	
	/**
	 * 
	 */
	private transient final Map<String, OpenAPI> openAPIs = new ConcurrentHashMap<>();
	
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
	transient Concurrent concurrent;
	
	/**
	 * 
	 */
	@Inject
	transient Validator validator;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Concurrent.CONCURRENT_URL)
	transient URI concurrentUrl;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			concurrent.connectToServer(concurrentUrl);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "postConstruct", e);
		}
	}
	
	private ResponseBuilder transitionLink(final ResponseBuilder response, final String workflow, final Optional<String> version, final String nextState, final Boolean compensate) {
		final LinkBuilder builder = new LinkBuilder();
		final Link transitionLink = Internal.transitionLink(workflow, version, nextState, compensate);
		builder.link(transitionLink).at(response.getSize());
		return response.links(builder.build());
	}
	
	private void putState(final String state, final URI instance, final WorkflowData workflowData) {
		final Instance workflowInstance = cache.getInstance(instance);
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
	
	private Map<String, EventDefinition> getEvents(final WorkflowDefinition workflowDefinition){
		final List<EventDefinition> events = EitherUtil.getArray(workflowDefinition.getEvents());
		final Map<String, EventDefinition> map = new HashMap<>();
		return MapUtil.putAll(map, events.iterator(), EventDefinition::getName);
	}
	
	private EventDefinition getEventDefinition(final WorkflowDefinition workflowDefinition, final String event) {
		final List<EventDefinition> events = EitherUtil.getArray(workflowDefinition.getEvents());
		return events.stream().filter(eventDef -> eventDef.getName().equals(event)).findFirst().get();
	}
	
	private Event newEvent(final EventDefinition eventDefinition, final URI instance, final Map<String, Object> ext) {
		final Event event = new Event();
		event.setSource(eventDefinition.getSource());
		event.setType(eventDefinition.getType());
		event.setSubject(instance.toString());
		eventDefinition.getCorrelation().forEach(correlationDefinition -> {
			ext.put(correlationDefinition.getContextAttributeName(), correlationDefinition.getContextAttributeValue());
		});
		return event;
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
	
	private void catchException(final WebApplicationException exception) throws WorkflowException {
		final WorkflowError workflowError = new WorkflowError();
		workflowError.setCode(String.valueOf(exception.getResponse().getStatus()));
		workflowError.setDescription(exception.getMessage());
		throw new WorkflowException(workflowError);
	}
	
	private OpenAPI getOpenAPI(final WorkflowDefinition workflowDefinition, final FunctionDefinition functionDefinition) throws Exception {
		final int index = functionDefinition.getOperation().indexOf("#");
		final String pathToOpenAPIDefinition = functionDefinition.getOperation().substring(0, index);
		if(!openAPIs.containsKey(pathToOpenAPIDefinition)) {
			final URI uri = URI.create(pathToOpenAPIDefinition);
			final OpenAPI openAPI = OpenAPIUtil.read(uri.toURL());
			openAPIs.put(pathToOpenAPIDefinition, openAPI);
		}
		final OpenAPI openAPI = openAPIs.get(pathToOpenAPIDefinition);
		return openAPI;
	}
	
	private String getOperationId(final FunctionDefinition functionDefinition) {
		final int index = functionDefinition.getOperation().indexOf("#");
		final String operationId = functionDefinition.getOperation().substring(index + 1);
		return operationId;
	}
	
	private Response openapi(final WorkflowDefinition workflowDefinition, final FunctionDefinition functionDefinition, final JsonValue data) throws Exception {
		final String operationId = getOperationId(functionDefinition);
		final OpenAPI openAPI = getOpenAPI(workflowDefinition, functionDefinition);
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
	
	private ResponseBuilder retry(final WorkflowDefinition workflowDefinition, final ActionDefinition actionDefinition, final WorkflowException exception) throws RetryableException, NonRetryableException {
		if(Boolean.TRUE.equals(workflowDefinition.getAutoRetries())) {
			for(WorkflowError workflowError : actionDefinition.getNonRetryableErrors()) {
				if(workflowError.getCode().equals(exception.getWorkflowError().getCode())) {
					throw new NonRetryableException(exception.getWorkflowError());
				}
			}
			throw new RetryableException(exception.getWorkflowError());
		}
		else {
			for(WorkflowError workflowError : actionDefinition.getRetryableErrors()) {
				if(workflowError.getCode().equals(exception.getWorkflowError().getCode())) {
					throw new RetryableException(exception.getWorkflowError());
				}
			}
			throw new NonRetryableException(exception.getWorkflowError());
		}
	}
	
	private WorkflowData input(final InputStream body) throws Exception {
		final WorkflowData workflowData = new WorkflowData();
		JsonValue input = JsonUtil.readValue(body);
		workflowData.setInput(input);
		workflowData.setOutput(input);
		return workflowData;
	}
	
	private WorkflowData input(final epf.event.schema.Event event) throws Exception {
		final WorkflowData workflowData = new WorkflowData();
		JsonValue input = JsonUtil.toJsonValue(event.getData());
		workflowData.setInput(input);
		workflowData.setOutput(input);
		return workflowData;
	}
	
	private ResponseBuilder output(final ResponseBuilder response, final WorkflowData workflowData) {
		return response.entity(workflowData.getOutput().toString());
	}
	
	private Response output(final URI instance, final ResponseBuilder response, final WorkflowData workflowData) {
		return response.entity(workflowData.getOutput().toString()).header(LRA.LRA_HTTP_CONTEXT_HEADER, instance).build();
	}
	
	private Response output(final URI instance, final ResponseBuilder response, final InputStream body) {
		return response.entity(body).header(LRA.LRA_HTTP_CONTEXT_HEADER, instance).build();
	}
	
	private Response output(final URI instance, final ResponseBuilder response) {
		return response.header(LRA.LRA_HTTP_CONTEXT_HEADER, instance).build();
	}
	
	private ResponseBuilder partial(final ResponseBuilder response, final List<?> list, final Link[] links) {
		return response.status(Status.PARTIAL_CONTENT).entity(list).links(links);
	}
	
	private ResponseBuilder partial(final ResponseBuilder response, final JsonArray array, final Link[] links) {
		return response.status(Status.PARTIAL_CONTENT).entity(array.toString()).links(links);
	}
	
	private ResponseBuilder function(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final ActionDefinition actionDefinition, final FunctionDefinition functionDefinition, final WorkflowData workflowData) throws Exception {
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
				try {
					final Response res = openapi(workflowDefinition, functionDefinition, workflowData.getInput());
					return ResponseBuilder.fromResponse(response, res);
				}
				catch(WebApplicationException ex) {
					catchException(ex);
				}
			case rest:
				break;
			case rpc:
				break;
			default:
				break;
		}
		return response;
	}
	
	private ResponseBuilder subFlow(final ResponseBuilder response) {
		return response;
	}
	
	private Map<String, Object> eventData(final EventRefDefinition eventRefDefinition, final EventDefinition eventRef, final URI instance, final WorkflowData workflowData) throws Exception {
		final Map<String, Object> ext = new HashMap<>();
		final epf.event.schema.Event event = newEvent(eventRef, instance, ext);
		Object data = null;
		if(eventRefDefinition.getData().isLeft()) {
			final JsonValue value = ELUtil.getValue(eventRefDefinition.getData().getLeft(), workflowData.getInput());
			data = JsonUtil.asValue(value);
		}
		else if(eventRefDefinition.getData().isRight()) {
			data = eventRefDefinition.getData().getRight();
		}
		event.setData(data);
		Map<String, Object> contextAttributes = eventRefDefinition.getContextAttributes();
		if(contextAttributes == null) {
			contextAttributes = new HashMap<>();
		}
		return event.toMap(contextAttributes);
	}
	
	private ResponseBuilder eventRef(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final String state, final ActionDefinition actionDefinition, final EventRefDefinition eventRefDefinition, final URI instance, final WorkflowData workflowData) throws Exception {
		final List<Map<String, Object>> eventDatas = new ArrayList<>();
		final List<Link> eventLinks = new ArrayList<>();
		final Link producesLink = epf.event.client.Event.producesLink();
		final Link consumeLink = Internal.observesLink(workflowDefinition.getId(), state, Optional.ofNullable(workflowDefinition.getVersion()));
		final LinkBuilder builder = new LinkBuilder();
		
		final EventDefinition produceEventDefinition = getEventDefinition(workflowDefinition, actionDefinition.getEventRef().getProduceEventRef());
		final Map<String, Object> produceEventData = eventData(eventRefDefinition, produceEventDefinition, instance, workflowData);
		final Link produceEventLink = builder.link(producesLink).at(response.getSize()).build();
		eventDatas.add(produceEventData);
		eventLinks.add(produceEventLink);
		
		if(actionDefinition.getEventRef().getConsumeEventRef() != null) {
			final EventDefinition consumeEventDefinition = getEventDefinition(workflowDefinition, actionDefinition.getEventRef().getConsumeEventRef());
			final Map<String, Object> consumeEventData = eventData(eventRefDefinition, consumeEventDefinition, instance, workflowData);
			builder.link(consumeLink);
			if(Invoke.sync == eventRefDefinition.getInvoke()) {
				final Synchronized synchronized_ = concurrent.synchronized_();
				String timeout = eventRefDefinition.getConsumeEventTimeout();
				if(timeout == null) {
					final WorkflowTimeoutDefinition timeouts = EitherUtil.getObject(workflowDefinition.getTimeouts());
					timeout = timeouts.getActionExecTimeout();
				}
				if(timeout != null) {
					builder.synchronized_(synchronized_.getId(), Duration.parse(timeout));
				}
				else {
					builder.synchronized_(synchronized_.getId());
				}
			}
			final Link consumeEventLink = builder.at(response.getSize()).build();
			
			eventDatas.add(consumeEventData);
			eventLinks.add(consumeEventLink);
		}
		return partial(response, eventDatas, eventLinks.toArray(new Link[0]));
	}
	
	private ResponseBuilder action(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final String state, final ActionDefinition actionDefinition, final URI instance, final WorkflowData workflowData) throws Exception {
		if(!actionDefinition.getFunctionRef().isNull()) {
			final FunctionDefinition functionDefinition = getFunctionDefinition(workflowDefinition, actionDefinition.getFunctionRef());
			try {
				function(response, workflowDefinition, actionDefinition, functionDefinition, workflowData);
			}
			catch(WorkflowException ex) {
				retry(workflowDefinition, actionDefinition, ex);
			}
		}
		else if(actionDefinition.getEventRef() != null) {
			eventRef(response, workflowDefinition, state, actionDefinition, actionDefinition.getEventRef(), instance, workflowData);
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
	
	private JsonValue filterStateDataOutput(final StateDataFilters stateDataFilters, final JsonValue output) throws Exception {
		if(stateDataFilters != null && stateDataFilters.getOutput() != null) {
			return ELUtil.getValue(stateDataFilters.getOutput(), output);
		}
		return output;
	}
	
	private JsonValue filterEventDataOutput(final EventDataFilters eventDataFilters, final WorkflowData workflowData, final JsonValue eventData) throws Exception {
		JsonValue output = workflowData.getOutput();
		if(eventDataFilters != null && !Boolean.FALSE.equals(eventDataFilters.isUseData())) {
			if(eventDataFilters.getToStateData() != null) {
				output =  StateUtil.mergeStateDataOutput(eventDataFilters.getToStateData(), workflowData.getOutput(), eventData);
			}
			else {
				output = StateUtil.mergeStateDataOutput(workflowData.getOutput(), eventData);
			}
		}
		return output;
	}
	
	private Map<String, Object> produceEventData(final ProducedEventDefinition producedEventDefinition, final EventDefinition eventDefinition, final URI instance, final WorkflowData workflowData) throws Exception {
		final Map<String, Object> ext = new HashMap<>();
		final epf.event.schema.Event event = newEvent(eventDefinition, instance, ext);
		Object data = null;
		if(producedEventDefinition.getData().isLeft()) {
			final JsonValue value = ELUtil.getValue(producedEventDefinition.getData().getLeft(), workflowData.getInput());
			data = JsonUtil.asValue(value);
		}
		else if(producedEventDefinition.getData().isRight()) {
			data = producedEventDefinition.getData().getRight();
		}
		event.setData(data);
		Map<String, Object> contextAttributes = producedEventDefinition.getContextAttributes();
		if(contextAttributes == null) {
			contextAttributes = new HashMap<>();
		}
		return event.toMap(contextAttributes);
	}
	
	private ResponseBuilder produceEvents(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final List<ProducedEventDefinition> produceEvents, final URI instance, final WorkflowData workflowData) throws Exception {
		final Map<String, EventDefinition> eventDefinitions = new HashMap<>();
		final List<EventDefinition> events = EitherUtil.getArray(workflowDefinition.getEvents());
		MapUtil.putAll(eventDefinitions, events.iterator(), EventDefinition::getName);
		final List<Map<String, Object>> eventDatas = new ArrayList<>();
		final List<Link> producesLinks = new ArrayList<>();
		final Link producesLink = epf.event.client.Event.producesLink();
		final LinkBuilder builder = new LinkBuilder();
		for(ProducedEventDefinition producedEventDefinition : produceEvents) {
			final EventDefinition eventDefinition = eventDefinitions.get(producedEventDefinition.getEventRef());
			final Map<String, Object> eventData = produceEventData(producedEventDefinition, eventDefinition, instance, workflowData);
			eventDatas.add(eventData);
			builder.link(producesLink).at(response.getSize());
			producesLinks.add(builder.build());
		}
		return partial(response, eventDatas, producesLinks.toArray(new Link[0]));
	}
	
	private ResponseBuilder compensates(final ResponseBuilder response, final String workflow, final Optional<String> version, final Instance workflowInstance) {
		final LinkBuilder builder = new LinkBuilder();
		final List<Link> compensateLinks = new ArrayList<>();
		WorkflowState workflowState = workflowInstance.getState();
		while(workflowState != null) {
			final Link compensateLink = Internal.compensateLink(workflow, version, workflowState.getName());
			final Link link = builder.link(compensateLink).at(response.getSize()).build();
			compensateLinks.add(link);
			workflowState = workflowState.getPreviousState();
		}
		return response.links(compensateLinks.toArray(new Link[0]));
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
		return partial(response, batchesArray, batchLinks.toArray(new Link[0]));
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
		return partial(response, branchesArray.build(), branchLinks.toArray(new Link[0]));
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
				produceEvents(response, workflowDefinition, endDefinition.getProduceEvents(), instance, workflowData);
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
			final WorkflowDefinition workflowDefinition = cache.get(continueAs.getLeft());
			return startLink(response, workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), instance);
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
			return startLink(response, workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), instance);
		}
		throw new BadRequestException();
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
	
	private ResponseBuilder transition(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final Either<String, TransitionDefinition> transition, final URI instance, final WorkflowData workflowData) throws Exception {
		if(transition.isLeft()) {
			final String nextState = transition.getLeft();
			return transitionLink(response, workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), nextState, false);
		}
		else if(transition.isRight()) {
			final TransitionDefinition transitionDef = transition.getRight();
			if(transitionDef.getProduceEvents() != null) {
				produceEvents(response, workflowDefinition, transitionDef.getProduceEvents(), instance, workflowData);
			}
			return transitionLink(response, workflowDefinition.getId(), Optional.ofNullable(workflowDefinition.getVersion()), transitionDef.getNextState(), transitionDef.isCompensate());
		}
		throw new BadRequestException();
	}
	
	private ResponseBuilder transitionCallbackState(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final CallbackState callbackState, final URI instance, final WorkflowData workflowData) throws Exception {
		final EventDefinition eventDefinition = getEventDefinition(workflowDefinition, callbackState.getEventRef());
		final Map<String, Object> ext = new HashMap<>();
		final Event event = newEvent(eventDefinition, instance, ext);
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
				final Event event = newEvent(eventDef, instance, ext);
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
		return partial(response, events, eventLinks.toArray(new Link[0]));
	}
	
	private ResponseBuilder transitionOperationState(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final OperationState operationState, final URI instance, final WorkflowData workflowData) throws Exception {
		actions(response, workflowDefinition.getId(), workflowDefinition.getVersion(), operationState.getName(), operationState.getActions(), workflowData);
		return transitionOrEnd(response, workflowDefinition, operationState.getName(), operationState.getTransition(), operationState.getEnd(), instance, workflowData);
	}
	
	private ResponseBuilder transitionSwitchState(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final SwitchState switchState, final URI instance, final WorkflowData workflowData) throws Exception {
		if(switchState.getDataConditions() != null) {
			for(SwitchStateDataConditions condition : switchState.getDataConditions()) {
				if(ELUtil.evaluateCondition(workflowData.getOutput(), condition.getCondition())) {
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
		final JsonValue output = StateUtil.mergeStateDataOutput(workflowData.getOutput(), JsonUtil.toJsonValue(injectState.getData()));
		workflowData.setOutput(output);
		return transitionOrEnd(response, workflowDefinition, injectState.getName(), injectState.getTransition(), injectState.getEnd(), instance, workflowData);
	}
	
	private ResponseBuilder transitionForEachState(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final ForEachState forEachState, final URI instance, final WorkflowData workflowData) throws Exception {
		final JsonArray inputCollection = ELUtil.getValue(forEachState.getInputCollection(), workflowData.getInput()).asJsonArray();
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
			workflowDefinition = Optional.ofNullable(cache.get(workflow, version));
		}
		else {
			workflowDefinition = Optional.ofNullable(cache.get(workflow));
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
	
	private boolean onEvent(final EventDefinition eventDefinition, final List<Map<String, Object>> eventDatas, final List<epf.event.schema.Event> events) throws Exception {
		boolean equals = true;
		final Iterator<Map<String, Object>> eventData = eventDatas.iterator();
		while(eventData.hasNext()) {
			equals = true;
			final Map<String, Object> ext = new HashMap<>();
			final Event event = Event.event(eventData.next(), ext);
			if(equals && eventDefinition.getSource() != null) {
				equals = eventDefinition.getSource().equals(event.getSource());
			}
			if(equals && eventDefinition.getType() != null) {
				equals = eventDefinition.getType().equals(event.getType());
			}
			if(equals && eventDefinition.getCorrelation() != null) {
				for(CorrelationDefinition correlationDefinition : eventDefinition.getCorrelation()) {
					final Object contextAttributeValue = ext.get(correlationDefinition.getContextAttributeName());
					if(correlationDefinition.getContextAttributeValue() != null) {
						equals = correlationDefinition.getContextAttributeValue().equals(contextAttributeValue);
					}
					if(!equals) {
						break;
					}
				}
			}
			if(equals) {
				events.add(event);
				break;
			}
		}
		return equals;
	}
	
	private boolean onEvents(final WorkflowDefinition workflowDefinition, final EventState eventState, final OnEventsDefinition onEventsDefinition, final Map<String, EventDefinition> eventDefinitions, final List<Map<String, Object>> eventDatas, final List<epf.event.schema.Event> events) throws Exception {
		final List<EventDefinition> onEvents = MapUtil.getAll(eventDefinitions, onEventsDefinition.getEventRefs().iterator());
		boolean all = true;
		boolean any = false;
		for(EventDefinition eventDefinition : onEvents) {
			if(onEvent(eventDefinition, eventDatas, events)) {
				any = true;
			}
			else {
				all = false;
			}
		}
		final boolean exclusive = !Boolean.FALSE.equals(eventState.isExclusive());
		return exclusive ? any : all;
	}
	
	private ResponseBuilder onEvents(final ResponseBuilder response, final Map<String, EventDefinition> eventDefinitions, final WorkflowDefinition workflowDefinition, final EventState eventState, URI instance, WorkflowData workflowData, final List<Map<String, Object>> eventDatas) throws Exception {
		final List<ActionDefinition> actions = new ArrayList<>();
		final JsonArrayBuilder actionDatas = Json.createArrayBuilder();
		boolean all = true;
		boolean any = false;
		Instance workflowInstance = null;
		for(OnEventsDefinition onEventsDefinition : eventState.getOnEvents()) {
			final List<epf.event.schema.Event> events = new ArrayList<>();
			if(onEvents(workflowDefinition, eventState, onEventsDefinition, eventDefinitions, eventDatas, events)) {
				any = true;
				actions.addAll(onEventsDefinition.getActions());
				if(instance == null) {
					instance = new URI(events.get(0).getSource());
				}
				if(workflowData == null) {
					workflowInstance = cache.getInstance(instance);
					workflowData = workflowInstance.getState().getWorkflowData();
				}
				final JsonValue eventData = JsonUtil.toJsonValue(events.get(0).getData());
				filterEventDataOutput(onEventsDefinition.getEventDataFilter(), workflowData, eventData);
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
			return transitionOrEnd(response, workflowDefinition, eventState.getName(), eventState.getTransition(), eventState.getEnd(), instance, workflowData);
		}
		return response;
	}
	
	private List<Map<String, Object>> events(final EventState eventState, final Map<String, EventDefinition> eventDefinitions, final URI instance) {
		final List<Map<String, Object>> events = new ArrayList<>();
		for(OnEventsDefinition onEventsDefinition : eventState.getOnEvents()) {
			for(String eventRef : onEventsDefinition.getEventRefs()) {
				final EventDefinition eventDefinition = eventDefinitions.get(eventRef);
				final Map<String, Object> map = new HashMap<>();
				final epf.event.schema.Event event = newEvent(eventDefinition, instance, map);
				map.put(epf.naming.Naming.Event.Schema.SOURCE, event.getSource());
				map.put(epf.naming.Naming.Event.Schema.SUBJECT, event.getSubject());
				map.put(epf.naming.Naming.Event.Schema.TYPE, event.getType());
				events.add(map);
			}
		}
		return events;
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
				workflowData.setInput(filterStateDataInput(eventState.getStateDataFilter(), workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
				transitionEventState(response, workflowDefinition, eventState, instance);
				break;
			case operation:
				final OperationState operationState = (OperationState) state;
				workflowData.setInput(filterStateDataInput(operationState.getStateDataFilter(), workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
				transitionOperationState(response, workflowDefinition, operationState, instance, workflowData);
				break;
			case Switch:
				final SwitchState switchState = (SwitchState) state;
				workflowData.setInput(filterStateDataInput(switchState.getStateDataFilter(), workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
				transitionSwitchState(response, workflowDefinition, switchState, instance, workflowData);
				workflowData.setOutput(filterStateDataOutput(switchState.getStateDataFilter(), workflowData.getOutput()));
				output(response, workflowData);
				break;
			case sleep:
				final SleepState sleepState = (SleepState) state;
				workflowData.setOutput(workflowData.getInput());
				transitionSleepState(response, workflowDefinition, sleepState, instance, workflowData);
				output(response, workflowData);
				break;
			case parallel:
				final ParallelState parallelState = (ParallelState) state;
				workflowData.setInput(filterStateDataInput(parallelState.getStateDataFilter(), workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
				transitionParallelState(response, workflowDefinition, parallelState, instance, workflowData);
				break;
			case inject:
				final InjectState injectState = (InjectState) state;
				workflowData.setInput(filterStateDataInput(injectState.getStateDataFilter(), workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
				transitionInjectState(response, workflowDefinition, injectState, instance, workflowData);
				workflowData.setOutput(filterStateDataOutput(injectState.getStateDataFilter(), workflowData.getOutput()));
				output(response, workflowData);
				break;
			case foreach:
				final ForEachState forEachState = (ForEachState) state;
				workflowData.setInput(filterStateDataInput(forEachState.getStateDataFilter(), workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
				transitionForEachState(response, workflowDefinition, forEachState, instance, workflowData);
				break;
			case callback:
				final CallbackState callbackState = (CallbackState) state;
				workflowData.setInput(filterStateDataInput(callbackState.getStateDataFilter(), workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
				transitionCallbackState(response, workflowDefinition, callbackState, instance, workflowData);
				break;
			default:
				throw new BadRequestException();
		}
		return response;
	}
	
	private StateDataFilters getStateDataFilter(final State state) {
		StateDataFilters stateDataFilter = null;
		switch(state.getType_()) {
			case Switch:
				final SwitchState switchState = (SwitchState) state;
				stateDataFilter = switchState.getStateDataFilter();
				break;
			case callback:
				final CallbackState callbackState = (CallbackState) state;
				stateDataFilter = callbackState.getStateDataFilter();
				break;
			case event:
				final EventState eventState = (EventState) state;
				stateDataFilter = eventState.getStateDataFilter();
				break;
			case foreach:
				final ForEachState forEachState = (ForEachState) state;
				stateDataFilter = forEachState.getStateDataFilter();
				break;
			case inject:
				final InjectState injectState = (InjectState) state;
				stateDataFilter = injectState.getStateDataFilter();
				break;
			case operation:
				final OperationState operationState = (OperationState) state;
				stateDataFilter = operationState.getStateDataFilter();
				break;
			case parallel:
				final ParallelState parallelState = (ParallelState) state;
				stateDataFilter = parallelState.getStateDataFilter();
				break;
			case sleep:
				break;
			default:
				break;
		}
		return stateDataFilter;
	}

	@Complete
	@Compensate
	@AfterLRA
	@RunOnVirtualThread
	@Override
	public Response end(final URI instance) throws Exception {
		cache.removeInstance(instance);
		return Response.ok().build();
	}

	@Forget
	@Leave
	@RunOnVirtualThread
	@Override
	public Response terminate(final URI instance) throws Exception {
		final Instance workflowInstance = cache.removeInstance(instance);
		final ResponseBuilder response = new ResponseBuilder();
		if(workflowInstance != null) {
			output(instance, response, workflowInstance.getState().getWorkflowData());
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
		final WorkflowData workflowData = input(body);
		cache.putInstance(instance, new Instance());
		final ResponseBuilder response = new ResponseBuilder();
		transitionLink(response, workflow, Optional.ofNullable(version), startState, false);
		return output(instance, response, workflowData);
	}
	
	@LRA(value = Type.MANDATORY, end = true)
	@RunOnVirtualThread
	@Override
	public Response end(final String workflow, final String version, final Boolean terminate, final Boolean compensate, final String continueAs, final URI instance, final InputStream body) throws Exception {
		final ResponseBuilder response = new ResponseBuilder();
		final Instance workflowInstance = cache.getInstance(instance);
		if(workflowInstance != null) {
			if(Boolean.TRUE.equals(compensate)) {
				compensates(response, workflow, Optional.ofNullable(version), workflowInstance);
			}
			return output(instance, response, body);
		}
		else {
			response.status(Status.NO_CONTENT);
		}
		return response.build();
	}

	@LRA(value = Type.MANDATORY, end = false, cancelOn = {Response.Status.RESET_CONTENT})
	@RunOnVirtualThread
	@Override
	public Response transition(final String workflow, final String version, final String nextState, final Boolean compensate, final URI instance, final InputStream body) throws Exception {
		final Instance workflowInstance = cache.getInstance(instance);
		final ResponseBuilder response = new ResponseBuilder();
		if(workflowInstance != null) {
			if(Boolean.TRUE.equals(compensate)) {
				compensates(response, workflow, Optional.ofNullable(version), workflowInstance);
			}
			final WorkflowDefinition workflowDefinition = getWorkflowDefinition(workflow, version);
			final State state = getState(workflowDefinition, nextState);
			final WorkflowData workflowData = input(body);
			if(workflowInstance.getState() != null) {
				final State currentState = getState(workflowDefinition, workflowInstance.getState().getName());
				final StateDataFilters stateDataFilters = getStateDataFilter(currentState);
				workflowData.setInput(filterStateDataInput(stateDataFilters, workflowData.getInput()));
				workflowData.setOutput(workflowData.getInput());
			}
			transitionState(response, workflowDefinition, state, instance, workflowData);
			putState(nextState, instance, workflowData);
			return output(instance, response);
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
		final State currentState = getState(workflowDefinition, state);
		final Optional<String> compensatedBy = getCompensatedBy(currentState);
		final ResponseBuilder response = new ResponseBuilder();
		if(compensatedBy.isPresent()) {
			transitionLink(response, workflow, Optional.ofNullable(version), compensatedBy.get(), false);
		}
		return output(instance, response, body);
	}
	
	@RunOnVirtualThread
	@Override
	public Response observes(final String workflow, final String state, final String version, final Map<String, Object> map) throws Exception {
		final Map<String, Object> ext = new HashMap<>();
		final Event event = Event.event(map, ext); 
		final URI instance = new URI(event.getSubject());
		final Instance workflowInstance = cache.getInstance(instance);
		final ResponseBuilder response = new ResponseBuilder();
		if(workflowInstance != null) {
			final WorkflowDefinition workflowDefinition = getWorkflowDefinition(workflow, version);
			final State currentState = getState(workflowDefinition, state);
			final EventState eventState = (EventState)currentState;
			final Map<String, EventDefinition> eventDefinitions = getEvents(workflowDefinition);
			if(Boolean.TRUE.equals(eventState.isExclusive())) {
				final List<Map<String, Object>> events = new ArrayList<>();
				events.add(map);
				onEvents(response, eventDefinitions, workflowDefinition, eventState, instance, workflowInstance.getState().getWorkflowData(), events);
			}
			else {
				final List<Map<String, Object>> events = events(eventState, eventDefinitions, instance);
				onEventsLink(response, instance, events);
			}
			return output(instance, response);
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
			final JsonValue actionData = filterActionDataInput(actionDefinition.getActionDataFilter(), workflowData.getInput());
			final Link actionLink = Internal.actionLink(workflow, Optional.ofNullable(version), state, actionDefinition.getName());
			final Link link = builder.link(actionLink).at(response.getSize()).build();
			actionLinks.add(link);
			array.add(actionData);
		}
		return partial(response, array.build(), actionLinks.toArray(new Link[0]));
	}
	
	@LRA(value = Type.MANDATORY, end = false)
	@RunOnVirtualThread
	@Override
	public Response iteration(final String workflow, final String state, final String version, final URI instance, final InputStream body) throws Exception {
		final WorkflowData workflowData = input(body);
		final WorkflowDefinition workflowDefinition = getWorkflowDefinition(workflow, version);
		final ForEachState forEachState = (ForEachState) getState(workflowDefinition, state);
		final List<ActionDefinition> actionDefinitions = forEachState.getActions();
		final ResponseBuilder response = new ResponseBuilder();;
		actions(response, workflow, version, state, actionDefinitions, workflowData);
		return output(instance, response);
	}
	
	@LRA(value = Type.MANDATORY, end = false)
	@RunOnVirtualThread
	@Override
	public Response branch(final String workflow, final String state, final String version, final int at, final URI instance, final InputStream body) throws Exception {
		final WorkflowDefinition workflowDefinition = getWorkflowDefinition(workflow, version);
		final ParallelState parallelState = (ParallelState) getState(workflowDefinition, state);
		final ParallelStateBranch branch = parallelState.getBranches().get(at);
		final ResponseBuilder response = new ResponseBuilder();
		final WorkflowData workflowData = input(body);
		actions(response, workflow, version, state, branch.getActions(), workflowData);
		return output(instance, response, workflowData);
	}

	@RunOnVirtualThread
	@Override
	public Response callback(final String workflow, final String state, final String version, final Map<String, Object> map) throws Exception {
		final Map<String, Object> ext = new HashMap<>();
		final epf.event.schema.Event event = epf.event.schema.Event.event(map, ext);
		final URI instance = new URI(event.getSubject());
		final Instance workflowInstance = cache.getInstance(instance);
		final ResponseBuilder response = new ResponseBuilder();
		if(workflowInstance != null) {
			final WorkflowDefinition workflowDefinition = getWorkflowDefinition(workflow, version);
			final CallbackState callbackState = (CallbackState) getState(workflowDefinition, state);
			final ActionDefinition actionDefinition = callbackState.getAction();
			final WorkflowData workflowData = input(event);
			action(response, workflowDefinition, state, actionDefinition, instance, workflowData);
			return output(instance, response);
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
		return partial(response, inputCollection, iterationLinks.toArray(new Link[0]));
	}

	@LRA(value = Type.MANDATORY, end = false)
	@RunOnVirtualThread
	@Override
	public Response batch(final String workflow, final String state, final String version, final URI instance, final InputStream body) throws Exception {
		final ResponseBuilder response = new ResponseBuilder();
		final JsonArray inputCollection = JsonUtil.readArray(body);
		iterations(response, workflow, version, state, false, inputCollection);
		return output(instance, response);
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
		final State currentState = getState(workflowDefinition, state);
		final EventState eventState = (EventState)currentState;
		final Map<String, EventDefinition> eventDefinitions = getEvents(workflowDefinition);
		onEvents(response, eventDefinitions, workflowDefinition, eventState, null, null, events);
		return response.build();
	}
}
