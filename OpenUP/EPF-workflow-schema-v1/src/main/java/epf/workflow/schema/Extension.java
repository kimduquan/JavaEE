package epf.workflow.schema;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Holds the definition for extending functionality, providing configuration options for how an extension extends and interacts with other components.")
public class Extension {

	@NotNull
	@JsonPropertyDescription("The type of task to extend. Supported values are: call, composite, emit, extension, for, listen, raise, run, set, switch, try, wait and all")
	private String extend;
	
	@JsonPropertyDescription("A runtime expression used to determine whether or not the extension should apply in the specified context")
	private String when;
	
	@JsonPropertyDescription("The list of tasks to execute, if any, before the extended task")
	private List<Task> before;
	
	@JsonPropertyDescription("The list of tasks to execute, if any, after the extended task")
	private List<Task> after;

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
		this.when = when;
	}

	public List<Task> getBefore() {
		return before;
	}

	public void setBefore(List<Task> before) {
		this.before = before;
	}

	public List<Task> getAfter() {
		return after;
	}

	public void setAfter(List<Task> after) {
		this.after = after;
	}
}
