package epf.workflow;

import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.CompletionStage;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonValue;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA.Type;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.util.json.JsonUtil;

/**
 * 
 */
@ApplicationScoped
@Path("workflow")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class WorkflowRuntime {
	
	private transient ManagedExecutor executor;
	
	@PostConstruct
	protected void postConstruct() {
		executor = ManagedExecutor.builder().build();
	}
	
	private Link getTransitionLink(final String workflow, final String state) {
		return Link.fromUri(String.format("/%s/state/transition?state=%s", workflow, state)).rel("workflow").type(HttpMethod.PUT).build();
	}
	
	@PUT
	@Path("{workflow}/state")
	@LRA(value = Type.NESTED, end = false)
	@Asynchronous
	public CompletionStage<Response> start(
			@PathParam("workflow")
			final String workflow,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) 
			final URI instance,
			final InputStream input) {
		final JsonValue json = JsonUtil.readValue(input);
		final WorkflowData workflowData = new WorkflowData();
		workflowData.setInput(JsonUtil.asValue(json));
		final Link transitionLink = getTransitionLink(workflow, "");
		return executor.completedStage(
				Response.ok(workflowData)
				.header(LRA.LRA_HTTP_CONTEXT_HEADER, instance)
				.links(transitionLink).build());
	}
	
	@PUT
	@Path("{workflow}/state/transition")
	@LRA(value = Type.MANDATORY, end = false)
	@Asynchronous
	@Retry(maxRetries = -1, maxDuration = 0, jitter = 0, retryOn = WorkflowRetry.class)
	@Fallback(fallbackMethod = "onError", applyOn = WorkflowException.class)
	public CompletionStage<Response> transition(
			@PathParam("workflow")
			final String workflow,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) 
			final URI instance,
			@QueryParam("state")
			final String state,
			final WorkflowData data) {
		final Link transitionLink = getTransitionLink(workflow, state);
		return executor.completedStage(Response.ok(data).header(LRA.LRA_HTTP_CONTEXT_HEADER, instance).links(transitionLink).build());
	}
	
	@PUT
	@Path("{workflow}/state/end")
	@LRA(value = Type.MANDATORY, end = true)
	@Asynchronous
	public CompletionStage<Response> end(
			@PathParam("workflow")
			final String workflow,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) 
			final URI instance,
			final WorkflowData data) {
		return executor.completedStage(Response.ok(data.getOutput()).build());
	}
	
	@PUT
	@Path("{workflow}/state/compensate")
	@Compensate
	@Asynchronous
	public CompletionStage<Response> compensate(
			@PathParam("workflow")
			final String workflow,
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) 
			final URI instance,
			@QueryParam("state")
			final String state,
			final WorkflowData data) {
		return executor.completedStage(Response.ok(data.getOutput()).build());
	}
	
	@Asynchronous
	public CompletionStage<Response> onError(
			final String workflow,
			final URI instance,
			final String state,
			final WorkflowData data) {
		final Link transitionLink = getTransitionLink(workflow, state);
		return executor.completedStage(Response.ok(data).header(LRA.LRA_HTTP_CONTEXT_HEADER, instance).links(transitionLink).build());
	}
	
	@Incoming("epf-workflow-event")
	public void onEvent(final WorkflowEvent event) {
		
	}
}
