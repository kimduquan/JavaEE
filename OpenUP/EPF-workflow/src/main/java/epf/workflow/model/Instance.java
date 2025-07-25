package epf.workflow.model;

import java.io.Serializable;

public class Instance implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private WorkflowState state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public WorkflowState getState() {
		return state;
	}

	public void setState(WorkflowState state) {
		this.state = state;
	}
}
