package epf.workflow;

import epf.workflow.schema.WorkflowDefinition;

/**
 * @author PC
 *
 */
public class Instance {

	/**
	 * 
	 */
	private final WorkflowDefinition workflowDefinition;
	
	/**
	 * 
	 */
	private boolean active = false;
	
	/**
	 * @param workflowDefinition
	 */
	public Instance(WorkflowDefinition workflowDefinition) {
		this.workflowDefinition = workflowDefinition;
	}

	public WorkflowDefinition getWorkflowDefinition() {
		return workflowDefinition;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
