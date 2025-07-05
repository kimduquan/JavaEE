package epf.workflow.task.schema;

import java.util.Map;
import epf.workflow.schema.SwitchCase;
import epf.workflow.schema.Task;

public class SwitchTask extends Task {

	private Map<String, SwitchCase> switch_;

	public Map<String, SwitchCase> getSwitch_() {
		return switch_;
	}

	public void setSwitch_(Map<String, SwitchCase> switch_) {
		this.switch_ = switch_;
	}
}
