package epf.workflow.event.persistence;

import epf.naming.Naming;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;

/**
 * @author PC
 *
 */
@Entity(Naming.Workflow.EVENT)
public class EventStateEvent extends WorkflowEvent {
	
	/**
	 * 
	 */
	@Column("oneventsdefinition")
	private Integer onEventsDefinition;

	public Integer getOnEventsDefinition() {
		return onEventsDefinition;
	}

	public void setOnEventsDefinition(Integer onEventsDefinition) {
		this.onEventsDefinition = onEventsDefinition;
	}
}
