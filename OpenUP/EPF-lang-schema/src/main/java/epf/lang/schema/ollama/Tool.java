package epf.lang.schema.ollama;

import java.io.Serializable;

/**
 * 
 */
public class Tool implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 
     */
    private String type;
    /**
     * 
     */
    private Function function;
    
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Function getFunction() {
		return function;
	}
	public void setFunction(Function function) {
		this.function = function;
	}
}
