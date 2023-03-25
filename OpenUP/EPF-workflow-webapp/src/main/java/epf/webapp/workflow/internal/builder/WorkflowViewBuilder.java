package epf.webapp.workflow.internal.builder;

import javax.faces.flow.ViewNode;
import javax.faces.flow.builder.ViewBuilder;
import epf.webapp.workflow.internal.WorkflowViewNode;

/**
 * @author PC
 *
 */
public class WorkflowViewBuilder extends ViewBuilder {
	
	/**
	 * 
	 */
	private WorkflowViewNode view;

	@Override
	public ViewBuilder markAsStartNode() {
		return this;
	}
	
	/**
	 * @return
	 */
	public ViewNode build() {
		return view;
	}
}
