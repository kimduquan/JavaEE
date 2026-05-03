package epf.workflow.schema;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Enables the execution of shell commands within a workflow, enabling workflows to interact with the underlying operating system and perform system-level operations, such as file manipulation, environment configuration, or system administration tasks.")
public class ShellProcess {

	@NotNull
	@JsonPropertyDescription("The shell command to run")
	private String command;
	
	@JsonPropertyDescription("A runtime expression, if any, to the shell command as standard input (stdin).")
	private String stdin;
	
	@JsonPropertyDescription("A list of the arguments, if any, to the shell command as argv")
	private List<String> arguments;
	
	@JsonPropertyDescription("A key/value mapping of the environment variables, if any, to use when running the configured process")
	private Map<String, String> environment;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getStdin() {
		return stdin;
	}

	public void setStdin(String stdin) {
		this.stdin = stdin;
	}

	public List<String> getArguments() {
		return arguments;
	}

	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
	}

	public Map<String, String> getEnvironment() {
		return environment;
	}

	public void setEnvironment(Map<String, String> environment) {
		this.environment = environment;
	}
}
