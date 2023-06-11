package epf.workflow.event.persistence;

import epf.naming.Naming;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;

/**
 * @author PC
 *
 */
@Entity(Naming.Workflow.EVENT)
public class EventStateActionEvent extends WorkflowEvent {
	
	/**
	 * 
	 */
	@Column("oneventsdefinition")
	private Integer onEventsDefinition;
	
	/**
	 * 
	 */
	@Column("actiondefinition")
	private Integer actionDefinition;

	public Integer getOnEventsDefinition() {
		return onEventsDefinition;
	}

	public void setOnEventsDefinition(Integer onEventsDefinition) {
		this.onEventsDefinition = onEventsDefinition;
	}

	public Integer getActionDefinition() {
		return actionDefinition;
	}

	public void setActionDefinition(Integer actionDefinition) {
		this.actionDefinition = actionDefinition;
	}
}
