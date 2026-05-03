package epf.workflow.schema;

import java.net.URI;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that the status phase of a task has changed.")
public class TaskStatusChangedEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow the task which's status phase has changed belongs to.")
	private String workflow;
	
	@NotNull
	@JsonPropertyDescription("A JSON Pointer that references the task which's status phase has changed.")
	private URI task;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the task's status phase has changed.")
	private Date updatedAt;
	
	@NotNull
	@JsonPropertyDescription("The task's current status phase.")
	private String status;

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

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
