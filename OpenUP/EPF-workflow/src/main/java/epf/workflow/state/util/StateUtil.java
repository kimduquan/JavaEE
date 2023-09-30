package epf.workflow.state.util;

import java.util.Map;
import epf.util.json.JsonUtil;
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
	 * @throws Exception 
	 */
	static void mergeStateDataOutput(final String data, final WorkflowData to, final Map<String, Object> fromOutput) throws Exception {
		Map<String, Object> output = to.getOutput();
		output = ELUtil.getValue(data, to.getOutput());
		final Map<String, Object> newOutput = JsonUtil.asMap(JsonUtil.createMergeDiff(output, fromOutput).apply(JsonUtil.toJsonValue(output)).asJsonObject());
		ELUtil.setValue(data, to.getOutput(), newOutput);
	}
	
	/**
	 * @param to
	 * @param fromOutput
	 * @throws Exception 
	 */
	static void mergeStateDataOutput(final WorkflowData to, final Map<String, Object> fromOutput) throws Exception {
		Map<String, Object> output = to.getOutput();
		final Map<String, Object> newOutput = JsonUtil.asMap(JsonUtil.createMergeDiff(output, fromOutput).apply(JsonUtil.toJsonValue(output)).asJsonObject());
		to.setOutput(newOutput);
	}
	
	/**
	 * @param data
	 * @param to
	 * @param fromInput
	 * @throws Exception 
	 */
	static void mergeStateDataInput(final String data, final WorkflowData to, final Map<String, Object> fromInput) throws Exception {
		Map<String, Object> input = to.getInput();
		input = ELUtil.getValue(data, to.getInput());
		final Map<String, Object> newInput = JsonUtil.asMap(JsonUtil.createMergeDiff(input, fromInput).apply(JsonUtil.toJsonValue(input)).asJsonObject());
		ELUtil.setValue(data, to.getInput(), newInput);
	}
	
	/**
	 * @param to
	 * @param fromInput
	 * @throws Exception 
	 */
	static void mergeStateDataInput(final WorkflowData to, final Map<String, Object> fromInput) throws Exception {
		Map<String, Object> input = to.getInput();
		final Map<String, Object> newInput = JsonUtil.asMap(JsonUtil.createMergeDiff(input, fromInput).apply(JsonUtil.toJsonValue(input)).asJsonObject());
		to.setInput(newInput);
	}
}
