package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class ProducedEventDefinition {

	/**
	 * 
	 */
	private String eventRef;
	/**
	 * 
	 */
	private Object data;
	/**
	 * 
	 */
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
