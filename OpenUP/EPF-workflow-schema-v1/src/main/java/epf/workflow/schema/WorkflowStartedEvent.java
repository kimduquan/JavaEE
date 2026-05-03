package epf.workflow.schema;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that a workflow has started.")
public class WorkflowStartedEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow that has started.")
	private String name;
	
	@NotNull
	@JsonPropertyDescription("An object that describes the definition of the workflow that has started.")
	private WorkflowDefinitionReference definition;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the workflow has started.")
	private Date startedAt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WorkflowDefinitionReference getDefinition() {
		return definition;
	}

	public void setDefinition(WorkflowDefinitionReference definition) {
		this.definition = definition;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}
}
