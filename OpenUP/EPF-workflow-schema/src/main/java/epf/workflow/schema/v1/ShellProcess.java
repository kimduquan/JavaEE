package epf.workflow.schema.v1;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Enables the execution of shell commands within a workflow, enabling workflows to interact with the underlying operating system and perform system-level operations, such as file manipulation, environment configuration, or system administration tasks.")
public class ShellProcess {

	@NotNull
	@Description("The shell command to run")
	private String command;
	
	@Description("A list of the arguments of the shell command to run")
	private Map<?, ?> arguments;
	
	@Description("A key/value mapping of the environment variables, if any, to use when running the configured process")
	private Map<?, ?> environment;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Map<?, ?> getArguments() {
		return arguments;
	}

	public void setArguments(Map<?, ?> arguments) {
		this.arguments = arguments;
	}

	public Map<?, ?> getEnvironment() {
		return environment;
	}

	public void setEnvironment(Map<?, ?> environment) {
		this.environment = environment;
	}
}
