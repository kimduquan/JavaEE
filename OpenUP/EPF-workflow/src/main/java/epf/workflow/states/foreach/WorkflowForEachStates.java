package epf.workflow.states.foreach;

import epf.workflow.states.WorkflowStates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 * 
 */
@ApplicationScoped
public class WorkflowForEachStates {
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowStates workflowStates;

}
