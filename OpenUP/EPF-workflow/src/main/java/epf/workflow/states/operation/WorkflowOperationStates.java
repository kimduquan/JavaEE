package epf.workflow.states.operation;

import epf.workflow.states.WorkflowStates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * 
 */
@ApplicationScoped
public class WorkflowOperationStates {
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowStates workflowStates;
}
