package epf.workflow;

import java.io.Serializable;
import jakarta.json.JsonValue;

/**
 * @author PC
 *
 */
public class WorkflowData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private JsonValue input = JsonValue.NULL;
	/**
	 * 
	 */
	private JsonValue output = JsonValue.NULL;
	
	public JsonValue getInput() {
		return input;
	}
	public void setInput(JsonValue input) {
		this.input = input;
	}
	public JsonValue getOutput() {
		return output;
	}
	public void setOutput(JsonValue output) {
		this.output = output;
	}
}
