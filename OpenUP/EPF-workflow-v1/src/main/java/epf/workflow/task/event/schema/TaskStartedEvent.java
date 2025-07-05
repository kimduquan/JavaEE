package epf.workflow.task.event.schema;

import java.util.Date;

public class TaskStartedEvent extends TaskLifecycleEvent {

	private Date startedAt;

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}
}
