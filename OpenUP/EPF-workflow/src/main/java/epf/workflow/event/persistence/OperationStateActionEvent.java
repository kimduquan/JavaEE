package epf.workflow.event.persistence;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.Entity;

/**
 * @author PC
 *
 */
@Entity
public class OperationStateActionEvent extends WorkflowEvent {
	
	/**
	 * 
	 */
	@Column
	private String actionDefinition;

	public String getActionDefinition() {
		return actionDefinition;
	}

	public void setActionDefinition(String actionDefinition) {
		this.actionDefinition = actionDefinition;
	}
}
