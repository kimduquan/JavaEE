package epf.workflow.schema.function;

import jakarta.validation.constraints.NotNull;
import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class FunctionRefDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column
	@Description("Name of the referenced function. Must follow the Serverless Workflow Naming Convention")
	private String refName;
	
	@Column
	@Description("Arguments (inputs) to be passed to the referenced function")
	private Object arguments;
	
	@Column
	@Description("Used if function type is graphql. String containing a valid GraphQL selection set")
	private String selectionSet;
	
	@Column
	@Description("Specifies if the function should be invoked sync or async.")
	@DefaultValue("sync")
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
