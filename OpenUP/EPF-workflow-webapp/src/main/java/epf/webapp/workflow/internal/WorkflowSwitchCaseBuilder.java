package epf.webapp.workflow.internal;

import javax.el.ValueExpression;
import javax.faces.flow.builder.SwitchCaseBuilder;

/**
 * @author PC
 *
 */
public class WorkflowSwitchCaseBuilder extends SwitchCaseBuilder {

	@Override
	public SwitchCaseBuilder switchCase() {
		return this;
	}

	@Override
	public SwitchCaseBuilder condition(ValueExpression expression) {
		return this;
	}

	@Override
	public SwitchCaseBuilder condition(String expression) {
		return this;
	}

	@Override
	public SwitchCaseBuilder fromOutcome(String outcome) {
		return this;
	}

}
