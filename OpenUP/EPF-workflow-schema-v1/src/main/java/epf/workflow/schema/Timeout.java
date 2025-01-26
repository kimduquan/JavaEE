package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Defines a workflow or task timeout.")
public class Timeout {

	@NotNull
	@Description("The duration after which the workflow or task times out.")
	private Duration after;

	public Duration getAfter() {
		return after;
	}

	public void setAfter(Duration after) {
		this.after = after;
	}
}
