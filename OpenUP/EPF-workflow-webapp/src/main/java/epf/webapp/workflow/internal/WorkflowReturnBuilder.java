package epf.webapp.workflow.internal;

import javax.el.ValueExpression;
import javax.faces.flow.builder.ReturnBuilder;

/**
 * @author PC
 *
 */
public class WorkflowReturnBuilder extends ReturnBuilder {

	@Override
	public ReturnBuilder fromOutcome(final String outcome) {
		return this;
	}

	@Override
	public ReturnBuilder fromOutcome(final ValueExpression outcome) {
		return this;
	}

	@Override
	public ReturnBuilder markAsStartNode() {
		return this;
	}
}
