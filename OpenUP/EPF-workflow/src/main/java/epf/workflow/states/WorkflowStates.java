package epf.workflow.states;

import java.io.InputStream;
import java.net.URI;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import epf.util.json.ext.JsonUtil;
import epf.workflow.data.WorkflowData;
import epf.workflow.util.ResponseBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonValue;
import jakarta.ws.rs.core.Response;

/**
 * 
 */
@ApplicationScoped
public class WorkflowStates {
	
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
}
