package epf.json.schema;

import java.util.Map;

public class JsonObject extends TypeValue {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Value> properties;
	private Map<String, Value> patternProperties;
	private Boolean additionalProperties;
	private Boolean unevaluatedProperties;
	private String[] required;
	private JsonString propertyNames;
	private Integer minProperties;
	private Integer maxProperties;
	
	public JsonObject() {
		setType(Type.object);
	}
	
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
	public Boolean isAdditionalProperties() {
		return additionalProperties;
	}
	public void setAdditionalProperties(Boolean additionalProperties) {
		this.additionalProperties = additionalProperties;
	}
	public Boolean isUnevaluatedProperties() {
		return unevaluatedProperties;
	}
	public void setUnevaluatedProperties(Boolean unevaluatedProperties) {
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
	public Integer getMinProperties() {
		return minProperties;
	}
	public void setMinProperties(Integer minProperties) {
		this.minProperties = minProperties;
	}
	public Integer getMaxProperties() {
		return maxProperties;
	}
	public void setMaxProperties(Integer maxProperties) {
		this.maxProperties = maxProperties;
	}
}
