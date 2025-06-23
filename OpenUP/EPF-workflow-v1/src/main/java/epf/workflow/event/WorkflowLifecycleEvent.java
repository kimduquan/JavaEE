package epf.workflow.event;

public abstract class WorkflowLifecycleEvent {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
