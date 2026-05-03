package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Defines a workflow or task timeout.")
public class Timeout {

	@NotNull
	@JsonPropertyDescription("The duration after which the workflow or task times out.")
	private Duration after;

	public Duration getAfter() {
		return after;
	}

	public void setAfter(Duration after) {
		this.after = after;
	}
}
