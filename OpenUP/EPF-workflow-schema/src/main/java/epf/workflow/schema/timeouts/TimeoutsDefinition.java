package epf.workflow.schema.timeouts;

import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "workflowExecTimeout",
    "stateExecTimeout",
    "actionExecTimeout",
    "branchExecTimeout",
    "eventTimeout"
})
public class TimeoutsDefinition implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 
     */
    @JsonbProperty("workflowExecTimeout")
    @Valid
    private WorkflowExecTimeout workflowExecTimeout;
    /**
     * 
     */
    @JsonbProperty("stateExecTimeout")
    @Valid
    private StateExecTimeout stateExecTimeout;
    /**
     * Single actions definition execution timeout duration (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("actionExecTimeout")
    @Size(min = 1)
    private String actionExecTimeout;
    /**
     * Single branch execution timeout duration (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("branchExecTimeout")
    @Size(min = 1)
    private String branchExecTimeout;
    /**
     * Timeout duration to wait for consuming defined events (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("eventTimeout")
    @Size(min = 1)
    private String eventTimeout;

    @JsonbProperty("workflowExecTimeout")
    public WorkflowExecTimeout getWorkflowExecTimeout() {
        return workflowExecTimeout;
    }

    @JsonbProperty("workflowExecTimeout")
    public void setWorkflowExecTimeout(final WorkflowExecTimeout workflowExecTimeout) {
        this.workflowExecTimeout = workflowExecTimeout;
    }

    @JsonbProperty("stateExecTimeout")
    public StateExecTimeout getStateExecTimeout() {
        return stateExecTimeout;
    }

    @JsonbProperty("stateExecTimeout")
    public void setStateExecTimeout(final StateExecTimeout stateExecTimeout) {
        this.stateExecTimeout = stateExecTimeout;
    }

    /**
     * Single actions definition execution timeout duration (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("actionExecTimeout")
    public String getActionExecTimeout() {
        return actionExecTimeout;
    }

    /**
     * Single actions definition execution timeout duration (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("actionExecTimeout")
    public void setActionExecTimeout(final String actionExecTimeout) {
        this.actionExecTimeout = actionExecTimeout;
    }

    /**
     * Single branch execution timeout duration (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("branchExecTimeout")
    public String getBranchExecTimeout() {
        return branchExecTimeout;
    }

    /**
     * Single branch execution timeout duration (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("branchExecTimeout")
    public void setBranchExecTimeout(final String branchExecTimeout) {
        this.branchExecTimeout = branchExecTimeout;
    }

    /**
     * Timeout duration to wait for consuming defined events (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("eventTimeout")
    public String getEventTimeout() {
        return eventTimeout;
    }

    /**
     * Timeout duration to wait for consuming defined events (ISO 8601 duration format)
     * 
     */
    @JsonbProperty("eventTimeout")
    public void setEventTimeout(final String eventTimeout) {
        this.eventTimeout = eventTimeout;
    }
}
