package epf.workflow.event;

import java.util.Date;

public class TaskResumedEvent extends TaskLifecycleEvent {

	private Date resumedAt;

	public Date getResumedAt() {
		return resumedAt;
	}

	public void setResumedAt(Date resumedAt) {
		this.resumedAt = resumedAt;
	}
}
