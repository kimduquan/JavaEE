package epf.workflow;

import java.util.concurrent.CompletionStage;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.lra.annotation.Compensate;
import org.eclipse.microprofile.lra.annotation.Complete;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA;
import org.eclipse.microprofile.lra.annotation.ws.rs.LRA.Type;
import org.eclipse.microprofile.reactive.messaging.Incoming;

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
	
	@PUT
	@Path("state")
	@LRA(value = Type.NESTED, end = false)
	@Asynchronous
	public CompletionStage<Response> start(
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) 
			final String instanceId) {
		return executor.completedStage(Response.ok().build());
	}
	
	@PUT
	@Path("state/transition")
	@LRA(value = Type.MANDATORY, end = false)
	@Asynchronous
	@Retry(maxRetries = -1, maxDuration = 0, jitter = 0, retryOn = RetryException.class)
	public CompletionStage<Response> transition(
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) 
			final String instanceId) {
		return executor.completedStage(Response.ok().build());
	}
	
	@PUT
	@Path("state/end")
	@Complete
	@Asynchronous
	public CompletionStage<Response> end(
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) 
			final String instanceId) {
		return executor.completedStage(Response.ok().build());
	}
	
	@PUT
	@Path("state/compensate")
	@Compensate
	@Asynchronous
	public CompletionStage<Response> compensate(
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) 
			final String instanceId) {
		return executor.completedStage(Response.ok().build());
	}
	
	@Fallback(fallbackMethod= "onError", applyOn=WorkflowException.class)
	@Asynchronous
	public CompletionStage<Response> onError(
			@HeaderParam(LRA.LRA_HTTP_CONTEXT_HEADER) 
			final String instanceId) {
		return executor.completedStage(Response.ok().build());
	}
	
	@Incoming("workflow/event")
	public void onEvent() {
		
	}
}
