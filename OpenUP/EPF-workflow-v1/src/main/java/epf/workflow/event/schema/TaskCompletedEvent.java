package epf.workflow.event.schema;

import java.util.Date;
import java.util.Map;

public class TaskCompletedEvent extends TaskLifecycleEvent {

	private Date completedAt;
	private Map<String, Object> output;
	
	public Date getCompletedAt() {
		return completedAt;
	}
	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
	}
	public Map<String, Object> getOutput() {
		return output;
	}
	public void setOutput(Map<String, Object> output) {
		this.output = output;
	}
}
