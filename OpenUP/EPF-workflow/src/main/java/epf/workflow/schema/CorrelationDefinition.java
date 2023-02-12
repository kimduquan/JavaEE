package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class CorrelationDefinition {

	/**
	 * 
	 */
	private String contextAttributeName;
	/**
	 * 
	 */
	private String contextAttributeValue;
	
	public String getContextAttributeName() {
		return contextAttributeName;
	}
	public void setContextAttributeName(String contextAttributeName) {
		this.contextAttributeName = contextAttributeName;
	}
	public String getContextAttributeValue() {
		return contextAttributeValue;
	}
	public void setContextAttributeValue(String contextAttributeValue) {
		this.contextAttributeValue = contextAttributeValue;
	}
}
