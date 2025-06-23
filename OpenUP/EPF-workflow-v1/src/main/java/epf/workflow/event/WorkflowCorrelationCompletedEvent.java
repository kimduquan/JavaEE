package epf.workflow.event;

import java.util.Date;
import java.util.Map;

public class WorkflowCorrelationCompletedEvent extends WorkflowLifecycleEvent {

	private Date completedAt;
	private Map<String, String> correlationKeys;
	
	public Date getCompletedAt() {
		return completedAt;
	}
	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
	}
	public Map<String, String> getCorrelationKeys() {
		return correlationKeys;
	}
	public void setCorrelationKeys(Map<String, String> correlationKeys) {
		this.correlationKeys = correlationKeys;
	}
}
