package epf.workflow.schema;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that a workflow has been cancelled.")
public class WorkflowCancelledEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow that has been cancelled.")
	private String name;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the workflow has been cancelled.")
	private Date cancelledAt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCancelledAt() {
		return cancelledAt;
	}

	public void setCancelledAt(Date cancelledAt) {
		this.cancelledAt = cancelledAt;
	}
}
