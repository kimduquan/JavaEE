package epf.workflow.schema;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotBlank;

@Description("Enables the invocation and execution of nested workflows within a parent workflow, facilitating modularization, reusability, and abstraction of complex logic or business processes by encapsulating them into standalone workflow units.")
public class WorkflowProcess {

	@NotBlank
	@Description("The name of the workflow to run")
	private String name;
	
	@NotBlank
	@Description("The version of the workflow to run.")
	@DefaultValue("latest")
	private String version = "latest";
	
	@Description("The data, if any, to pass as input to the workflow to execute. The value should be validated against the target workflow's input schema, if specified")
	private Object input;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Object getInput() {
		return input;
	}

	public void setInput(Object input) {
		this.input = input;
	}
}
