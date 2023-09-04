package epf.workflow.schema.function;

import javax.validation.constraints.NotNull;
import jakarta.nosql.Column;
import org.eclipse.jnosql.mapping.Embeddable;

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
	private Invoke invoke = Invoke.sync;

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
