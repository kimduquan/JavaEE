package epf.workflow.task.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;

import epf.workflow.schema.Task;

@Description("Allows workflows to execute multiple subtasks concurrently, enabling parallel processing and improving the overall efficiency of the workflow. By defining a set of subtasks to perform concurrently, the Fork task facilitates the execution of complex operations in parallel, ensuring that multiple tasks can be executed simultaneously.")
public class Fork {

	@Description("The tasks to perform concurrently.")
	private Map<String, Task> branches;
	
	@Description("Indicates whether or not the concurrent tasks are racing against each other, with a single possible winner, which sets the composite task's output.")
	@DefaultValue("false")
	private boolean compete = false;

	public Map<String, Task> getBranches() {
		return branches;
	}

	public void setBranches(Map<String, Task> branches) {
		this.branches = branches;
	}

	public boolean isCompete() {
		return compete;
	}

	public void setCompete(boolean compete) {
		this.compete = compete;
	}
}
