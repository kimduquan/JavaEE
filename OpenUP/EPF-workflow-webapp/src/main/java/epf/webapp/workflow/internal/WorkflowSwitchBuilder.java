package epf.webapp.workflow.internal;

import javax.el.ValueExpression;
import javax.faces.flow.builder.SwitchBuilder;
import javax.faces.flow.builder.SwitchCaseBuilder;

/**
 * @author PC
 *
 */
public class WorkflowSwitchBuilder extends SwitchBuilder {
	
	/**
	 * 
	 */
	private WorkflowSwitchCaseBuilder switchCase;

	@Override
	public SwitchCaseBuilder switchCase() {
		return switchCase;
	}

	@Override
	public SwitchCaseBuilder defaultOutcome(final String outcome) {
		return switchCase;
	}

	@Override
	public SwitchCaseBuilder defaultOutcome(final ValueExpression outcome) {
		return switchCase;
	}

	@Override
	public SwitchBuilder markAsStartNode() {
		return this;
	}

}
