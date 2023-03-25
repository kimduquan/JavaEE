package epf.webapp.workflow.flow.builder;

import javax.el.ValueExpression;
import javax.faces.flow.SwitchNode;
import javax.faces.flow.builder.SwitchBuilder;
import javax.faces.flow.builder.SwitchCaseBuilder;

import epf.webapp.workflow.flow.WorkflowSwitchNode;

/**
 * @author PC
 *
 */
public class WorkflowSwitchBuilder extends SwitchBuilder {
	
	/**
	 * 
	 */
	private WorkflowSwitchCaseBuilder switchCase;
	
	/**
	 * 
	 */
	private WorkflowSwitchNode _switch;

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

	/**
	 * @return
	 */
	public SwitchNode build() {
		return _switch;
	}
}
