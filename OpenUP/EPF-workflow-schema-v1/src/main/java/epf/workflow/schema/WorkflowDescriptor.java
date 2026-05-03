package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class WorkflowDescriptor {

	@JsonPropertyDescription("A unique id of the workflow execution. Now specific format is imposed")
	private String id;
	
	@JsonPropertyDescription("The workflow's definition as a parsed object")
	private Workflow definition;
	
	@JsonPropertyDescription("The workflow's raw input (i.e BEFORE the input.from expression). For the result of input.from expression use the $input argument")
	private Object input;
	
	@JsonPropertyDescription("The start time of the execution")
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
