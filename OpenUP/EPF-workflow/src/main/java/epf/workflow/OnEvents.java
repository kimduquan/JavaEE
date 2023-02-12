package epf.workflow;

import epf.workflow.schema.EventState;
import epf.workflow.schema.WorkflowDefinition;

/**
 * @author PC
 *
 */
public class OnEvents {

	/**
	 * 
	 */
	private final WorkflowDefinition workflowDefinition;
	
	/**
	 * 
	 */
	private final EventState eventState;
	
	/**
	 * 
	 */
	private Instance workflowInstance;
	
	/**
	 * 
	 */
	private boolean end;
	
	/**
	 * @param workflowDefinition
	 * @param eventState
	 */
	public OnEvents(WorkflowDefinition workflowDefinition, EventState eventState) {
		this.workflowDefinition = workflowDefinition;
		this.eventState = eventState;
	}

	public WorkflowDefinition getWorkflowDefinition() {
		return workflowDefinition;
	}

	public EventState getEventState() {
		return eventState;
	}

	public Instance getWorkflowInstance() {
		return workflowInstance;
	}

	public void setWorkflowInstance(Instance workflowInstance) {
		this.workflowInstance = workflowInstance;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}
}
