package epf.workflow.data;

import epf.workflow.expressions.ELUtil;
import jakarta.json.Json;
import jakarta.json.JsonValue;

/**
 * @author PC
 *
 */
public interface StateUtil {
	
	/**
	 * @param data
	 * @param toInput
	 * @param fromOutput
	 * @throws Exception
	 */
	static JsonValue mergeStateDataOutput(final String data, final JsonValue toInput, final JsonValue fromOutput) throws Exception {
		JsonValue output = toInput;
		output = ELUtil.getValue(data, toInput);
		final JsonValue newOutput = Json.createMergeDiff(output, fromOutput).apply(output);
		ELUtil.setValue(data, toInput, newOutput);
		return toInput;
	}
	
	/**
	 * @param toOutput
	 * @param fromOutput
	 * @return
	 * @throws Exception
	 */
	static JsonValue mergeStateDataOutput(final JsonValue toOutput, final JsonValue fromOutput) throws Exception {
		final JsonValue newOutput = Json.createMergeDiff(toOutput, fromOutput).apply(toOutput);
		return newOutput;
	}
	
	/**
	 * @param data
	 * @param toInput
	 * @param fromInput
	 * @throws Exception
	 */
	static void mergeStateDataInput(final String data, final JsonValue toInput, final JsonValue fromInput) throws Exception {
		JsonValue input = toInput;
		input = ELUtil.getValue(data, toInput);
		final JsonValue newInput = Json.createMergeDiff(input, fromInput).apply(input);
		ELUtil.setValue(data, toInput, newInput);
	}
	
	/**
	 * @param toInput
	 * @param fromInput
	 * @return
	 * @throws Exception
	 */
	static JsonValue mergeStateDataInput(final JsonValue toInput, final JsonValue fromInput) throws Exception {
		final JsonValue newInput = Json.createMergeDiff(toInput, fromInput).apply(toInput);
		return newInput;
	}
}
