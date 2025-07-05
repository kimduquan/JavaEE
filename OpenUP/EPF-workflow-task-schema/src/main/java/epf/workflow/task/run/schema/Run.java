package epf.workflow.task.run.schema;

import org.eclipse.microprofile.graphql.Description;

import epf.workflow.schema.WorkflowProcess;

@Description("Provides the capability to execute external containers, shell commands, scripts, or workflows.")
public class Run {
	
	@Description("The definition of the container to run.")
	private ContainerProcess container;
	
	@Description("The definition of the script to run.")
	private ScriptProcess script;
	
	@Description("The definition of the shell command to run.")
	private ShellProcess shell;
	
	@Description("The definition of the workflow to run.")
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
