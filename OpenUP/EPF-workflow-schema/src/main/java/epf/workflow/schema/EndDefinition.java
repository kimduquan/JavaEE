package epf.workflow.schema;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import epf.workflow.schema.event.ProducedEventDefinition;
import epf.workflow.schema.adapter.ContinueAsAdapter;
import jakarta.nosql.Column;
import java.util.List;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class EndDefinition {

	/**
	 * 
	 */
	@Column
	private Boolean terminate;
	/**
	 * 
	 */
	@Column
	private List<ProducedEventDefinition> produceEvents;
	/**
	 * 
	 */
	@Column
	private Boolean compensate = false;
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = ContinueAsAdapter.class)
	private Object continueAs;
	
	public Boolean isTerminate() {
		return terminate;
	}
	public void setTerminate(Boolean terminate) {
		this.terminate = terminate;
	}
	public List<ProducedEventDefinition> getProduceEvents() {
		return produceEvents;
	}
	public void setProduceEvents(List<ProducedEventDefinition> produceEvents) {
		this.produceEvents = produceEvents;
	}
	public Boolean isCompensate() {
		return compensate;
	}
	public void setCompensate(Boolean compensate) {
		this.compensate = compensate;
	}
	public Object getContinueAs() {
		return continueAs;
	}
	public void setContinueAs(Object continueAs) {
		this.continueAs = continueAs;
	}
}
