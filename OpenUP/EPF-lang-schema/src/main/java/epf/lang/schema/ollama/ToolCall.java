package epf.lang.schema.ollama;

import java.io.Serializable;

/**
 * 
 */
public class ToolCall implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private FunctionCall function;

	public FunctionCall getFunction() {
		return function;
	}

	public void setFunction(FunctionCall function) {
		this.function = function;
	}
}
