package epf.workflow;

import javax.json.JsonValue;
import epf.workflow.schema.mapping.JsonValueAttributeConverter;
import jakarta.nosql.mapping.Embeddable;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Convert;

/**
 * @author PC
 *
 */
@Embeddable
public class WorkflowData {

	/**
	 * 
	 */
	@Column
	@Convert(JsonValueAttributeConverter.class)
	private JsonValue input;
	/**
	 * 
	 */
	@Column
	@Convert(JsonValueAttributeConverter.class)
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
