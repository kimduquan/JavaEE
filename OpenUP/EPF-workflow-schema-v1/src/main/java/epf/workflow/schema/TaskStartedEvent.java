package epf.workflow.schema;

import java.net.URI;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that a task has started.")
public class TaskStartedEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow the task that has started belongs to.")
	private String workflow;
	
	@NotNull
	@JsonPropertyDescription("A JSON Pointer that references the task that has started.")
	private URI task;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the task has started.")
	private Date startedAt;

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

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}
}
