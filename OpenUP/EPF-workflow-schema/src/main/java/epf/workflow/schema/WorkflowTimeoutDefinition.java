package epf.workflow.schema;

import jakarta.json.bind.annotation.JsonbTypeAdapter;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.adapter.StringOrWorkflowExecTimeoutDefinitionAdapter;
import jakarta.nosql.Column;
import java.io.Serializable;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class WorkflowTimeoutDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	@Description("Workflow execution timeout (literal ISO 8601 duration format or expression which evaluation results in an ISO 8601 duration)")
	@JsonbTypeAdapter(value = StringOrWorkflowExecTimeoutDefinitionAdapter.class)
	private StringOrObject<WorkflowExecTimeoutDefinition> workflowExecTimeout;
	
	@Column
	@Description("Workflow state execution timeout (literal ISO 8601 duration format or expression which evaluation results in an ISO 8601 duration)")
	private String stateExecTimeout;
	
	@Column
	@Description("Actions execution timeout (literal ISO 8601 duration format or expression which evaluation results in an ISO 8601 duration)")
	private String actionExecTimeout;
	
	@Column
	@Description("Branch execution timeout (literal ISO 8601 duration format or expression which evaluation results in an ISO 8601 duration)")
	private String branchExecTimeout;
	
	@Column
	@Description("Default timeout for consuming defined events (literal ISO 8601 duration format or expression which evaluation results in an ISO 8601 duration)")
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
