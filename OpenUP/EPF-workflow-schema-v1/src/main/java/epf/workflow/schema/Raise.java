package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.util.Either;
import jakarta.validation.constraints.NotNull;

@Description("Intentionally triggers and propagates errors. By employing the \"Raise\" task, workflows can deliberately generate error conditions, allowing for explicit error handling and fault management strategies to be implemented.")
public class Raise {

	public class _Raise {
		
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
	
	private _Raise raise;

	public _Raise getRaise() {
		return raise;
	}

	public void setRaise(_Raise raise) {
		this.raise = raise;
	}
}
