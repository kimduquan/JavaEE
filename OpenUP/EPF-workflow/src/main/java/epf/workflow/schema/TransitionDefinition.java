package epf.workflow.schema;

import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class TransitionDefinition {

	/**
	 * 
	 */
	@NotNull
	@Column
	private String nextState;
	/**
	 * 
	 */
	@Column
	private boolean compensate = false;
	/**
	 * 
	 */
	@Column
	private ProducedEventDefinition[] produceEvents;
	
	public String getNextState() {
		return nextState;
	}
	public void setNextState(String nextState) {
		this.nextState = nextState;
	}
	public boolean isCompensate() {
		return compensate;
	}
	public void setCompensate(boolean compensate) {
		this.compensate = compensate;
	}
	public ProducedEventDefinition[] getProduceEvents() {
		return produceEvents;
	}
	public void setProduceEvents(ProducedEventDefinition[] produceEvents) {
		this.produceEvents = produceEvents;
	}
}
