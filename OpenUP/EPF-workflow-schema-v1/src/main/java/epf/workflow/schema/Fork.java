package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Allows workflows to execute multiple subtasks concurrently, enabling parallel processing and improving the overall efficiency of the workflow. By defining a set of subtasks to perform concurrently, the Fork task facilitates the execution of complex operations in parallel, ensuring that multiple tasks can be executed simultaneously.")
public class Fork extends Task {

	public class Fork_ {
		
		@JsonPropertyDescription("The tasks to perform concurrently.")
		private Map<String, Task> branches;
		
		@JsonPropertyDescription("Indicates whether or not the concurrent tasks are racing against each other, with a single possible winner, which sets the composite task's output.")
		private Boolean compete = false;

		public Map<String, Task> getBranches() {
			return branches;
		}

		public void setBranches(Map<String, Task> branches) {
			this.branches = branches;
		}

		public Boolean getCompete() {
			return compete;
		}

		public void setCompete(Boolean compete) {
			this.compete = compete;
		}
	}
	
	private Fork_ fork;

	public Fork_ getFork() {
		return fork;
	}

	public void setFork(Fork_ fork) {
		this.fork = fork;
	}
}
