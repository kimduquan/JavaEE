package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Represents the definition of the parameters that control the randomness or variability of a delay, typically between retry attempts")
public class Jitter {

	@NotNull
	@JsonPropertyDescription("The minimum duration of the jitter range.")
	private Duration from;
	
	@NotNull
	@JsonPropertyDescription("The maximum duration of the jitter range.")
	private Duration to;

	public Duration getFrom() {
		return from;
	}

	public void setFrom(Duration from) {
		this.from = from;
	}

	public Duration getTo() {
		return to;
	}

	public void setTo(Duration to) {
		this.to = to;
	}
}
