package epf.workflow.task.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.Task;
import jakarta.validation.constraints.NotNull;

public class ForTask extends Task {
	
	private For for_;
	
	@Description("A runtime expression that represents the condition, if any, that must be met for the iteration to continue.")
	private String while_;
	
	@NotNull
	@Description("The task(s) to perform for each item in the collection.")
	private Map<String, Task> do_;

	public String getWhile_() {
		return while_;
	}

	public void setWhile_(String while_) {
		this.while_ = while_;
	}

	public Map<String, Task> getDo_() {
		return do_;
	}

	public void setDo_(Map<String, Task> do_) {
		this.do_ = do_;
	}

	public For getFor_() {
		return for_;
	}

	public void setFor_(For for_) {
		this.for_ = for_;
	}
}
