package epf.workflow.states;

import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import epf.nosql.schema.Either;
import epf.util.json.ext.JsonUtil;
import epf.workflow.WorkflowEvents;
import epf.workflow.client.Internal;
import epf.workflow.data.ActionDataFilters;
import epf.workflow.instance.WorkflowInstance;
import epf.workflow.model.Instance;
import epf.workflow.model.WorkflowData;
import epf.workflow.model.WorkflowState;
import epf.workflow.schema.TransitionDefinition;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.action.Mode;
import epf.workflow.schema.state.State;
import epf.workflow.util.LinkBuilder;
import epf.workflow.util.ResponseBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonValue;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

/**
 * 
 */
@ApplicationScoped
public class WorkflowStates {
	
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
	transient WorkflowEvents workflowEvents;
	
	/**
	 * 
	 */
	@Inject
	transient ActionDataFilters actionDataFilters;
	
	public WorkflowData input(final InputStream body) throws Exception {
		final WorkflowData workflowData = new WorkflowData();
		JsonValue input = JsonUtil.readValue(body);
		workflowData.setInput(input);
		workflowData.setOutput(input);
		return workflowData;
	}
	
	public WorkflowData input(final epf.event.schema.Event event) throws Exception {
		final WorkflowData workflowData = new WorkflowData();
		JsonValue input = JsonUtil.toJsonValue(event.getData());
		workflowData.setInput(input);
		workflowData.setOutput(input);
		return workflowData;
	}
	
	public ResponseBuilder output(final ResponseBuilder response, final WorkflowData workflowData) {
		return response.entity(workflowData.getOutput().toString());
	}
	
	public Response output(final URI instance, final ResponseBuilder response, final WorkflowData workflowData) {
		return response.entity(workflowData.getOutput().toString()).header(LRA.LRA_HTTP_CONTEXT_HEADER, instance).build();
	}
	
	public Response output(final URI instance, final ResponseBuilder response, final InputStream body) {
		return response.entity(body).header(LRA.LRA_HTTP_CONTEXT_HEADER, instance).build();
	}
	
	public Response output(final URI instance, final ResponseBuilder response) {
		return response.header(LRA.LRA_HTTP_CONTEXT_HEADER, instance).build();
	}
	
	public ResponseBuilder partial(final ResponseBuilder response, final List<?> list, final Link[] links) {
		return response.status(Status.PARTIAL_CONTENT).entity(list).links(links);
	}
	
	public ResponseBuilder partial(final ResponseBuilder response, final JsonArray array, final Link[] links) {
		return response.status(Status.PARTIAL_CONTENT).entity(array.toString()).links(links);
	}
	
	public State getState(final WorkflowDefinition workflowDefinition, final String name) {
		return workflowDefinition.getStates().stream().filter(state -> state.getName().equals(name)).findFirst().orElseThrow(NotFoundException::new);
	}
	
	public ResponseBuilder transitionLink(final ResponseBuilder response, final String workflow, final Optional<String> version, final String nextState, final Boolean compensate) {
		final LinkBuilder builder = new LinkBuilder();
		final Link transitionLink = Internal.transitionLink(workflow, version, nextState, compensate);
		builder.link(transitionLink).at(response.getSize());
		return response.links(builder.build());
	}
	
	public ResponseBuilder actions(final ResponseBuilder response, final String workflow, final String state, final String version, final List<ActionDefinition> actionDefinitions, final WorkflowData workflowData) throws Exception {
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
		return partial(response, array.build(), actionLinks.toArray(new Link[0]));
	}
	
	public ResponseBuilder iterations(final ResponseBuilder response, final String workflow, final String version, final String state, final boolean synchronized_, final JsonArray inputCollection) {
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
	
	public ResponseBuilder batches(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final String state, final Mode mode, final int batchSize, final JsonArray inputCollection) {
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
	
	public ResponseBuilder transition(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final Either<String, TransitionDefinition> transition, final URI instance, final WorkflowData workflowData) throws Exception {
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
}
