package epf.workflow.schema;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class ContinueAs {

	/**
	 * 
	 */
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
	private Object data;
	
	/**
	 * 
	 */
	@Column
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
