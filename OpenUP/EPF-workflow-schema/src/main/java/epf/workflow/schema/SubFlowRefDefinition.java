package epf.workflow.schema;

import jakarta.validation.constraints.NotNull;
import epf.workflow.schema.function.Invoke;
import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class SubFlowRefDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@NotNull
	@Column
	private String workflowId;
	
	/**
	 * 
	 */
	@Column
	private String version;
	
	/**
	 * 
	 */
	@Column
	private Invoke invoke = Invoke.sync;
	
	/**
	 * 
	 */
	@Column
	private OnParentComplete onParentComplete = OnParentComplete.Terminate;

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
