package epf.workflow.states;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import epf.util.json.ext.JsonUtil;
import epf.workflow.instance.WorkflowInstance;
import epf.workflow.model.Instance;
import epf.workflow.model.WorkflowData;
import epf.workflow.model.WorkflowState;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.state.State;
import epf.workflow.util.ResponseBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonArray;
import jakarta.json.JsonValue;
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
	
	public void putState(final String state, final URI uri, final WorkflowData workflowData) {
		final Instance instance = workflowInstance.getInstance(uri);
		final WorkflowState workflowState = instance.getState();
		final WorkflowState newWorkflowState = new WorkflowState();
		newWorkflowState.setPreviousState(workflowState);
		newWorkflowState.setName(state);
		newWorkflowState.setWorkflowData(workflowData);
		instance.setState(newWorkflowState);
		workflowInstance.replaceInstance(uri, instance);
	}
}
