package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Configures the target server of an AsyncAPI operation.")
public class AsyncAPIServer {

	@NotNull
	@JsonPropertyDescription("The name of the server to call the specified AsyncAPI operation on.")
	private String name;
	
	@JsonPropertyDescription("The target server's variables, if any.")
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
