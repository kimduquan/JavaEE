package epf.workflow.task.schema;

import epf.workflow.schema.Task;

public class RaiseTask extends Task {

	private Raise raise;

	public Raise getRaise() {
		return raise;
	}

	public void setRaise(Raise raise) {
		this.raise = raise;
	}
}
