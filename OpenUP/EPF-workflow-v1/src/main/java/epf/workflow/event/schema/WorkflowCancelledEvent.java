package epf.workflow.event.schema;

import java.util.Date;

public class WorkflowCancelledEvent extends WorkflowLifecycleEvent {

	private Date cancelledAt;

	public Date getCancelledAt() {
		return cancelledAt;
	}

	public void setCancelledAt(Date cancelledAt) {
		this.cancelledAt = cancelledAt;
	}
}
