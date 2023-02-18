package epf.workflow;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
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
	private final WorkflowData workflowData;
	
	/**
	 * 
	 */
	private final WorkflowInstance workflowInstance;
	
	/**
	 * 
	 */
	private final List<Event> events = new CopyOnWriteArrayList<>();
	
	/**
	 * @param workflowDefinition
	 * @param eventState
	 * @param workflowData
	 */
	public OnEvents(WorkflowDefinition workflowDefinition, EventState eventState, WorkflowData workflowData) {
		this.workflowDefinition = workflowDefinition;
		this.eventState = eventState;
		this.workflowData = workflowData;
		workflowInstance = null;
	}
	
	/**
	 * @param eventState
	 * @param workflowInstance
	 */
	public OnEvents(EventState eventState, final WorkflowInstance workflowInstance) {
		this.workflowDefinition = workflowInstance.getWorkflowDefinition();
		this.eventState = eventState;
		this.workflowData = workflowInstance.getWorkflowData();
		this.workflowInstance = workflowInstance;
	}

	public WorkflowDefinition getWorkflowDefinition() {
		return workflowDefinition;
	}

	public EventState getEventState() {
		return eventState;
	}
	
	public Event[] getEvents() {
		return events.toArray(new Event[0]);
	}
	
	public void addEvent(final Event event) {
		events.add(event);
	}

	public WorkflowData getWorkflowData() {
		return workflowData;
	}

	public WorkflowInstance getWorkflowInstance() {
		return workflowInstance;
	}
}
