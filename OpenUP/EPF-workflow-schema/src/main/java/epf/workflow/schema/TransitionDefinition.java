package epf.workflow.schema;

import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.event.ProducedEventDefinition;
import jakarta.nosql.Column;
import java.io.Serializable;
import java.util.List;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class TransitionDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	@Description("Name of the state to transition to next")
	private String nextState;
	
	@Column
	@Description("If set to true, triggers workflow compensation before this transition is taken.")
	@DefaultValue("false")
	private Boolean compensate = false;
	
	@Column
	@Description("Array of producedEvent definitions. Events to be produced before the transition takes place")
	private List<ProducedEventDefinition> produceEvents;
	
	public String getNextState() {
		return nextState;
	}
	public void setNextState(String nextState) {
		this.nextState = nextState;
	}
	public Boolean isCompensate() {
		return compensate;
	}
	public void setCompensate(Boolean compensate) {
		this.compensate = compensate;
	}
	public List<ProducedEventDefinition> getProduceEvents() {
		return produceEvents;
	}
	public void setProduceEvents(List<ProducedEventDefinition> produceEvents) {
		this.produceEvents = produceEvents;
	}
}
