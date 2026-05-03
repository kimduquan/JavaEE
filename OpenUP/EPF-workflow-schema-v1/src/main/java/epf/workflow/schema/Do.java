package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.json.bind.annotation.JsonbProperty;

@JsonClassDescription("Serves as a fundamental building block within workflows, enabling the sequential execution of multiple subtasks. By defining a series of subtasks to perform in sequence, the Do task facilitates the efficient execution of complex operations, ensuring that each subtask is completed before the next one begins.")
public class Do extends Task {

	@JsonProperty("do")
	@JsonPropertyDescription("The tasks to perform sequentially.")
	@JsonbProperty("do")
	private Map<String, Task> do_;

	public Map<String, Task> getDo() {
		return do_;
	}

	public void setDo(Map<String, Task> do_) {
		this.do_ = do_;
	}
}
