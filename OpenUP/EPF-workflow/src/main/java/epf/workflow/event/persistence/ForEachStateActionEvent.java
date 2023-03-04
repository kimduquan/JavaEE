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
	private String forEachState;
	
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

	public String getForEachState() {
		return forEachState;
	}

	public void setForEachState(String forEachState) {
		this.forEachState = forEachState;
	}

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
