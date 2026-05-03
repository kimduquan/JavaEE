package epf.workflow.schema;

import java.util.Date;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The data carried by the cloud event that notifies that a workflow has completed correlating events.")
public class WorkflowCorrelationCompletedEvent {

	@NotNull
	@JsonPropertyDescription("The qualified name of the workflow that has completed correlating events.")
	private String name;
	
	@NotNull
	@JsonPropertyDescription("The date and time at which the workflow has completed correlating events.")
	private Date completedAt;
	
	@JsonPropertyDescription("A key/value mapping, if any, of the resolved correlation keys.")
	private Map<?, ?> correlationKeys;

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

	public Map<?, ?> getCorrelationKeys() {
		return correlationKeys;
	}

	public void setCorrelationKeys(Map<?, ?> correlationKeys) {
		this.correlationKeys = correlationKeys;
	}
}
