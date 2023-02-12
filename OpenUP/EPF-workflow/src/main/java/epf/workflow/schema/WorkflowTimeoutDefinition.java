package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class WorkflowTimeoutDefinition {

	/**
	 * 
	 */
	private Object workflowExecTimeout;
	
	/**
	 * 
	 */
	private String stateExecTimeout;
	
	/**
	 * 
	 */
	private String actionExecTimeout;
	
	/**
	 * 
	 */
	private String branchExecTimeout;
	
	/**
	 * 
	 */
	private String eventTimeout;

	public Object getWorkflowExecTimeout() {
		return workflowExecTimeout;
	}

	public void setWorkflowExecTimeout(Object workflowExecTimeout) {
		this.workflowExecTimeout = workflowExecTimeout;
	}

	public String getStateExecTimeout() {
		return stateExecTimeout;
	}

	public void setStateExecTimeout(String stateExecTimeout) {
		this.stateExecTimeout = stateExecTimeout;
	}

	public String getActionExecTimeout() {
		return actionExecTimeout;
	}

	public void setActionExecTimeout(String actionExecTimeout) {
		this.actionExecTimeout = actionExecTimeout;
	}

	public String getBranchExecTimeout() {
		return branchExecTimeout;
	}

	public void setBranchExecTimeout(String branchExecTimeout) {
		this.branchExecTimeout = branchExecTimeout;
	}

	public String getEventTimeout() {
		return eventTimeout;
	}

	public void setEventTimeout(String eventTimeout) {
		this.eventTimeout = eventTimeout;
	}
}
