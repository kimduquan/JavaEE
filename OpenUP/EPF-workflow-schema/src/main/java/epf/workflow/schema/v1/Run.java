package epf.workflow.schema.v1;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

@Description("Provides the capability to execute external containers, shell commands, scripts, or workflows.")
public class Run {

	public class _Run {
		
		@Description("The definition of the container to run.")
		private ContainerProcess container;
		
		@Description("The definition of the script to run.")
		private ScriptProcess script;
		
		@Description("The definition of the shell command to run.")
		private ShellProcess shell;
		
		@Description("The definition of the workflow to run.")
		private Workflow workflow;

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

		public Workflow getWorkflow() {
			return workflow;
		}

		public void setWorkflow(Workflow workflow) {
			this.workflow = workflow;
		}
	}
	
	private _Run run;
	
	@Description("Determines whether or not the process to run should be awaited for.")
	@DefaultValue("true")
	private boolean await = true;

	public _Run getRun() {
		return run;
	}

	public void setRun(_Run run) {
		this.run = run;
	}

	public boolean isAwait() {
		return await;
	}

	public void setAwait(boolean await) {
		this.await = await;
	}
}
