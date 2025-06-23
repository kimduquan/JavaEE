package epf.workflow.event;

import java.util.Date;

public class TaskCancelledEvent extends TaskLifecycleEvent {

	private Date cancelledAt;

	public Date getCancelledAt() {
		return cancelledAt;
	}

	public void setCancelledAt(Date cancelledAt) {
		this.cancelledAt = cancelledAt;
	}
}
