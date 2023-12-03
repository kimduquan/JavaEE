package epf.workflow;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import jakarta.ws.rs.core.Response;

/**
 * 
 */
public class WorkflowFallbackHandler implements FallbackHandler<Response> {

	@Override
	public Response handle(final ExecutionContext context) {
		return null;
	}
}
