package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Describes the client of a Model Context Protocol (MCP) call.")
public class MCPClient {

	@NotNull
	@JsonPropertyDescription("The name of the client used to connect to the MCP server.")
	private String name;
	
	@NotNull
	@JsonPropertyDescription("The version of the client used to connect to the MCP server.")
	private String version;

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
}
