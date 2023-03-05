package epf.workflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import epf.workflow.state.schema.State;
import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

/**
 * @author PC
 *
 */
@Entity
public class WorkflowInstance {
	
	/**
	 * 
	 */
	@Column
	private String workflowDefinition;
	
	/**
	 * 
	 */
	@Id
	private String id;
	
	/**
	 * 
	 */
	@Column
	private String[] states;
	
	/**
	 * 
	 */
	@Column
	private WorkflowData workflowData;
	
	/**
	 * 
	 */
	private final List<Future<?>> subFlows = new CopyOnWriteArrayList<>();
	
	/**
	 * 
	 */
	private boolean terminate = false;
	
	/**
	 * @param state
	 */
	public void transition(final State state) {
		final List<String> newStates = new ArrayList<>(Arrays.asList(states));
		states = newStates.toArray(new String[0]);
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

	public void setId(String id) {
		this.id = id;
	}

	public String[] getStates() {
		return states;
	}

	public void setStates(String[] states) {
		this.states = states;
	}

	public WorkflowData getWorkflowData() {
		return workflowData;
	}

	public void setWorkflowData(WorkflowData workflowData) {
		this.workflowData = workflowData;
	}

	public String getWorkflowDefinition() {
		return workflowDefinition;
	}

	public void setWorkflowDefinition(String workflowDefinition) {
		this.workflowDefinition = workflowDefinition;
	}
}
