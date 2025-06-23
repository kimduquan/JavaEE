package epf.workflow.event;

import java.util.Date;
import java.util.Map;

public class TaskCompletedEvent extends TaskLifecycleEvent {

	private Date completedAt;
	private Map<String, String> output;
	
	public Date getCompletedAt() {
		return completedAt;
	}
	public void setCompletedAt(Date completedAt) {
		this.completedAt = completedAt;
	}
	public Map<String, String> getOutput() {
		return output;
	}
	public void setOutput(Map<String, String> output) {
		this.output = output;
	}
}
