package epf.workflow.error;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import epf.workflow.client.util.LinkUtil;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

/**
 * 
 */
public class WorkflowErrorHandler implements FallbackHandler<Response> {

	@Override
	public Response handle(final ExecutionContext context) {
		ResponseBuilder response = Response.serverError();
		final WorkflowErrorException workflowError = (WorkflowErrorException) context.getFailure();
		if(workflowError.getActionDefinition().getRetryRef() != null) {
			response = Response.ok().links(LinkUtil.self());
		}
		return response.build();
	}
	
}
