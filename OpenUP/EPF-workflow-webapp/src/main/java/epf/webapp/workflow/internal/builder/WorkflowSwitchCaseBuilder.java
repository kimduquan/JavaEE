package epf.webapp.workflow.internal.builder;

import javax.el.ValueExpression;
import javax.faces.flow.SwitchCase;
import javax.faces.flow.builder.SwitchCaseBuilder;

import epf.webapp.workflow.internal.WorkflowSwitchCase;

/**
 * @author PC
 *
 */
public class WorkflowSwitchCaseBuilder extends SwitchCaseBuilder {
	
	/**
	 * 
	 */
	private WorkflowSwitchCase switchCase;

	@Override
	public SwitchCaseBuilder switchCase() {
		return this;
	}

	@Override
	public SwitchCaseBuilder condition(final ValueExpression expression) {
		return this;
	}

	@Override
	public SwitchCaseBuilder condition(final String expression) {
		return this;
	}

	@Override
	public SwitchCaseBuilder fromOutcome(final String outcome) {
		return this;
	}

	/**
	 * @return
	 */
	public SwitchCase build() {
		return switchCase;
	}
}
