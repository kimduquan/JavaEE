package epf.workflow.schema;

import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.event.ProducedEventDefinition;
import jakarta.nosql.Column;
import java.io.Serializable;
import java.util.List;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class TransitionDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	private Boolean compensate = false;
	/**
	 * 
	 */
	@Column
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
