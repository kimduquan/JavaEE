package epf.workflow.retries;

import epf.workflow.error.WorkflowException;
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
