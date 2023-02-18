package epf.workflow;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
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
	private final String id;
	
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
	private final List<Future<?>> subFlows = new CopyOnWriteArrayList<>();
	
	/**
	 * 
	 */
	private boolean terminate = false;
	
	/**
	 * 
	 */
	private final WorkflowData workflowData;
	
	/**
	 * @param workflowDefinition
	 * @param id
	 * @param workflowData
	 * @param events
	 */
	public WorkflowInstance(WorkflowDefinition workflowDefinition, String id, WorkflowData workflowData, Event[] events) {
		this.workflowDefinition = workflowDefinition;
		this.id = id;
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
	
	/**
	 * @param subFlow
	 */
	public void addSubFlow(final Future<?> subFlow) {
		subFlows.add(subFlow);
	}
	
	/**
	 * @return
	 */
	public Future<?>[] getSubFlows() {
		return subFlows.toArray(new Future<?>[0]);
	}

	public boolean isTerminate() {
		return terminate;
	}

	/**
	 * 
	 */
	public void terminate() {
		this.terminate = true;
	}

	public String getId() {
		return id;
	}
}
