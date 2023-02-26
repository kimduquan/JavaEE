package epf.workflow.util;

import java.util.Objects;
import javax.el.ELProcessor;
import javax.json.JsonValue;

/**
 * @author PC
 *
 */
public interface ELUtil {
	
	/**
	 * @param filter
	 * @param object
	 * @return
	 */
	static JsonValue getValue(final String filter, final JsonValue object) {
		final ELProcessor elProcessor = newELProcessor(object);
		return (JsonValue) elProcessor.getValue(filter, JsonValue.class);
	}
	
	/**
	 * @param data
	 * @param object
	 * @param value
	 */
	static void setValue(final String data, final JsonValue object, final JsonValue value) {
		final ELProcessor elProcessor = newELProcessor(object);
		elProcessor.setValue(data, value);
	}
	
	/**
	 * @param data
	 * @return
	 */
	static ELProcessor newELProcessor(final JsonValue data) {
		final ELProcessor elProcessor = new ELProcessor();
		final JsonELResolver elResolver = new JsonELResolver(data);
		elResolver.defineBean(elProcessor);
		elProcessor.getELManager().addELResolver(elResolver);
		return elProcessor;
	}
	
	/**
	 * @param data
	 * @param condition
	 * @return
	 */
	static Boolean evaluateCondition(final JsonValue data, final String condition) {
		Objects.requireNonNull(data, "JsonValue");
		Objects.requireNonNull(condition, "String");
		final ELProcessor elProcessor = newELProcessor(data);
		return (Boolean) elProcessor.eval(condition);
	}
}
