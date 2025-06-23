package epf.workflow.event;

import java.util.Date;

public class WorkflowStatusChangedEvent extends WorkflowLifecycleEvent {

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
