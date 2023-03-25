package epf.webapp.workflow.flow;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.el.MethodExpression;
import javax.faces.application.NavigationCase;
import javax.faces.flow.Flow;
import javax.faces.flow.FlowCallNode;
import javax.faces.flow.FlowNode;
import javax.faces.flow.MethodCallNode;
import javax.faces.flow.Parameter;
import javax.faces.flow.ReturnNode;
import javax.faces.flow.SwitchNode;
import javax.faces.flow.ViewNode;
import javax.faces.lifecycle.ClientWindow;

/**
 * @author PC
 *
 */
public class Workflow extends Flow {
	
	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String definingDocumentId;
	/**
	 * 
	 */
	private String startNodeId;
	/**
	 * 
	 */
	private MethodExpression finalizer;
	/**
	 * 
	 */
	private MethodExpression initializer;
	/**
	 * 
	 */
	private Map<String, Parameter> inboundParameters;
	/**
	 * 
	 */
	private List<ViewNode> views;
	/**
	 * 
	 */
	private Map<String, ReturnNode> returns;
	/**
	 * 
	 */
	private Map<String, SwitchNode> switches;
	/**
	 * 
	 */
	private Map<String, FlowCallNode> flowCalls;
	/**
	 * 
	 */
	private List<MethodCallNode> methodCalls;
	/**
	 * 
	 */
	private Map<String, Set<NavigationCase>> navigationCases;
	/**
	 * 
	 */
	private Map<String, FlowNode> nodes;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getDefiningDocumentId() {
		return definingDocumentId;
	}

	@Override
	public String getStartNodeId() {
		return startNodeId;
	}

	@Override
	public MethodExpression getFinalizer() {
		return finalizer;
	}

	@Override
	public MethodExpression getInitializer() {
		return initializer;
	}

	@Override
	public Map<String, Parameter> getInboundParameters() {
		return inboundParameters;
	}

	@Override
	public List<ViewNode> getViews() {
		return views;
	}

	@Override
	public Map<String, ReturnNode> getReturns() {
		return returns;
	}

	@Override
	public Map<String, SwitchNode> getSwitches() {
		return switches;
	}

	@Override
	public Map<String, FlowCallNode> getFlowCalls() {
		return flowCalls;
	}

	@Override
	public FlowCallNode getFlowCall(final Flow targetFlow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MethodCallNode> getMethodCalls() {
		return methodCalls;
	}

	@Override
	public FlowNode getNode(final String nodeId) {
		return nodes.get(nodeId);
	}
	
	/**
	 * @param nodeId
	 * @param node
	 */
	public void setNode(final String nodeId, final FlowNode node) {
		nodes.put(nodeId, node);
	}

	@Override
	public Map<String, Set<NavigationCase>> getNavigationCases() {
		return navigationCases;
	}

	@Override
	public String getClientWindowFlowId(final ClientWindow curWindow) {
		return null;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public void setDefiningDocumentId(final String definingDocumentId) {
		this.definingDocumentId = definingDocumentId;
	}

	public void setStartNodeId(final String startNodeId) {
		this.startNodeId = startNodeId;
	}

	public void setFinalizer(final MethodExpression finalizer) {
		this.finalizer = finalizer;
	}

	public void setInitializer(final MethodExpression initializer) {
		this.initializer = initializer;
	}

	public void setInboundParameters(final Map<String, Parameter> inboundParameters) {
		this.inboundParameters = inboundParameters;
	}

	public void setViews(final List<ViewNode> views) {
		this.views = views;
	}

	public void setReturns(final Map<String, ReturnNode> returns) {
		this.returns = returns;
	}

	public void setSwitches(final Map<String, SwitchNode> switches) {
		this.switches = switches;
	}

	public void setFlowCalls(final Map<String, FlowCallNode> flowCalls) {
		this.flowCalls = flowCalls;
	}

	public void setMethodCalls(final List<MethodCallNode> methodCalls) {
		this.methodCalls = methodCalls;
	}

	public void setNavigationCases(final Map<String, Set<NavigationCase>> navigationCases) {
		this.navigationCases = navigationCases;
	}
}
