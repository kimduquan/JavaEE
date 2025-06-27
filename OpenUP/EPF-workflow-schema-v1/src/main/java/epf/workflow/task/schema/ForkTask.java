package epf.workflow.task.schema;

import org.eclipse.microprofile.graphql.Description;

import epf.workflow.schema.Task;

@Description("Allows workflows to execute multiple subtasks concurrently, enabling parallel processing and improving the overall efficiency of the workflow.")
public class ForkTask extends Task {

	private Fork fork;

	public Fork getFork() {
		return fork;
	}

	public void setFork(Fork fork) {
		this.fork = fork;
	}
}
