package epf.workflow;

import java.time.Instant;
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
	private final Instant time;
	
	/**
	 * 
	 */
	private final WorkflowData workflowData;
	
	/**
	 * 
	 */
	private final List<Event> events = new CopyOnWriteArrayList<>();
	
	/**
	 * @param workflowDefinition
	 * @param eventState
	 * @param time
	 * @param workflowData
	 */
	public OnEvents(WorkflowDefinition workflowDefinition, EventState eventState, Instant time, WorkflowData workflowData) {
		this.workflowDefinition = workflowDefinition;
		this.eventState = eventState;
		this.time = time;
		this.workflowData = workflowData;
	}

	public WorkflowDefinition getWorkflowDefinition() {
		return workflowDefinition;
	}

	public EventState getEventState() {
		return eventState;
	}

	public WorkflowData getWorkflowData() {
		return workflowData;
	}

	public Instant getTime() {
		return time;
	}
	
	public Event[] getEvents() {
		return events.toArray(new Event[0]);
	}
	
	public void addEvent(final Event event) {
		events.add(event);
	}
}
