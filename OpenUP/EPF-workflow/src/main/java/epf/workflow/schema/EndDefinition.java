package epf.workflow.schema;

import javax.json.bind.annotation.JsonbTypeAdapter;
import epf.workflow.schema.adapter.ContinueAsAdapter;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

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
	private boolean terminate;
	/**
	 * 
	 */
	@Column
	private ProducedEventDefinition[] produceEvents;
	/**
	 * 
	 */
	@Column
	private boolean compensate = false;
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = ContinueAsAdapter.class)
	private Object continueAs;
	
	public boolean isTerminate() {
		return terminate;
	}
	public void setTerminate(boolean terminate) {
		this.terminate = terminate;
	}
	public ProducedEventDefinition[] getProduceEvents() {
		return produceEvents;
	}
	public void setProduceEvents(ProducedEventDefinition[] produceEvents) {
		this.produceEvents = produceEvents;
	}
	public boolean isCompensate() {
		return compensate;
	}
	public void setCompensate(boolean compensate) {
		this.compensate = compensate;
	}
	public Object getContinueAs() {
		return continueAs;
	}
	public void setContinueAs(Object continueAs) {
		this.continueAs = continueAs;
	}
}
