package epf.webapp.workflow.flow;

import javax.el.ValueExpression;
import javax.faces.flow.Parameter;

/**
 * @author PC
 *
 */
public class WorkflowParameter extends Parameter {
	
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private ValueExpression value;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ValueExpression getValue() {
		return value;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setValue(final ValueExpression value) {
		this.value = value;
	}
}
