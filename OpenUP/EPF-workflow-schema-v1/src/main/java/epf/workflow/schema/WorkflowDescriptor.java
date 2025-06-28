package epf.workflow.schema;

import java.util.Map;

public class WorkflowDescriptor {

	private String id;
	private Map<String, Object> definition;
	private Object input;
	private DateTimeDescriptor startedAt;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public DateTimeDescriptor getStartedAt() {
		return startedAt;
	}
	public void setStartedAt(DateTimeDescriptor startedAt) {
		this.startedAt = startedAt;
	}
}
