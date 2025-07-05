package epf.workflow.task.event.schema;

import java.util.Date;

public class TaskStatusChangedEvent extends TaskLifecycleEvent {

	private Date updatedAt;
	private String status;
	
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
