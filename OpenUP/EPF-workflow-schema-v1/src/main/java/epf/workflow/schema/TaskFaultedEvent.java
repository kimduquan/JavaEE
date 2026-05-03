package epf.workflow.schema;

import java.net.URI;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that a task has been faulted.")
public class TaskFaultedEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow the task that has faulted belongs to.")
	private String workflow;
	
	@NotNull
	@JsonPropertyDescription("A JSON Pointer that references the task that has faulted.")
	private URI task;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the task has faulted.")
	private Date faultedAt;
	
	@NotNull
	@JsonPropertyDescription("The error that has cause the task to fault.")
	private Error error;

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

	public Date getFaultedAt() {
		return faultedAt;
	}

	public void setFaultedAt(Date faultedAt) {
		this.faultedAt = faultedAt;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
}
