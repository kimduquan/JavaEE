package epf.workflow.expressions;

import java.util.Objects;
import jakarta.el.ELProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonValue;

@ApplicationScoped
public class WorkflowExpressions {
	
	public JsonValue getValue(final String filter, final JsonValue data) throws Exception {
		final ELProcessor elProcessor = newELProcessor(data);
		return elProcessor.getValue(filter, JsonValue.class);
	}
	
	public void setValue(final String filter, final JsonValue data, final JsonValue value) throws Exception {
		final ELProcessor elProcessor = newELProcessor(data);
		elProcessor.setValue(filter, value);
	}
	
	private static ELProcessor newELProcessor(final JsonValue data) throws Exception {
		final ELProcessor elProcessor = new ELProcessor();
		final JsonELResolver elResolver = new JsonELResolver(data);
		elResolver.defineBean(elProcessor);
		elProcessor.getELManager().addELResolver(elResolver);
		return elProcessor;
	}
	
	public Boolean evaluateCondition(final JsonValue data, final String condition) throws Exception {
		Objects.requireNonNull(data, "JsonValue");
		Objects.requireNonNull(condition, "String");
		final ELProcessor elProcessor = newELProcessor(data);
		return (Boolean) elProcessor.eval(condition);
	}
}
