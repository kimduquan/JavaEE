package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Enables the execution of a specified function within a workflow, allowing seamless integration with custom business logic or external services.")
public class Call<T> extends Task {

	@NotNull
	@JsonPropertyDescription("The name of the function to call.")
	private String call;
	
	@JsonPropertyDescription("A name/value mapping of the parameters to call the function with")
	private T with;

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public T getWith() {
		return with;
	}

	public void setWith(T with) {
		this.with = with;
	}
}