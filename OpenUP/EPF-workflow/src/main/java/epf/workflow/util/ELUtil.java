package epf.workflow.util;

import java.util.Map;
import java.util.Objects;
import javax.el.ELProcessor;
import javax.json.JsonValue;
import epf.util.json.JsonUtil;

/**
 * @author PC
 *
 */
public interface ELUtil {
	
	/**
	 * @param filter
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	static Map<String, Object> getValue(final String filter, final Map<String, Object> object) throws Exception {
		final ELProcessor elProcessor = newELProcessor(object);
		return (Map<String, Object>) elProcessor.getValue(filter, JsonValue.class);
	}
	
	/**
	 * @param data
	 * @param object
	 * @param value
	 * @throws Exception 
	 */
	static void setValue(final String data, final Map<String, Object> object, final Object value) throws Exception {
		final ELProcessor elProcessor = newELProcessor(object);
		elProcessor.setValue(data, value);
	}
	
	/**
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	static ELProcessor newELProcessor(final Map<String, Object> data) throws Exception {
		final ELProcessor elProcessor = new ELProcessor();
		final JsonELResolver elResolver = new JsonELResolver(JsonUtil.toJsonValue(data));
		elResolver.defineBean(elProcessor);
		elProcessor.getELManager().addELResolver(elResolver);
		return elProcessor;
	}
	
	/**
	 * @param data
	 * @param condition
	 * @return
	 * @throws Exception 
	 */
	static Boolean evaluateCondition(final Map<String, Object> data, final String condition) throws Exception {
		Objects.requireNonNull(data, "JsonValue");
		Objects.requireNonNull(condition, "String");
		final ELProcessor elProcessor = newELProcessor(data);
		return (Boolean) elProcessor.eval(condition);
	}
}
