package epf.workflow.schema;

import java.net.URI;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that notifies that a task is being retried.")
public class TaskRetriedEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow the task that is being retried belongs to.")
	private String workflow;
	
	@NotNull
	@JsonPropertyDescription("A JSON Pointer that references the task that is being retried.")
	private URI task;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the task has been retried.")
	private Date retriedAt;

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

	public Date getRetriedAt() {
		return retriedAt;
	}

	public void setRetriedAt(Date retriedAt) {
		this.retriedAt = retriedAt;
	}
}
