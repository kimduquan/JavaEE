package epf.workflow.schema;

import java.net.URI;
import java.util.Date;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that a task ran to completion.")
public class TaskCompletedEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow the task that ran to completion belongs to.")
	private String workflow;
	
	@NotNull
	@JsonPropertyDescription("A JSON Pointer that references the task that ran to completion.")
	private URI task;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the task ran to completion.")
	private Date completedAt;
	
	@JsonPropertyDescription("The task's output, if any.")
	private Map<?, ?> output;

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public URI getTask() {
		return task;
	}

	public void setTask(URI task) {
		this.task = task;
	}

	public Date getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
	}

	public Map<?, ?> getOutput() {
		return output;
	}

	public void setOutput(Map<?, ?> output) {
		this.output = output;
	}
}
