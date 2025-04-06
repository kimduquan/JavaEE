package epf.workflow.schema.event;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import java.io.Serializable;
import java.util.Map;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.Description;
import epf.nosql.schema.StringOrObject;

@Embeddable
public class ProducedEventDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	@Description("Reference to a defined unique event name in the events definition")
	private String eventRef;
	
	@Column
	@Description("If string type, an expression which selects parts of the states data output to become the data (payload) of the produced event. If object type, a custom object to become the data (payload) of produced event.")
	private StringOrObject<Object> data;
	
	@Column
	@Description("Add additional event extension context attributes")
	private Map<String, Object> contextAttributes;
	
	public String getEventRef() {
		return eventRef;
	}
	public void setEventRef(String eventRef) {
		this.eventRef = eventRef;
	}
	public StringOrObject<Object> getData() {
		return data;
	}
	public void setData(StringOrObject<Object> data) {
		this.data = data;
	}
	public Map<String, Object> getContextAttributes() {
		return contextAttributes;
	}
	public void setContextAttributes(Map<String, Object> contextAttributes) {
		this.contextAttributes = contextAttributes;
	}
}
