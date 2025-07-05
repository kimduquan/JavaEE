package epf.workflow.task.schema;

import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotBlank;

@Description("Configures the target server of an AsyncAPI operation.")
public class AsyncAPIServer {

	@NotBlank
	@Description("The name of the server to call the specified AsyncAPI operation on.")
	private String name;
	
	@Description("The target server's variables, if any.")
	private Object variables;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getVariables() {
		return variables;
	}

	public void setVariables(Object variables) {
		this.variables = variables;
	}
}
