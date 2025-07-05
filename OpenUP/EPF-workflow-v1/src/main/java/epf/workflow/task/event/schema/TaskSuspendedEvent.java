package epf.workflow.task.event.schema;

import java.util.Date;

public class TaskSuspendedEvent extends TaskLifecycleEvent {

	private Date suspendedAt;

	public Date getSuspendedAt() {
		return suspendedAt;
	}

	public void setSuspendedAt(Date suspendedAt) {
		this.suspendedAt = suspendedAt;
	}
}
