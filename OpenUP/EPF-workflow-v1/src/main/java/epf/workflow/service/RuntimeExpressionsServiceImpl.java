package epf.workflow.service;

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

}
