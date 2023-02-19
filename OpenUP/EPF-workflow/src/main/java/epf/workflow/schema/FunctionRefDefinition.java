package epf.workflow.schema;

import javax.validation.constraints.NotNull;

/**
 * @author PC
 *
 */
public class FunctionRefDefinition {

	/**
	 * 
	 */
	@NotNull
	private String refName;
	
	/**
	 * 
	 */
	private Object arguments;
	
	/**
	 * 
	 */
	private String selectionSet;
	
	/**
	 * 
	 */
	private Invoke invoke;

	public String getRefName() {
		return refName;
	}

	public void setRefName(String refName) {
		this.refName = refName;
	}

	public Object getArguments() {
		return arguments;
	}

	public void setArguments(Object arguments) {
		this.arguments = arguments;
	}

	public String getSelectionSet() {
		return selectionSet;
	}

	public void setSelectionSet(String selectionSet) {
		this.selectionSet = selectionSet;
	}

	public Invoke getInvoke() {
		return invoke;
	}

	public void setInvoke(Invoke invoke) {
		this.invoke = invoke;
	}
}
