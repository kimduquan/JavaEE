package epf.workflow.event.schema;

import java.util.Date;

public class WorkflowCorrelationStartedEvent extends WorkflowLifecycleEvent {

	private Date startedAt;

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}
}
