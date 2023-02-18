package epf.workflow;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import epf.workflow.schema.State;
import epf.workflow.schema.WorkflowDefinition;

/**
 * @author PC
 *
 */
public class WorkflowInstance {

	/**
	 * 
	 */
	private final WorkflowDefinition workflowDefinition;
	
	/**
	 * 
	 */
	private final List<State> states = new CopyOnWriteArrayList<>();
	
	/**
	 * 
	 */
	private final List<Event> events = new CopyOnWriteArrayList<>();
	
	/**
	 * 
	 */
	private final WorkflowData workflowData;
	
	/**
	 * @param workflowDefinition
	 * @param workflowData
	 * @param events
	 */
	public WorkflowInstance(WorkflowDefinition workflowDefinition, WorkflowData workflowData, Event[] events) {
		this.workflowDefinition = workflowDefinition;
		this.workflowData = workflowData;
		Arrays.asList(events).forEach(this.events::add);
	}

	public WorkflowDefinition getWorkflowDefinition() {
		return workflowDefinition;
	}
	
	/**
	 * @return
	 */
	public State[] getStates() {
		return states.toArray(new State[0]);
	}
	
	/**
	 * @param state
	 */
	public void transition(final State state) {
		states.add(state);
	}
	
	/**
	 * @param state
	 */
	public void start(final State state) {
		
	}
	
	/**
	 * @param state
	 */
	public void end(final State state) {
		
	}
	
	/**
	 * @param state
	 */
	public void compensate(final State state) {
		
	}

	public WorkflowData getWorkflowData() {
		return workflowData;
	}
	
	/**
	 * @param event
	 */
	public void addEvent(final Event event) {
		events.add(event);
	}
	
	/**
	 * @return
	 */
	public Event[] getEvents() {
		return events.toArray(new Event[0]);
	}
}
