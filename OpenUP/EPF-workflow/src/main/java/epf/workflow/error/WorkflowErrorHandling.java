package epf.workflow.error;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import epf.workflow.client.util.LinkUtil;
import epf.workflow.schema.error.ErrorReference;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

/**
 * 
 */
@ApplicationScoped
public class WorkflowErrorHandling implements FallbackHandler<Response> {

	@Override
	public Response handle(final ExecutionContext context) {
		ResponseBuilder response = Response.serverError();
		response = Response.ok().links(LinkUtil.self());
		return response.build();
	}
	
	public void catchException(final WebApplicationException exception) throws WorkflowException {
		final ErrorReference workflowError = new ErrorReference();
		workflowError.setStatus(String.valueOf(exception.getResponse().getStatus()));
		workflowError.setRefName(exception.getMessage());
		throw new WorkflowException(workflowError);
	}
}