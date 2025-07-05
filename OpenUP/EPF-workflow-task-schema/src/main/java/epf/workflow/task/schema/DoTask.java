package epf.workflow.task.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.Task;

@Description("Serves as a fundamental building block within workflows, enabling the sequential execution of multiple subtasks.")
public class DoTask extends Task {

	@Description("The tasks to perform sequentially.")
	private Map<String, Task> do_;

	public Map<String, Task> getDo_() {
		return do_;
	}

	public void setDo_(Map<String, Task> do_) {
		this.do_ = do_;
	}
}
