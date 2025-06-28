package epf.workflow.task.schema;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.Task;

public class RunTask extends Task {
	
	public enum Output {
		stdout,
		stderr,
		code,
		all,
		none
	}

	private Run run;
	
	@Description("Determines whether or not the process to run should be awaited for.")
	@DefaultValue("true")
	private boolean await = true;
	
	@Description("Configures the output of the process.")
	@DefaultValue("stdout")
	private Output return_ = Output.stdout;

	public Run getRun() {
		return run;
	}

	public void setRun(Run run) {
		this.run = run;
	}

	public boolean isAwait() {
		return await;
	}

	public void setAwait(boolean await) {
		this.await = await;
	}

	public Output getReturn_() {
		return return_;
	}

	public void setReturn_(Output return_) {
		this.return_ = return_;
	}
}
