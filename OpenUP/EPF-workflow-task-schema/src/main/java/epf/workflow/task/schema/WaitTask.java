package epf.workflow.task.schema;

import epf.workflow.schema.Duration;
import epf.workflow.schema.Task;
import epf.workflow.schema.util.Either;

public class WaitTask extends Task {

	private Either<String, Duration> wait;

	public Either<String, Duration> getWait() {
		return wait;
	}

	public void setWait(Either<String, Duration> wait) {
		this.wait = wait;
	}
}
