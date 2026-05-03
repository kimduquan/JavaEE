package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Configures the lifetime of a container.")
public class ContainerLifetime {

	@NotNull
	@JsonPropertyDescription("The cleanup policy to use. Defaults to never.")
	private String cleanup = "never";
	
	@JsonPropertyDescription("The duration, if any, after which to delete the container once executed. Required if cleanup has been set to eventually, otherwise ignored.")
	private Duration after;

	public String getCleanup() {
		return cleanup;
	}

	public void setCleanup(String cleanup) {
		this.cleanup = cleanup;
	}

	public Duration getAfter() {
		return after;
	}

	public void setAfter(Duration after) {
		this.after = after;
	}
}
