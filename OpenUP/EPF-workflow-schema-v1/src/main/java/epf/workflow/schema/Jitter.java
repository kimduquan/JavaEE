package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Represents the definition of the parameters that control the randomness or variability of a delay, typically between retry attempts")
public class Jitter {

	@NotNull
	@Description("The minimum duration of the jitter range.")
	private Duration from;
	
	@NotNull
	@Description("The maximum duration of the jitter range.")
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
