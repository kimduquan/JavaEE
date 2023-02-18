package epf.workflow.schema;

/**
 * @author PC
 *
 */
public class ContinueAs {

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
	private Object data;
	
	/**
	 * 
	 */
	private Object workflowExecTimeout;

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getWorkflowExecTimeout() {
		return workflowExecTimeout;
	}

	public void setWorkflowExecTimeout(Object workflowExecTimeout) {
		this.workflowExecTimeout = workflowExecTimeout;
	}
}
