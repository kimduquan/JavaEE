package epf.workflow;

import epf.workflow.schema.WorkflowError;

/**
 * 
 */
public class RetryableException extends WorkflowException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RetryableException(WorkflowError workflowError) {
		super(workflowError);
	}

}
