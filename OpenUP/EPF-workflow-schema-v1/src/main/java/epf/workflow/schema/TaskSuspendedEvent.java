package epf.workflow.schema;

import java.net.URI;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that the execution of a task has been suspended.")
public class TaskSuspendedEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow the task that has been suspended belongs to.")
	private String workflow;
	
	@NotNull
	@JsonPropertyDescription("A JSON Pointer that references the task that has been suspended.")
	private URI task;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the task has been suspended.")
	private Date suspendedAt;

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

	public Date getSuspendedAt() {
		return suspendedAt;
	}

	public void setSuspendedAt(Date suspendedAt) {
		this.suspendedAt = suspendedAt;
	}
}
