package epf.workflow.task.event.schema;

import java.util.Date;

public class TaskRetriedEvent extends TaskLifecycleEvent {

	private Date retriedAt;

	public Date getRetriedAt() {
		return retriedAt;
	}

	public void setRetriedAt(Date retriedAt) {
		this.retriedAt = retriedAt;
	}
}
