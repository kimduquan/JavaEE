package epf.workflow.schema;

import java.util.Date;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that a workflow ran to completion.")
public class WorkflowCompletedEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow ran to completion.")
	private String name;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the workflow ran to completion.")
	private Date completedAt;
	
	@JsonPropertyDescription("The workflow's output, if any.")
	private Map<?, ?> output;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCompletedAt() {
		return completedAt;
	}

	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
	}

	public Map<?, ?> getOutput() {
		return output;
	}

	public void setOutput(Map<?, ?> output) {
		this.output = output;
	}
}
