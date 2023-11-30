package epf.workflow;

import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * 
 */
@Embeddable
public class WorkflowState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String name;
	
	/**
	 * 
	 */
	private WorkflowState previousState;
	
	/**
	 * 
	 */
	private WorkflowData workflowData;

	public WorkflowState getPreviousState() {
		return previousState;
	}

	public void setPreviousState(final WorkflowState previousState) {
		this.previousState = previousState;
	}

	public WorkflowData getWorkflowData() {
		return workflowData;
	}

	public void setWorkflowData(final WorkflowData workflowData) {
		this.workflowData = workflowData;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
