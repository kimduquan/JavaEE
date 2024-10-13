package epf.workflow.schema.v1;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Enables the execution of a specified function within a workflow, allowing seamless integration with custom business logic or external services.")
public class Call {

	@NotNull
	@Description("The name of the function to call.")
	private String call;
	
	@Description("A name/value mapping of the parameters to call the function with")
	private Map<?, ?> with;

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public Map<?, ?> getWith() {
		return with;
	}

	public void setWith(Map<?, ?> with) {
		this.with = with;
	}
}
