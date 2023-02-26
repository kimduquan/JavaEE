package epf.workflow.function.schema;

import javax.validation.constraints.NotNull;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class FunctionRefDefinition {

	/**
	 * 
	 */
	@NotNull
	@Column
	private String refName;
	
	/**
	 * 
	 */
	@Column
	private Object arguments;
	
	/**
	 * 
	 */
	@Column
	private String selectionSet;
	
	/**
	 * 
	 */
	@Column
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
