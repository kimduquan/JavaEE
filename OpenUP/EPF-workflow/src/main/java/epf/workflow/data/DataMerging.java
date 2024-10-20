package epf.workflow.data;

import epf.workflow.expressions.WorkflowExpressions;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonValue;

@ApplicationScoped
public class DataMerging {
	
	@Inject
	transient WorkflowExpressions workflowExpressions;
	
	public JsonValue mergeStateDataOutput(final String data, final JsonValue toInput, final JsonValue fromOutput) throws Exception {
		JsonValue output = toInput;
		output = workflowExpressions.getValue(data, toInput);
		final JsonValue newOutput = Json.createMergeDiff(output, fromOutput).apply(output);
		workflowExpressions.setValue(data, toInput, newOutput);
		return toInput;
	}
	
	public JsonValue mergeStateDataOutput(final JsonValue toOutput, final JsonValue fromOutput) throws Exception {
		final JsonValue newOutput = Json.createMergeDiff(toOutput, fromOutput).apply(toOutput);
		return newOutput;
	}
	
	public void mergeStateDataInput(final String data, final JsonValue toInput, final JsonValue fromInput) throws Exception {
		JsonValue input = toInput;
		input = workflowExpressions.getValue(data, toInput);
		final JsonValue newInput = Json.createMergeDiff(input, fromInput).apply(input);
		workflowExpressions.setValue(data, toInput, newInput);
	}
	
	public static JsonValue mergeStateDataInput(final JsonValue toInput, final JsonValue fromInput) throws Exception {
		final JsonValue newInput = Json.createMergeDiff(toInput, fromInput).apply(toInput);
		return newInput;
	}
}
