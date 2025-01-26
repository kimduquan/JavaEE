package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.util.Either;
import jakarta.validation.constraints.NotNull;

@Description("Allows workflows to pause or delay their execution for a specified period of time.")
public class Wait {

	@NotNull
	@Description("The amount of time to wait.")
	private Either<String, Duration> wait;

	public Either<String, Duration> getWait() {
		return wait;
	}

	public void setWait(Either<String, Duration> wait) {
		this.wait = wait;
	}
}
