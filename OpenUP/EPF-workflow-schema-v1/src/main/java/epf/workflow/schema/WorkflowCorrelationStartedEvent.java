package epf.workflow.schema;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that a workflow has started correlating events.")
public class WorkflowCorrelationStartedEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow that has started correlating events.")
	private String name;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the workflow has started correlating events.")
	private Date startedAt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}
}
