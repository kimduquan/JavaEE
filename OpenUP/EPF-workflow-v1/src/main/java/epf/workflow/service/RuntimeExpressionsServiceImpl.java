package epf.workflow.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import epf.workflow.schema.ExpressionError;
import epf.workflow.schema.RuntimeExpressionArguments;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RuntimeExpressionsServiceImpl extends RuntimeExpressionsServiceBase {

	@Override
	public boolean if_(final String condition, final Map<String, Object> context, final Map<String, Object> secrets) throws ExpressionError {
		return false;
	}

	@Override
	protected void evaluate(final String expression, final RuntimeExpressionArguments arguments) {
		
	}

	@Override
	public <T> List<T> in(final String in, final Map<String, Object> context, final Map<String, Object> secrets) throws ExpressionError {
		return null;
	}

	@Override
	public Map<String, Object> createContext(final Map<String, Object> context) throws Error {
		return new LinkedHashMap<>(context);
	}

	@Override
	public void set(final Map<String, Object> context, final String name, final Object value) throws Error {
		context.put(name, value);
	}

	@Override
	public <T> T get(final Map<String, Object> context, final String name) throws Error {
		@SuppressWarnings("unchecked")
		final T value = (T)context.get(name);
		return value;
	}

}
