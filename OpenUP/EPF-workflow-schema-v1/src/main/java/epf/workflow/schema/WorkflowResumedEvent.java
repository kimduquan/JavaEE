package epf.workflow.schema;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that notifies that a workflow has been resumed.")
public class WorkflowResumedEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow that has been resumed.")
	private String name;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the workflow has been resumed.")
	private Date resumedAt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getResumedAt() {
		return resumedAt;
	}

	public void setResumedAt(Date resumedAt) {
		this.resumedAt = resumedAt;
	}
}
