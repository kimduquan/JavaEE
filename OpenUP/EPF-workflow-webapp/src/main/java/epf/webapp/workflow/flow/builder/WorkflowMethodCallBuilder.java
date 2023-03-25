package epf.webapp.workflow.flow.builder;

import java.util.List;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.flow.MethodCallNode;
import javax.faces.flow.Parameter;
import javax.faces.flow.builder.MethodCallBuilder;

import epf.webapp.workflow.flow.WorkflowMethodCallNode;

/**
 * @author PC
 *
 */
public class WorkflowMethodCallBuilder extends MethodCallBuilder {
	
	/**
	 * 
	 */
	private WorkflowMethodCallNode methodCall;

	@Override
	public MethodCallBuilder expression(final MethodExpression methodExpression) {
		return this;
	}

	@Override
	public MethodCallBuilder expression(final String methodExpression) {
		return this;
	}

	@Override
	public MethodCallBuilder expression(final String methodExpression, @SuppressWarnings("rawtypes") final Class[] paramTypes) {
		return this;
	}

	@Override
	public MethodCallBuilder parameters(final List<Parameter> parameters) {
		return this;
	}

	@Override
	public MethodCallBuilder defaultOutcome(final String outcome) {
		return this;
	}

	@Override
	public MethodCallBuilder defaultOutcome(final ValueExpression outcome) {
		return this;
	}

	@Override
	public MethodCallBuilder markAsStartNode() {
		return this;
	}
	
	/**
	 * @return
	 */
	public MethodCallNode build() {
		return methodCall;
	}
}
