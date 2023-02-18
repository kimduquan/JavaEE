package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class SubFlowRefDefinition {

	/**
	 * 
	 */
	private String workflowId;
	
	/**
	 * 
	 */
	private String version;
	
	/**
	 * 
	 */
	private Invoke invoke = Invoke.sync;
	
	/**
	 * 
	 */
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
