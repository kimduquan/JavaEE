package epf.workflow.event;

import java.util.Date;
import java.util.Map;

public class WorkflowCompletedEvent extends WorkflowLifecycleEvent {

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
