package epf.workflow.state;

import epf.workflow.WorkflowInstance;
import epf.workflow.state.schema.CallbackState;

/**
 * @author PC
 *
 */
public class Callback {

	/**
	 * 
	 */
	private final WorkflowInstance workflowInstance;
	/**
	 * 
	 */
	private final CallbackState callbackState;
	
	/**
	 * @param callbackState
	 * @param workflowInstance
	 */
	public Callback(CallbackState callbackState, WorkflowInstance workflowInstance) {
		this.workflowInstance = workflowInstance;
		this.callbackState = callbackState;
	}

	public WorkflowInstance getWorkflowInstance() {
		return workflowInstance;
	}

	public CallbackState getCallbackState() {
		return callbackState;
	}
}