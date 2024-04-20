package epf.workflow.instance;

import java.io.Serializable;
import epf.workflow.states.WorkflowState;

/**
 * @author PC
 *
 */
public class Instance implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	
	/**
	 * 
	 */
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
