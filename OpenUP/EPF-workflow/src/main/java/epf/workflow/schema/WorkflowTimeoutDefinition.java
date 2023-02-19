package epf.workflow.schema;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class WorkflowTimeoutDefinition {

	/**
	 * 
	 */
	@Column
	private Object workflowExecTimeout;
	
	/**
	 * 
	 */
	@Column
	private String stateExecTimeout;
	
	/**
	 * 
	 */
	@Column
	private String actionExecTimeout;
	
	/**
	 * 
	 */
	@Column
	private String branchExecTimeout;
	
	/**
	 * 
	 */
	@Column
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
