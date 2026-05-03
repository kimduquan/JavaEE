package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import epf.workflow.schema.util.Either;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Intentionally triggers and propagates errors. By employing the \"Raise\" task, workflows can deliberately generate error conditions, allowing for explicit error handling and fault management strategies to be implemented.")
public class Raise extends Task {

	public class Raise_ {
		
		@NotNull
		@JsonPropertyDescription("Defines the error to raise. If a string, must be the name of an error defined in the workflow's reusable components.")
		private Either<String, Error> error;

		public Either<String, Error> getError() {
			return error;
		}

		public void setError(Either<String, Error> error) {
			this.error = error;
		}
	}
	
	private Raise_ raise;

	public Raise_ getRaise() {
		return raise;
	}

	public void setRaise(Raise_ raise) {
		this.raise = raise;
	}
}
