package epf.workflow.event.persistence;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;

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
	private String branch;
	
	/**
	 * 
	 */
	@Column
	private String actionDefinition;

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
