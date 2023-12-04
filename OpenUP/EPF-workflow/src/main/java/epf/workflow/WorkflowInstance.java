package epf.workflow;

import java.io.Serializable;

import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

/**
 * @author PC
 *
 */
@Entity
public class WorkflowInstance implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Id
	private String id;
	
	/**
	 * 
	 */
	@Column
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
