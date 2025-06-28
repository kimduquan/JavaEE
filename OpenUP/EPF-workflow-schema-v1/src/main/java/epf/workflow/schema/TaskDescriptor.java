package epf.workflow.schema;

import java.util.Map;

public class TaskDescriptor {

	private String name;
	private String reference;
	private Map<String, Object> definition;
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
	public Map<String, Object> getDefinition() {
		return definition;
	}
	public void setDefinition(Map<String, Object> definition) {
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
