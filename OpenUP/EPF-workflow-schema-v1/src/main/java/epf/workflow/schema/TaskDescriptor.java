package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class TaskDescriptor {

	@JsonPropertyDescription("The task's name.")
	private String name;
	
	@JsonPropertyDescription("The task's reference.")
	private String reference;
	
	@JsonPropertyDescription("The tasks definition (specified under the name) as a parsed object")
	private Task definition;
	
	@JsonPropertyDescription("The task's raw input (i.e. BEFORE the input.from expression). For the result of input.from expression use the context of the runtime expression (for jq .)")
	private Object input;
	
	@JsonPropertyDescription("The task's raw output (i.e. BEFORE the output.as expression).")
	private Object output;
	
	@JsonPropertyDescription("The start time of the task")
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
