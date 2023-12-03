package epf.workflow;

import epf.workflow.schema.WorkflowError;

/**
 * 
 */
public class NonRetryableException extends WorkflowException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NonRetryableException(WorkflowError workflowError) {
		super(workflowError);
	}

}
