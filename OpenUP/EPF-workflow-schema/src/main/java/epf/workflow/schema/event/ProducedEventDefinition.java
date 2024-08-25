package epf.workflow.schema.event;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import java.io.Serializable;
import java.util.Map;

import org.eclipse.jnosql.mapping.Embeddable;

import epf.nosql.schema.StringOrObject;

/**
 * @author PC
 *
 */
@Embeddable
public class ProducedEventDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	private StringOrObject<Object> data;
	/**
	 * 
	 */
	@Column
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
