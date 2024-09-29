package epf.lang.schema.ollama;

import java.util.List;
import java.util.Map;

/**
 * 
 */
public class Parameters {
	
	/**
	 * 
	 */
	private String type;
    /**
     * 
     */
    private Map<String, Parameter> properties;
    /**
     * 
     */
    private List<String> required;
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, Parameter> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, Parameter> properties) {
		this.properties = properties;
	}
	public List<String> getRequired() {
		return required;
	}
	public void setRequired(List<String> required) {
		this.required = required;
	}
}
