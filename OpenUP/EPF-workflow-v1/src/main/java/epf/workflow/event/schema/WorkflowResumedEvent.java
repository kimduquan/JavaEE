package epf.workflow.event.schema;

import java.util.Date;

public class WorkflowResumedEvent extends WorkflowLifecycleEvent {

	private Date resumedAt;

	public Date getResumedAt() {
		return resumedAt;
	}

	public void setResumedAt(Date resumedAt) {
		this.resumedAt = resumedAt;
	}
}
