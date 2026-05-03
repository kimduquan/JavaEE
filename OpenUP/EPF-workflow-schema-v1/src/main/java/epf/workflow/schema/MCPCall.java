package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import epf.workflow.schema.util.Either;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The MCP Call enables workflows to interact with Model Context Protocol (MCP) servers.")
public class MCPCall {

	@NotNull
	@JsonPropertyDescription("The version of the MCP protocol to use. Defaults to 2025-06-18.")
	private String protocolVersion = "2025-06-18";
	
	@NotNull
	@JsonPropertyDescription("The MCP method to call.")
	private String method;
	
	@JsonPropertyDescription("The MCP method parameters. Supports runtime expressions.")
	private Either<Map<?, ?>, String> parameters;
	
	@JsonPropertyDescription("The duration after which the MCP call times out.")
	private Either<String, Duration> timeout;
	
	@NotNull
	@JsonPropertyDescription("The transport to use to perform the MCP call.")
	private MCPTransport transport;
	
	@JsonPropertyDescription("Describes the client used to perform the MCP call.")
	private MCPClient client;

	public String getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Either<Map<?, ?>, String> getParameters() {
		return parameters;
	}

	public void setParameters(Either<Map<?, ?>, String> parameters) {
		this.parameters = parameters;
	}

	public Either<String, Duration> getTimeout() {
		return timeout;
	}

	public void setTimeout(Either<String, Duration> timeout) {
		this.timeout = timeout;
	}

	public MCPTransport getTransport() {
		return transport;
	}

	public void setTransport(MCPTransport transport) {
		this.transport = transport;
	}

	public MCPClient getClient() {
		return client;
	}

	public void setClient(MCPClient client) {
		this.client = client;
	}
}
