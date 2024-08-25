package epf.workflow.retries;

import epf.workflow.error.WorkflowException;
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
