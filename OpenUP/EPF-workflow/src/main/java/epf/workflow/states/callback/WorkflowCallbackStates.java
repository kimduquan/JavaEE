package epf.workflow.states.callback;

import epf.workflow.states.WorkflowStates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * 
 */
@ApplicationScoped
public class WorkflowCallbackStates {
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowStates workflowStates;

}
