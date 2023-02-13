package epf.workflow;

import epf.workflow.schema.WorkflowError;

/**
 * @author PC
 *
 */
public class WorkflowException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final WorkflowError workflowError;
	
	/**
	 * @param workflowError
	 */
	public WorkflowException(WorkflowError workflowError) {
		super(workflowError.getDescription());
		this.workflowError = workflowError;
	}

	public WorkflowError getWorkflowError() {
		return workflowError;
	}
}
