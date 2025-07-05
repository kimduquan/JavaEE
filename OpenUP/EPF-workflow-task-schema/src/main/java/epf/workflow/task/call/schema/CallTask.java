package epf.workflow.task.call.schema;

import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.Task;

@Description("Enables the execution of a specified function within a workflow, allowing seamless integration with custom business logic or external services.")
public class CallTask extends Task {

	private Call<?> call;

	public Call<?> getCall() {
		return call;
	}

	public void setCall(Call<?> call) {
		this.call = call;
	}
}
