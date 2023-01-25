package epf.workflow.schema.end;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import epf.workflow.schema.timeouts.WorkflowExecTimeout;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "workflowId",
    "version",
    "data",
    "workflowExecTimeout"
})
public class ContinueAs implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Unique id of the workflow to continue execution as
     * 
     */
    @JsonbProperty("workflowId")
    private String workflowId;
    /**
     * Version of the workflow to continue execution as
     * 
     */
    @JsonbProperty("version")
    @Size(min = 1)
    private String version;
    /**
     * Expression which selects parts of the states data output to become the workflow data input of continued execution
     * 
     */
    @JsonbProperty("data")
    private String data;
    @JsonbProperty("workflowExecTimeout")
    @Valid
    private WorkflowExecTimeout workflowExecTimeout;

    /**
     * Unique id of the workflow to continue execution as
     * 
     */
    @JsonbProperty("workflowId")
    public String getWorkflowId() {
        return workflowId;
    }

    /**
     * Unique id of the workflow to continue execution as
     * 
     */
    @JsonbProperty("workflowId")
    public void setWorkflowId(final String workflowId) {
        this.workflowId = workflowId;
    }

    /**
     * Version of the workflow to continue execution as
     * 
     */
    @JsonbProperty("version")
    public String getVersion() {
        return version;
    }

    /**
     * Version of the workflow to continue execution as
     * 
     */
    @JsonbProperty("version")
    public void setVersion(final String version) {
        this.version = version;
    }

    /**
     * Expression which selects parts of the states data output to become the workflow data input of continued execution
     * 
     */
    @JsonbProperty("data")
    public String getData() {
        return data;
    }

    /**
     * Expression which selects parts of the states data output to become the workflow data input of continued execution
     * 
     */
    @JsonbProperty("data")
    public void setData(final String data) {
        this.data = data;
    }

    @JsonbProperty("workflowExecTimeout")
    public WorkflowExecTimeout getWorkflowExecTimeout() {
        return workflowExecTimeout;
    }

    @JsonbProperty("workflowExecTimeout")
    public void setWorkflowExecTimeout(final WorkflowExecTimeout workflowExecTimeout) {
        this.workflowExecTimeout = workflowExecTimeout;
    }
}
