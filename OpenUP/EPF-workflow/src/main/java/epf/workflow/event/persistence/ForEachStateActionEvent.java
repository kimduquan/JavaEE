package epf.workflow.event.persistence;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;

/**
 * @author PC
 *
 */
@Entity
public class ForEachStateActionEvent extends WorkflowEvent {
	
	/**
	 * 
	 */
	@Column
	private Integer iteration;
	
	/**
	 * 
	 */
	@Column
	private String actionDefinition;

	public Integer getIteration() {
		return iteration;
	}

	public void setIteration(Integer iteration) {
		this.iteration = iteration;
	}

	public String getActionDefinition() {
		return actionDefinition;
	}

	public void setActionDefinition(String actionDefinition) {
		this.actionDefinition = actionDefinition;
	}
}
