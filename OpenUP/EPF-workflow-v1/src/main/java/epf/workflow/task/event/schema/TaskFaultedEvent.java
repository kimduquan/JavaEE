package epf.workflow.task.event.schema;

import java.util.Date;
import epf.workflow.schema.Error;

public class TaskFaultedEvent extends TaskLifecycleEvent {

	private Date faultedAt;
	private Error error;

	public Date getFaultedAt() {
		return faultedAt;
	}

	public void setFaultedAt(Date faultedAt) {
		this.faultedAt = faultedAt;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
}
