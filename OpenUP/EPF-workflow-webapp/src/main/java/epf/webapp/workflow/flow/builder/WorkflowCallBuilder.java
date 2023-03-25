package epf.webapp.workflow.flow.builder;

import javax.el.ValueExpression;
import javax.faces.flow.FlowCallNode;
import javax.faces.flow.builder.FlowCallBuilder;
import epf.webapp.workflow.flow.WorkflowCallNode;

/**
 * @author PC
 *
 */
public class WorkflowCallBuilder extends FlowCallBuilder {
	
	/**
	 * 
	 */
	private WorkflowCallNode flowCall;

	@Override
	public FlowCallBuilder flowReference(final String flowDocumentId, final String flowId) {
		return this;
	}

	@Override
	public FlowCallBuilder outboundParameter(final String name, final ValueExpression value) {
		return this;
	}

	@Override
	public FlowCallBuilder outboundParameter(final String name, final String value) {
		return this;
	}

	@Override
	public FlowCallBuilder markAsStartNode() {
		return this;
	}
	
	/**
	 * @return
	 */
	public FlowCallNode build() {
		return flowCall;
	}
}
