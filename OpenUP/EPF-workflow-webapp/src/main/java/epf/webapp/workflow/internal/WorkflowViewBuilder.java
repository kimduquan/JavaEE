package epf.webapp.workflow.internal;

import javax.faces.flow.builder.ViewBuilder;

/**
 * @author PC
 *
 */
public class WorkflowViewBuilder extends ViewBuilder {

	@Override
	public ViewBuilder markAsStartNode() {
		return this;
	}
}
