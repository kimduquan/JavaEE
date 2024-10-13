package epf.workflow.schema.v1;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;

@Description("Serves as a fundamental building block within workflows, enabling the sequential execution of multiple subtasks. By defining a series of subtasks to perform in sequence, the Do task facilitates the efficient execution of complex operations, ensuring that each subtask is completed before the next one begins.")
public class Do {

	@Description("The tasks to perform sequentially.")
	private Map<String, Task>[] do_;

	public Map<String, Task>[] getDo_() {
		return do_;
	}

	public void setDo_(Map<String, Task>[] do_) {
		this.do_ = do_;
	}
}
