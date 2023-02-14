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
	private boolean end;
	
	/**
	 * 
	 */
	private final WorkflowData workflowData;
	
	/**
	 * @param workflowDefinition
	 * @param eventState
	 * @param workflowData
	 */
	public OnEvents(WorkflowDefinition workflowDefinition, EventState eventState, WorkflowData workflowData) {
		this.workflowDefinition = workflowDefinition;
		this.eventState = eventState;
		this.workflowData = workflowData;
	}

	public WorkflowDefinition getWorkflowDefinition() {
		return workflowDefinition;
	}

	public EventState getEventState() {
		return eventState;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public WorkflowData getWorkflowData() {
		return workflowData;
	}
}
