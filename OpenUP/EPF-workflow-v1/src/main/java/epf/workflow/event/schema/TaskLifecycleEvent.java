package epf.workflow.event.schema;

import java.net.URI;

public abstract class TaskLifecycleEvent {

	private String workflow;
	private URI task;
	
	public String getWorkflow() {
		return workflow;
	}
	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}
	public URI getTask() {
		return task;
	}
	public void setTask(URI task) {
		this.task = task;
	}
}
