package epf.workflow.event.schema;

import java.util.Date;

public class WorkflowSuspendedEvent extends WorkflowLifecycleEvent {

	private Date suspendedAt;

	public Date getSuspendedAt() {
		return suspendedAt;
	}

	public void setSuspendedAt(Date suspendedAt) {
		this.suspendedAt = suspendedAt;
	}
}
