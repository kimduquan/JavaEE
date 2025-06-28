package epf.workflow.schema;

public class TaskDescriptor {

	private String name;
	private String reference;
	private Task definition;
	private Object input;
	private Object output;
	private DateTimeDescriptor startedAt;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public Task getDefinition() {
		return definition;
	}
	public void setDefinition(Task definition) {
		this.definition = definition;
	}
	public Object getInput() {
		return input;
	}
	public void setInput(Object input) {
		this.input = input;
	}
	public Object getOutput() {
		return output;
	}
	public void setOutput(Object output) {
		this.output = output;
	}
	public DateTimeDescriptor getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(DateTimeDescriptor startedAt) {
		this.startedAt = startedAt;
	}
}
