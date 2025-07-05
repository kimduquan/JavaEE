package epf.workflow.event.schema;

public abstract class WorkflowLifecycleEvent {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
