package epf.workflow.schema;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that the execution of a workflow has been suspended.")
public class WorkflowSuspendedEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow that has been suspended.")
	private String name;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the workflow has been suspended.")
	private Date suspendedAt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getSuspendedAt() {
		return suspendedAt;
	}

	public void setSuspendedAt(Date suspendedAt) {
		this.suspendedAt = suspendedAt;
	}
}
