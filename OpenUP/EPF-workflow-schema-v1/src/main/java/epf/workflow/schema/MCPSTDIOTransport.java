package epf.workflow.schema;

import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Defines a Model Context Protocol (MCP) STDIO transport.")
public class MCPSTDIOTransport {

	@NotNull
	@JsonPropertyDescription("The command used to run the MCP server. Supports runtime expressions.")
	private String command;
	
	@JsonPropertyDescription("An optional list of arguments to pass to the command.")
	private List<String> arguments;
	
	@JsonPropertyDescription("A key/value mapping, if any, of environment variables used to configure the MCP server.")
	private Map<String, String> environment;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
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
