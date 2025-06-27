package epf.workflow.task.schema;

import java.util.Map;
import epf.workflow.schema.Catch;
import epf.workflow.schema.Task;

public class TryTask extends Task {

	private Map<String, Task> try_;
	
	private Catch catch_;

	public Map<String, Task> getTry_() {
		return try_;
	}

	public void setTry_(Map<String, Task> try_) {
		this.try_ = try_;
	}

	public Catch getCatch_() {
		return catch_;
	}

	public void setCatch_(Catch catch_) {
		this.catch_ = catch_;
	}
}
