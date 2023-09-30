package epf.workflow.state.util;

import java.util.Map;
import javax.json.Json;
import javax.json.JsonValue;
import epf.workflow.schema.WorkflowData;
import epf.workflow.util.ELUtil;

/**
 * @author PC
 *
 */
public interface StateUtil {
	
	/**
	 * @param data
	 * @param to
	 * @param fromOutput
	 */
	static void mergeStateDataOutput(final String data, final WorkflowData to, final Map<String, Object> fromOutput) {
		Map<String, Object> output = to.getOutput();
		output = ELUtil.getValue(data, to.getOutput());
		final Map<String, Object> newOutput = Json.createMergeDiff(output, fromOutput).apply(output);
		ELUtil.setValue(data, to.getOutput(), newOutput);
	}
	
	/**
	 * @param to
	 * @param fromOutput
	 */
	static void mergeStateDataOutput(final WorkflowData to, final Map<String, Object> fromOutput) {
		Map<String, Object> output = to.getOutput();
		final Map<String, Object> newOutput = Json.createMergeDiff(output, fromOutput).apply(output);
		to.setOutput(newOutput);
	}
	
	/**
	 * @param data
	 * @param to
	 * @param fromInput
	 */
	static void mergeStateDataInput(final String data, final WorkflowData to, final Map<String, Object> fromInput) {
		Map<String, Object> input = to.getInput();
		input = ELUtil.getValue(data, to.getInput());
		final Map<String, Object> newInput = Json.createMergeDiff(input, fromInput).apply(input);
		ELUtil.setValue(data, to.getInput(), newInput);
	}
	
	/**
	 * @param to
	 * @param fromInput
	 */
	static void mergeStateDataInput(final WorkflowData to, final Map<String, Object> fromInput) {
		Map<String, Object> input = to.getInput();
		final Map<String, Object> newInput = Json.createMergeDiff(input, fromInput).apply(input);
		to.setInput(newInput);
	}
}
