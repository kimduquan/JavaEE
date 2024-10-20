package epf.workflow.schema;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import epf.workflow.schema.event.ProducedEventDefinition;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.adapter.StringOrContinueAsAdapter;
import jakarta.nosql.Column;
import java.io.Serializable;
import java.util.List;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class EndDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	@Description("If true. terminates workflow instance execution")
	private Boolean terminate;
	
	@Column
	@Description("Array of producedEvent definitions. Defines events that should be produced.")
	private List<ProducedEventDefinition> produceEvents;
	
	@Column
	@Description("If set to true, triggers workflow compensation before workflow execution completes.")
	@DefaultValue("false")
	private Boolean compensate = false;
	
	@Column
	@Description("Defines that current workflow execution should stop, and execution should continue as a new workflow instance of the provided name")
	@JsonbTypeAdapter(value = StringOrContinueAsAdapter.class)
	private StringOrObject<ContinueAs> continueAs;
	
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
	public StringOrObject<ContinueAs> getContinueAs() {
		return continueAs;
	}
	public void setContinueAs(StringOrObject<ContinueAs> continueAs) {
		this.continueAs = continueAs;
	}
}
