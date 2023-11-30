package epf.workflow;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

import epf.workflow.schema.state.State;
import jakarta.nosql.Column;
import jakarta.nosql.Entity;
import jakarta.nosql.Id;

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
	private List<String> states;
	
	/**
	 * 
	 */
	@Column
	private WorkflowData workflowData;
	
	/**
	 * 
	 */
	private URI uri;
	
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
	public void start(final State state) {
		states = new ArrayList<>();
		states.add(state.getName());
	}
	
	/**
	 * @param state
	 */
	public void transition(final State state) {
		states.add(state.getName());
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

	public List<String> getStates() {
		return states;
	}

	public void setStates(List<String> states) {
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

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}
}
