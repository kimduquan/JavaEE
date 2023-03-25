package epf.webapp.workflow.flow.builder;

import javax.el.ValueExpression;
import javax.faces.flow.ReturnNode;
import javax.faces.flow.builder.ReturnBuilder;
import epf.webapp.workflow.flow.WorkflowReturnNode;

/**
 * @author PC
 *
 */
public class WorkflowReturnBuilder extends ReturnBuilder {
	
	/**
	 * 
	 */
	private WorkflowReturnNode _return;

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
	
	/**
	 * @return
	 */
	public ReturnNode build() {
		return _return;
	}
}
