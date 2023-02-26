package epf.workflow.event.schema;

import javax.validation.constraints.NotNull;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class ProducedEventDefinition {

	/**
	 * 
	 */
	@NotNull
	@Column
	private String eventRef;
	/**
	 * 
	 */
	@Column
	private Object data;
	/**
	 * 
	 */
	@Column
	private Object contextAttributes;
	
	public String getEventRef() {
		return eventRef;
	}
	public void setEventRef(String eventRef) {
		this.eventRef = eventRef;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Object getContextAttributes() {
		return contextAttributes;
	}
	public void setContextAttributes(Object contextAttributes) {
		this.contextAttributes = contextAttributes;
	}
}
