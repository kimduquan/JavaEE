package epf.workflow.error;

import epf.workflow.schema.error.ErrorReference;

public class WorkflowException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ErrorReference workflowError;
	
	public WorkflowException(final ErrorReference workflowError) {
		super();
		this.workflowError = workflowError;
	}

	public ErrorReference getWorkflowError() {
		return workflowError;
	}
}
