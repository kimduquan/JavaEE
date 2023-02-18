package epf.workflow;

import javax.json.JsonValue;

/**
 * @author PC
 *
 */
public class WorkflowData {

	/**
	 * 
	 */
	private JsonValue input;
	/**
	 * 
	 */
	private JsonValue output;
	
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
