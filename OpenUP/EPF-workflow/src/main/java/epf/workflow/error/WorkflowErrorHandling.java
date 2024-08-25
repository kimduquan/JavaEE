package epf.workflow.error;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import epf.workflow.client.util.LinkUtil;
import epf.workflow.schema.WorkflowError;
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
		final WorkflowErrorException workflowError = (WorkflowErrorException) context.getFailure();
		if(workflowError.getActionDefinition().getRetryRef() != null) {
			response = Response.ok().links(LinkUtil.self());
		}
		return response.build();
	}
	
	public void catchException(final WebApplicationException exception) throws WorkflowException {
		final WorkflowError workflowError = new WorkflowError();
		workflowError.setCode(String.valueOf(exception.getResponse().getStatus()));
		workflowError.setDescription(exception.getMessage());
		throw new WorkflowException(workflowError);
	}
}