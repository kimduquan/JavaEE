package epf.workflow.schema;

import java.io.Serializable;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.Description;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.adapter.StringOrWorkflowTimeoutDefinitionAdapter;
import jakarta.json.JsonValue;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import jakarta.nosql.Column;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class ContinueAs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	@Description("Unique name of the workflow to continue execution as.")
	private String workflowId;
	
	@Column
	@Description("Version of the workflow to continue execution as.")
	private String version;
	
	@Column
	@Description("If string type, a workflow expression which selects parts of the states data output to become the workflow data input of continued execution. If object type, a custom object to become the workflow data input of the continued execution.")
	private StringOrObject<JsonValue> data;
	
	@Column
	@Description("Workflow execution timeout to be used by the workflow continuing execution. Overwrites any specific settings set by that workflow.")
	@JsonbTypeAdapter(value = StringOrWorkflowTimeoutDefinitionAdapter.class)
	private StringOrObject<WorkflowTimeoutDefinition> workflowExecTimeout;

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public StringOrObject<JsonValue> getData() {
		return data;
	}

	public void setData(StringOrObject<JsonValue> data) {
		this.data = data;
	}

	public StringOrObject<WorkflowTimeoutDefinition> getWorkflowExecTimeout() {
		return workflowExecTimeout;
	}

	public void setWorkflowExecTimeout(StringOrObject<WorkflowTimeoutDefinition> workflowExecTimeout) {
		this.workflowExecTimeout = workflowExecTimeout;
	}
}
