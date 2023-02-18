package epf.workflow;

import epf.workflow.schema.WorkflowDefinition;

/**
 * @author PC
 *
 */
public class WorkflowInstance {

	/**
	 * 
	 */
	private final WorkflowDefinition workflowDefinition;
	
	/**
	 * @param workflowDefinition
	 */
	public WorkflowInstance(WorkflowDefinition workflowDefinition) {
		this.workflowDefinition = workflowDefinition;
	}

	public WorkflowDefinition getWorkflowDefinition() {
		return workflowDefinition;
	}
}
