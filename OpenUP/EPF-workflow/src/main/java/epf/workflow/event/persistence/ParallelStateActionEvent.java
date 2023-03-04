package epf.workflow.event.persistence;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;

/**
 * @author PC
 *
 */
@Entity
public class ParallelStateActionEvent extends WorkflowEvent {

	/**
	 * 
	 */
	@Column
	private String parallelState;
	
	/**
	 * 
	 */
	@Column
	private String branch;
	
	/**
	 * 
	 */
	@Column
	private String actionDefinition;

	public String getParallelState() {
		return parallelState;
	}

	public void setParallelState(String parallelState) {
		this.parallelState = parallelState;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getActionDefinition() {
		return actionDefinition;
	}

	public void setActionDefinition(String actionDefinition) {
		this.actionDefinition = actionDefinition;
	}
}
