package epf.workflow.retries;

import epf.workflow.error.WorkflowException;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.WorkflowError;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.util.ResponseBuilder;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * 
 */
@ApplicationScoped
public class ActionRetries {
	
	public ResponseBuilder retry(final WorkflowDefinition workflowDefinition, final ActionDefinition actionDefinition, final WorkflowException exception) throws RetryableException, NonRetryableException {
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
}
