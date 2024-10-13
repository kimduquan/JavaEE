package epf.workflow.schema.v1;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Holds the definition for extending functionality, providing configuration options for how an extension extends and interacts with other components.")
public class Extension {

	@NotNull
	@Description("The type of task to extend")
	private String extend;
	
	@Description("A runtime expression used to determine whether or not the extension should apply in the specified context")
	private String when;
	
	@Description("The task to execute, if any, before the extended task")
	private Map<String, Task>[] before;
	
	@Description("The task to execute, if any, after the extended task")
	private Map<String, Task>[] after;

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

	public Map<String, Task>[] getBefore() {
		return before;
	}

	public void setBefore(Map<String, Task>[] before) {
		this.before = before;
	}

	public Map<String, Task>[] getAfter() {
		return after;
	}

	public void setAfter(Map<String, Task>[] after) {
		this.after = after;
	}
}
