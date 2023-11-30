package epf.workflow.state.util;

import epf.workflow.WorkflowData;
import epf.workflow.util.ELUtil;
import jakarta.json.Json;
import jakarta.json.JsonValue;

/**
 * @author PC
 *
 */
public interface StateUtil {
	
	/**
	 * @param data
	 * @param to
	 * @param fromOutput
	 * @throws Exception 
	 */
	static void mergeStateDataOutput(final String data, final WorkflowData to, final JsonValue fromOutput) throws Exception {
		JsonValue output = to.getOutput();
		output = ELUtil.getValue(data, to.getOutput());
		final JsonValue newOutput = Json.createMergeDiff(output, fromOutput).apply(output);
		ELUtil.setValue(data, to.getOutput(), newOutput);
	}
	
	/**
	 * @param to
	 * @param fromOutput
	 * @throws Exception 
	 */
	static void mergeStateDataOutput(final WorkflowData to, final JsonValue fromOutput) throws Exception {
		JsonValue output = to.getOutput();
		final JsonValue newOutput = Json.createMergeDiff(output, fromOutput).apply(output);
		to.setOutput(newOutput);
	}
	
	/**
	 * @param data
	 * @param to
	 * @param fromInput
	 * @throws Exception 
	 */
	static void mergeStateDataInput(final String data, final WorkflowData to, final JsonValue fromInput) throws Exception {
		JsonValue input = to.getInput();
		input = ELUtil.getValue(data, to.getInput());
		final JsonValue newInput = Json.createMergeDiff(input, fromInput).apply(input);
		ELUtil.setValue(data, to.getInput(), newInput);
	}
	
	/**
	 * @param to
	 * @param fromInput
	 * @throws Exception 
	 */
	static void mergeStateDataInput(final WorkflowData to, final JsonValue fromInput) throws Exception {
		JsonValue input = to.getInput();
		final JsonValue newInput = Json.createMergeDiff(input, fromInput).apply(input);
		to.setInput(newInput);
	}
}
