package epf.workflow.schema;

import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.function.Invoke;
import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class SubFlowRefDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull
	@Column
	@Description("Sub-workflow unique name")
	private String workflowId;
	
	@Column
	@Description("Sub-workflow version")
	private String version;
	
	@Column
	@Description("Specifies if the subflow should be invoked sync or async.")
	@DefaultValue("sync")
	private Invoke invoke = Invoke.sync;
	
	@Column
	@Description("If invoke is async, specifies if subflow execution should terminate or continue when parent workflow completes.")
	@DefaultValue("terminate")
	private OnParentComplete onParentComplete = OnParentComplete.terminate;

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Invoke getInvoke() {
		return invoke;
	}

	public void setInvoke(Invoke invoke) {
		this.invoke = invoke;
	}

	public OnParentComplete getOnParentComplete() {
		return onParentComplete;
	}

	public void setOnParentComplete(OnParentComplete onParentComplete) {
		this.onParentComplete = onParentComplete;
	}
}
