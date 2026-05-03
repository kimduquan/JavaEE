package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.json.bind.annotation.JsonbProperty;

@JsonClassDescription("Provides the capability to execute external containers, shell commands, scripts, or workflows.")
public class Run extends Task {

	public class Run_ {
		
		@JsonPropertyDescription("The definition of the container to run. Required if script, shell and workflow have not been set.")
		private ContainerProcess container;
		
		@JsonPropertyDescription("The definition of the script to run. Required if container, shell and workflow have not been set.")
		private ScriptProcess script;
		
		@JsonPropertyDescription("The definition of the shell command to run. Required if container, script and workflow have not been set.")
		private ShellProcess shell;
		
		@JsonPropertyDescription("The definition of the workflow to run. Required if container, script and shell have not been set.")
		private WorkflowProcess workflow;

		public ContainerProcess getContainer() {
			return container;
		}

		public void setContainer(ContainerProcess container) {
			this.container = container;
		}

		public ScriptProcess getScript() {
			return script;
		}

		public void setScript(ScriptProcess script) {
			this.script = script;
		}

		public ShellProcess getShell() {
			return shell;
		}

		public void setShell(ShellProcess shell) {
			this.shell = shell;
		}

		public WorkflowProcess getWorkflow() {
			return workflow;
		}

		public void setWorkflow(WorkflowProcess workflow) {
			this.workflow = workflow;
		}
	}
	
	private Run_ run;
	
	@JsonPropertyDescription("Determines whether or not the process to run should be awaited for. Defaults to true.")
	private Boolean await = true;
	
	@JsonProperty("return")
	@JsonPropertyDescription("Configures the output of the process. Defaults to stdout.")
	@JsonbProperty("return")
	private String return_ = "stdout";

	public Run_ getRun() {
		return run;
	}

	public void setRun(Run_ run) {
		this.run = run;
	}

	public Boolean getAwait() {
		return await;
	}

	public void setAwait(Boolean await) {
		this.await = await;
	}

	public String getReturn() {
		return return_;
	}

	public void setReturn(String return_) {
		this.return_ = return_;
	}
}
