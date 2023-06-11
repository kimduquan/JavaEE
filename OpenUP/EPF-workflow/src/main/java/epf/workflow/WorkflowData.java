package epf.workflow;

import javax.json.JsonValue;
import jakarta.nosql.mapping.Convert;
import jakarta.nosql.mapping.Embeddable;
import epf.workflow.schema.mapping.JsonValueAttributeConverter;
import jakarta.nosql.mapping.Column;

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
	private JsonValue input = JsonValue.EMPTY_JSON_OBJECT;
	/**
	 * 
	 */
	@Column
	@Convert(JsonValueAttributeConverter.class)
	private JsonValue output = JsonValue.EMPTY_JSON_OBJECT;
	
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
