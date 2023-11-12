package epf.workflow.schema;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import epf.workflow.schema.adapter.WorkflowExecTimeoutDefinitionAdapter;
import epf.workflow.schema.util.StringOrObject;
import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class WorkflowTimeoutDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = WorkflowExecTimeoutDefinitionAdapter.class)
	private StringOrObject<WorkflowExecTimeoutDefinition> workflowExecTimeout;
	
	/**
	 * 
	 */
	@Column
	private String stateExecTimeout;
	
	/**
	 * 
	 */
	@Column
	private String actionExecTimeout;
	
	/**
	 * 
	 */
	@Column
	private String branchExecTimeout;
	
	/**
	 * 
	 */
	@Column
	private String eventTimeout;

	public StringOrObject<WorkflowExecTimeoutDefinition> getWorkflowExecTimeout() {
		return workflowExecTimeout;
	}

	public void setWorkflowExecTimeout(StringOrObject<WorkflowExecTimeoutDefinition> workflowExecTimeout) {
		this.workflowExecTimeout = workflowExecTimeout;
	}

	public String getStateExecTimeout() {
		return stateExecTimeout;
	}

	public void setStateExecTimeout(String stateExecTimeout) {
		this.stateExecTimeout = stateExecTimeout;
	}

	public String getActionExecTimeout() {
		return actionExecTimeout;
	}

	public void setActionExecTimeout(String actionExecTimeout) {
		this.actionExecTimeout = actionExecTimeout;
	}

	public String getBranchExecTimeout() {
		return branchExecTimeout;
	}

	public void setBranchExecTimeout(String branchExecTimeout) {
		this.branchExecTimeout = branchExecTimeout;
	}

	public String getEventTimeout() {
		return eventTimeout;
	}

	public void setEventTimeout(String eventTimeout) {
		this.eventTimeout = eventTimeout;
	}
}
