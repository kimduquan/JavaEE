package epf.workflow.states._switch;

import epf.workflow.states.WorkflowStates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * 
 */
@ApplicationScoped
public class WorkflowSwitchStates {
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowStates workflowStates;

}
