package epf.workflow.expressions;

import java.util.Objects;
import jakarta.el.ELProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonValue;

/**
 * 
 */
@ApplicationScoped
public class WorkflowExpressions {
	
	/**
	 * @param filter
	 * @param object
	 * @return
	 * @throws Exception 
	 */
	public JsonValue getValue(final String filter, final JsonValue data) throws Exception {
		final ELProcessor elProcessor = newELProcessor(data);
		return elProcessor.getValue(filter, JsonValue.class);
	}
	
	/**
	 * @param data
	 * @param object
	 * @param value
	 * @throws Exception 
	 */
	public void setValue(final String filter, final JsonValue data, final JsonValue value) throws Exception {
		final ELProcessor elProcessor = newELProcessor(data);
		elProcessor.setValue(filter, value);
	}
	
	/**
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public ELProcessor newELProcessor(final JsonValue data) throws Exception {
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
	 * @throws Exception 
	 */
	public Boolean evaluateCondition(final JsonValue data, final String condition) throws Exception {
		Objects.requireNonNull(data, "JsonValue");
		Objects.requireNonNull(condition, "String");
		final ELProcessor elProcessor = newELProcessor(data);
		return (Boolean) elProcessor.eval(condition);
	}
}
