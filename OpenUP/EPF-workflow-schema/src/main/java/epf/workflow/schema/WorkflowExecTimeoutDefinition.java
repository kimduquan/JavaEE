package epf.workflow.schema;

import jakarta.nosql.Column;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import jakarta.nosql.Embeddable;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

@Embeddable
public class WorkflowExecTimeoutDefinition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column
	@Description("Timeout duration (literal ISO 8601 duration format or expression which evaluation results in an ISO 8601 duration)")
	private String duration;

	@Column
	@Description("If false, workflow instance is allowed to finish current execution. If true, current workflow execution is stopped immediately.")
	@DefaultValue("false")
	private Boolean interrupt = false;
	
	@Column
	@Description("Name of a workflow state to be executed before workflow instance is terminated")
	private String runBefore;
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Boolean isInterrupt() {
		return interrupt;
	}
	public void setInterrupt(Boolean interrupt) {
		this.interrupt = interrupt;
	}
	public String getRunBefore() {
		return runBefore;
	}
	public void setRunBefore(String runBefore) {
		this.runBefore = runBefore;
	}
}
