package epf.workflow.schema.end;

import java.io.Serializable;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.validation.Valid;
import javax.validation.constraints.Size;

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
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public ContinueAs withWorkflowId(String workflowId) {
        this.workflowId = workflowId;
        return this;
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
    public void setVersion(String version) {
        this.version = version;
    }

    public ContinueAs withVersion(String version) {
        this.version = version;
        return this;
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
    public void setData(String data) {
        this.data = data;
    }

    public ContinueAs withData(String data) {
        this.data = data;
        return this;
    }

    @JsonbProperty("workflowExecTimeout")
    public WorkflowExecTimeout getWorkflowExecTimeout() {
        return workflowExecTimeout;
    }

    @JsonbProperty("workflowExecTimeout")
    public void setWorkflowExecTimeout(WorkflowExecTimeout workflowExecTimeout) {
        this.workflowExecTimeout = workflowExecTimeout;
    }

    public ContinueAs withWorkflowExecTimeout(WorkflowExecTimeout workflowExecTimeout) {
        this.workflowExecTimeout = workflowExecTimeout;
        return this;
    }

}
