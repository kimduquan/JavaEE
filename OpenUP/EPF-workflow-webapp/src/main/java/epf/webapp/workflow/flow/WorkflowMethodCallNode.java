package epf.webapp.workflow.flow;

import java.util.List;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.flow.MethodCallNode;
import javax.faces.flow.Parameter;

/**
 * @author PC
 *
 */
public class WorkflowMethodCallNode extends MethodCallNode {
	
	/**
	 * 
	 */
	private MethodExpression methodExpression;
	
	/**
	 * 
	 */
	private ValueExpression outcome;
	
	/**
	 * 
	 */
	private List<Parameter> parameters;
	
	/**
	 * 
	 */
	private String id;

	@Override
	public MethodExpression getMethodExpression() {
		return methodExpression;
	}

	@Override
	public ValueExpression getOutcome() {
		return outcome;
	}

	@Override
	public List<Parameter> getParameters() {
		return parameters;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setMethodExpression(final MethodExpression methodExpression) {
		this.methodExpression = methodExpression;
	}

	public void setOutcome(final ValueExpression outcome) {
		this.outcome = outcome;
	}

	public void setParameters(final List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public void setId(final String id) {
		this.id = id;
	}

}
