package epf.workflow.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotBlank;

@Description("Enables the execution of shell commands within a workflow, enabling workflows to interact with the underlying operating system and perform system-level operations, such as file manipulation, environment configuration, or system administration tasks.")
public class ShellProcess {

	@NotBlank
	@Description("The shell command to run")
	private String command;
	
	@Description("A list of the arguments of the shell command to run")
	private Map<String, Object> arguments;
	
	@Description("A key/value mapping of the environment variables, if any, to use when running the configured process")
	private Map<String, String> environment;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Map<String, Object> getArguments() {
		return arguments;
	}

	public void setArguments(Map<String, Object> arguments) {
		this.arguments = arguments;
	}

	public Map<String, String> getEnvironment() {
		return environment;
	}

	public void setEnvironment(Map<String, String> environment) {
		this.environment = environment;
	}
}
