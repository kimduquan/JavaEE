package epf.workflow.schema;

import jakarta.json.JsonValue;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import epf.nosql.schema.StringOrObject;
import epf.workflow.schema.adapter.StringOrWorkflowExecTimeoutDefinitionAdapter;
import jakarta.nosql.Column;
import java.io.Serializable;
import org.eclipse.jnosql.mapping.Embeddable;

/**
 * @author PC
 *
 */
@Embeddable
public class ContinueAs implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@Column
	private String workflowId;
	
	/**
	 * 
	 */
	@Column
	private String version;
	
	/**
	 * 
	 */
	@Column
	private StringOrObject<JsonValue> data;
	
	/**
	 * 
	 */
	@Column
	@JsonbTypeAdapter(value = StringOrWorkflowExecTimeoutDefinitionAdapter.class)
	private StringOrObject<WorkflowExecTimeoutDefinition> workflowExecTimeout;

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

	public StringOrObject<WorkflowExecTimeoutDefinition> getWorkflowExecTimeout() {
		return workflowExecTimeout;
	}

	public void setWorkflowExecTimeout(StringOrObject<WorkflowExecTimeoutDefinition> workflowExecTimeout) {
		this.workflowExecTimeout = workflowExecTimeout;
	}
}
