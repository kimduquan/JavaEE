package epf.webapp.workflow.internal;

import javax.el.ValueExpression;
import javax.faces.flow.builder.FlowCallBuilder;

/**
 * @author PC
 *
 */
public class WorkflowCallBuilder extends FlowCallBuilder {

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
}
