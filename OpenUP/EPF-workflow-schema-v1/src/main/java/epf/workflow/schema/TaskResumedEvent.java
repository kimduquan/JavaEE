package epf.workflow.schema;

import java.net.URI;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that notifies that a task has been resumed.")
public class TaskResumedEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow the task that has been resumed belongs to.")
	private String workflow;
	
	@NotNull
	@JsonPropertyDescription("A JSON Pointer that references the task that has been resumed.")
	private URI task;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the task has been resumed.")
	private Date resumedAt;

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

	public Date getResumedAt() {
		return resumedAt;
	}

	public void setResumedAt(Date resumedAt) {
		this.resumedAt = resumedAt;
	}
}
