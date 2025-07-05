package epf.workflow.schema;

public class WorkflowDescriptor {

	private String id;
	private Workflow definition;
	private Object input;
	private DateTimeDescriptor startedAt;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Workflow getDefinition() {
		return definition;
	}
	public void setDefinition(Workflow definition) {
		this.definition = definition;
	}
	public Object getInput() {
		return input;
	}
	public void setInput(Object input) {
		this.input = input;
	}
	public DateTimeDescriptor getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(DateTimeDescriptor startedAt) {
		this.startedAt = startedAt;
	}
}
