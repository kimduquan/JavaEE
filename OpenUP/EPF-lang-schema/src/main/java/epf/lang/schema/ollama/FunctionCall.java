package epf.lang.schema.ollama;

import java.util.Map;

/**
 * 
 */
public class FunctionCall {

	/**
	 * 
	 */
	private String name;
    /**
     * 
     */
    private Map<String, Object> arguments;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Object> getArguments() {
		return arguments;
	}
	public void setArguments(Map<String, Object> arguments) {
		this.arguments = arguments;
	}
}
