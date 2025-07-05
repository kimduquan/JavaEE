package epf.workflow.retries;

import epf.workflow.error.WorkflowException;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.util.ResponseBuilder;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ActionRetries {
	
	public void retry(final WorkflowDefinition workflowDefinition, final ActionDefinition actionDefinition) throws Exception {
		
	}
	
	public ResponseBuilder retry(final WorkflowDefinition workflowDefinition, final ActionDefinition actionDefinition, final WorkflowException exception) {
		return null;
	}
}
