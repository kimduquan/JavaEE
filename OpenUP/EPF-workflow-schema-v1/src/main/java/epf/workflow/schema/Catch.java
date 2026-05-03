package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import epf.workflow.schema.util.Either;
import jakarta.json.bind.annotation.JsonbProperty;

@JsonClassDescription("Defines the configuration of a catch clause, which a concept used to catch errors.")
public class Catch {

	@JsonPropertyDescription("The definition of the errors to catch.")
	private Error errors;
	
	@JsonPropertyDescription("The name of the runtime expression variable to save the error as. Defaults to 'error'.")
	private String as = "error";
	
	@JsonPropertyDescription("A runtime expression used to determine whether or not to catch the filtered error.")
	private String when;
	
	@JsonPropertyDescription("A runtime expression used to determine whether or not to catch the filtered error.")
	private String exceptWhen;
	
	@JsonPropertyDescription("The retry policy to use, if any, when catching errors. If a string, must be the name of a retry policy defined in the workflow's reusable components.")
	private Either<String, Retry> retry;
	
	@JsonProperty("do")
	@JsonPropertyDescription("The definition of the task(s) to run when catching an error.")
	@JsonbProperty("do")
	private Map<String, Task> do_;

	public Error getErrors() {
		return errors;
	}

	public void setErrors(Error errors) {
		this.errors = errors;
	}

	public String getAs() {
		return as;
	}

	public void setAs(String as) {
		this.as = as;
	}

	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
		this.when = when;
	}

	public String getExceptWhen() {
		return exceptWhen;
	}

	public void setExceptWhen(String exceptWhen) {
		this.exceptWhen = exceptWhen;
	}

	public Either<String, Retry> getRetry() {
		return retry;
	}

	public void setRetry(Either<String, Retry> retry) {
		this.retry = retry;
	}

	public Map<String, Task> getDo() {
		return do_;
	}

	public void setDo(Map<String, Task> do_) {
		this.do_ = do_;
	}
}
