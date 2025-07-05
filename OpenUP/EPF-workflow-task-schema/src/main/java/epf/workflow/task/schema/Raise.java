package epf.workflow.task.schema;

import org.eclipse.microprofile.graphql.Description;

import epf.workflow.schema.Error;
import epf.workflow.schema.util.Either;
import jakarta.validation.constraints.NotNull;

@Description("Intentionally triggers and propagates errors. By employing the \"Raise\" task, workflows can deliberately generate error conditions, allowing for explicit error handling and fault management strategies to be implemented.")
public class Raise {
	
	@NotNull
	@Description("Defines the error to raise.")
	private Either<String, Error> error;

	public Either<String, Error> getError() {
		return error;
	}

	public void setError(Either<String, Error> error) {
		this.error = error;
	}
}
