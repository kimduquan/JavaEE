package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import epf.workflow.schema.util.Either;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Allows workflows to pause or delay their execution for a specified period of time.")
public class Wait extends Task {

	@NotNull
	@JsonPropertyDescription("The amount of time to wait. If a string, must be a valid ISO 8601 duration expression.")
	private Either<String, Duration> wait;

	public Either<String, Duration> getWait() {
		return wait;
	}

	public void setWait(Either<String, Duration> wait) {
		this.wait = wait;
	}
}
