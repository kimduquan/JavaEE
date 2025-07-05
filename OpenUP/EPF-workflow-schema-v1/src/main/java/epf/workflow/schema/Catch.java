package epf.workflow.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.util.Either;

@Description("Defines the configuration of a catch clause, which a concept used to catch errors.")
public class Catch {

	@Description("The definition of the errors to catch.")
	private Error errors;
	
	@Description("The name of the runtime expression variable to save the error as.")
	@DefaultValue("error")
	private String as = "error";
	
	@Description("A runtime expression used to determine whether or not to catch the filtered error.")
	private String when;
	
	@Description("A runtime expression used to determine whether or not to catch the filtered error.")
	private String exceptWhen;
	
	@Description("The retry policy to use, if any, when catching errors.")
	private Either<String, RetryPolicy> retry;
	
	@Description("The definition of the task(s) to run when catching an error.")
	private Map<String, Task> _do;

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

	public Either<String, RetryPolicy> getRetry() {
		return retry;
	}

	public void setRetry(Either<String, RetryPolicy> retry) {
		this.retry = retry;
	}

	public Map<String, Task> get_do() {
		return _do;
	}

	public void set_do(Map<String, Task> _do) {
		this._do = _do;
	}
}
