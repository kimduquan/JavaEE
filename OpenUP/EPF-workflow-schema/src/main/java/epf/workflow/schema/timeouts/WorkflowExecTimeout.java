package epf.workflow.schema.timeouts;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;

/**
 * @author PC
 *
 */
@JsonbPropertyOrder({
    "duration",
    "interrupt",
    "runBefore"
})
public class WorkflowExecTimeout implements Serializable
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Workflow execution timeout duration (ISO 8601 duration format). If not specified should be 'unlimited'
     * (Required)
     * 
     */
    @JsonbProperty("duration")
    @Size(min = 1)
    @NotNull
    private String duration;
    /**
     * If `false`, workflow instance is allowed to finish current execution. If `true`, current workflow execution is abrupted.
     * 
     */
    @JsonbProperty("interrupt")
    private boolean interrupt = true;
    /**
     * Name of a workflow state to be executed before workflow instance is terminated
     * 
     */
    @JsonbProperty("runBefore")
    @Size(min = 1)
    private String runBefore;

    /**
     * Workflow execution timeout duration (ISO 8601 duration format). If not specified should be 'unlimited'
     * (Required)
     * 
     */
    @JsonbProperty("duration")
    public String getDuration() {
        return duration;
    }

    /**
     * Workflow execution timeout duration (ISO 8601 duration format). If not specified should be 'unlimited'
     * (Required)
     * 
     */
    @JsonbProperty("duration")
    public void setDuration(final String duration) {
        this.duration = duration;
    }

    /**
     * If `false`, workflow instance is allowed to finish current execution. If `true`, current workflow execution is abrupted.
     * 
     */
    @JsonbProperty("interrupt")
    public boolean isInterrupt() {
        return interrupt;
    }

    /**
     * If `false`, workflow instance is allowed to finish current execution. If `true`, current workflow execution is abrupted.
     * 
     */
    @JsonbProperty("interrupt")
    public void setInterrupt(final boolean interrupt) {
        this.interrupt = interrupt;
    }

    /**
     * Name of a workflow state to be executed before workflow instance is terminated
     * 
     */
    @JsonbProperty("runBefore")
    public String getRunBefore() {
        return runBefore;
    }

    /**
     * Name of a workflow state to be executed before workflow instance is terminated
     * 
     */
    @JsonbProperty("runBefore")
    public void setRunBefore(final String runBefore) {
        this.runBefore = runBefore;
    }
}
