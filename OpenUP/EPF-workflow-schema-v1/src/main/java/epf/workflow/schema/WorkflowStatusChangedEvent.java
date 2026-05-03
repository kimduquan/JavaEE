package epf.workflow.schema;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that the status phase of a workflow has changed.")
public class WorkflowStatusChangedEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow which's status phase has changed.")
	private String name;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the workflow's status phase has changed.")
	private Date updatedAt;
	
	@JsonPropertyDescription("The workflow's current status phase.")
	private String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
