package epf.workflow;

import javax.json.JsonObject;

/**
 * @author PC
 *
 */
public class WorkflowData {

	/**
	 * 
	 */
	private JsonObject input;
	/**
	 * 
	 */
	private JsonObject output;
	
	public JsonObject getInput() {
		return input;
	}
	public void setInput(JsonObject input) {
		this.input = input;
	}
	public JsonObject getOutput() {
		return output;
	}
	public void setOutput(JsonObject output) {
		this.output = output;
	}
}
