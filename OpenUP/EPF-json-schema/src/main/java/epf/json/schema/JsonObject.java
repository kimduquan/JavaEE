package epf.json.schema;

import java.util.Map;

/**
 * @author PC
 *
 */
public class JsonObject extends TypeValue {

	/**
	 * 
	 */
	private Map<String, Value> properties;
	/**
	 * 
	 */
	private Map<String, Value> patternProperties;
	/**
	 * 
	 */
	private boolean additionalProperties = true;
	/**
	 * 
	 */
	private boolean unevaluatedProperties;
	/**
	 * 
	 */
	private String[] required;
	/**
	 * 
	 */
	private JsonString propertyNames;
	/**
	 * 
	 */
	private int minProperties;
	/**
	 * 
	 */
	private int maxProperties;
	
	public Map<String, Value> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, Value> properties) {
		this.properties = properties;
	}
	public Map<String, Value> getPatternProperties() {
		return patternProperties;
	}
	public void setPatternProperties(Map<String, Value> patternProperties) {
		this.patternProperties = patternProperties;
	}
	public boolean isAdditionalProperties() {
		return additionalProperties;
	}
	public void setAdditionalProperties(boolean additionalProperties) {
		this.additionalProperties = additionalProperties;
	}
	public boolean isUnevaluatedProperties() {
		return unevaluatedProperties;
	}
	public void setUnevaluatedProperties(boolean unevaluatedProperties) {
		this.unevaluatedProperties = unevaluatedProperties;
	}
	public String[] getRequired() {
		return required;
	}
	public void setRequired(String[] required) {
		this.required = required;
	}
	public JsonString getPropertyNames() {
		return propertyNames;
	}
	public void setPropertyNames(JsonString propertyNames) {
		this.propertyNames = propertyNames;
	}
	public int getMinProperties() {
		return minProperties;
	}
	public void setMinProperties(int minProperties) {
		this.minProperties = minProperties;
	}
	public int getMaxProperties() {
		return maxProperties;
	}
	public void setMaxProperties(int maxProperties) {
		this.maxProperties = maxProperties;
	}
}
