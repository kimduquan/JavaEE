package epf.workflow.event;

import java.util.Date;

public class TaskCreatedEvent extends TaskLifecycleEvent {

	private Date createdAt;

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
