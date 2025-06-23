package epf.workflow.event;

import java.util.Date;
import epf.workflow.schema.Error;

public class WorkflowFaultedEvent extends WorkflowLifecycleEvent {

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
