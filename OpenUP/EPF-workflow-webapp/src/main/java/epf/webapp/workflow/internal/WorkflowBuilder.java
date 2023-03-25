package epf.webapp.workflow.internal;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.flow.Flow;
import javax.faces.flow.builder.FlowBuilder;
import javax.faces.flow.builder.FlowCallBuilder;
import javax.faces.flow.builder.MethodCallBuilder;
import javax.faces.flow.builder.NavigationCaseBuilder;
import javax.faces.flow.builder.ReturnBuilder;
import javax.faces.flow.builder.SwitchBuilder;
import javax.faces.flow.builder.ViewBuilder;

/**
 * @author PC
 *
 */
public class WorkflowBuilder extends FlowBuilder {
	
	/**
	 * 
	 */
	private WorkflowViewBuilder viewNode;
	
	/**
	 * 
	 */
	private WorkflowNavigationCaseBuilder navigationCase;
	
	/**
	 * 
	 */
	private WorkflowSwitchBuilder switchNode;
	
	/**
	 * 
	 */
	private WorkflowReturnBuilder returnNode;
	
	/**
	 * 
	 */
	private WorkflowMethodCallBuilder methodCallNode;
	
	/**
	 * 
	 */
	private WorkflowCallBuilder flowCallNode;
	
	/**
	 * 
	 */
	private Workflow flow;
	
	@Override
	public FlowBuilder id(final String definingDocumentId, final String id) {
		return this;
	}

	@Override
	public ViewBuilder viewNode(final String viewNodeId, final String vdlDocumentId) {
		return viewNode;
	}

	@Override
	public NavigationCaseBuilder navigationCase() {
		return navigationCase;
	}

	@Override
	public SwitchBuilder switchNode(final String switchNodeId) {
		return switchNode;
	}

	@Override
	public ReturnBuilder returnNode(final String returnNodeId) {
		return returnNode;
	}

	@Override
	public MethodCallBuilder methodCallNode(final String methodCallNodeId) {
		return methodCallNode;
	}

	@Override
	public FlowCallBuilder flowCallNode(final String flowCallNodeId) {
		return flowCallNode;
	}

	@Override
	public FlowBuilder initializer(final MethodExpression methodExpression) {
		return this;
	}

	@Override
	public FlowBuilder initializer(final String methodExpression) {
		return this;
	}

	@Override
	public FlowBuilder finalizer(final MethodExpression methodExpression) {
		return this;
	}

	@Override
	public FlowBuilder finalizer(final String methodExpression) {
		return this;
	}

	@Override
	public FlowBuilder inboundParameter(final String name, final ValueExpression expression) {
		return this;
	}

	@Override
	public FlowBuilder inboundParameter(final String name, final String expression) {
		return this;
	}

	@Override
	public Flow getFlow() {
		return flow;
	}

}
