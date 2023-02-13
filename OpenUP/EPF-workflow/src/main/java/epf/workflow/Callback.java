package epf.workflow;

import epf.workflow.schema.CallbackState;
import epf.workflow.schema.WorkflowDefinition;

/**
 * @author PC
 *
 */
public class Callback {

	/**
	 * 
	 */
	private final WorkflowDefinition workflowDefinition;
	/**
	 * 
	 */
	private final CallbackState callbackState;
	
	/**
	 * 
	 */
	private final WorkflowData workflowData;
	
	/**
	 * @param workflowDefinition
	 * @param callbackState
	 * @param workflowData
	 */
	public Callback(WorkflowDefinition workflowDefinition, CallbackState callbackState, WorkflowData workflowData) {
		this.workflowDefinition = workflowDefinition;
		this.callbackState = callbackState;
		this.workflowData = workflowData;
	}

	public WorkflowDefinition getWorkflowDefinition() {
		return workflowDefinition;
	}

	public CallbackState getCallbackState() {
		return callbackState;
	}

	public WorkflowData getWorkflowData() {
		return workflowData;
	}
}
