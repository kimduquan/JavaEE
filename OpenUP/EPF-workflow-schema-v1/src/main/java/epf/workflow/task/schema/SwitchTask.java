package epf.workflow.task.schema;

import epf.workflow.schema.SwitchCase;
import epf.workflow.schema.Task;

public class SwitchTask extends Task {

	private SwitchCase[] switch_;

	public SwitchCase[] getSwitch_() {
		return switch_;
	}

	public void setSwitch_(SwitchCase[] switch_) {
		this.switch_ = switch_;
	}
}
