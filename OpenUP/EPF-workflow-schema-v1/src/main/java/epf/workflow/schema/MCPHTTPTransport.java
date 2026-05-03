package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import epf.workflow.schema.util.Either;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Defines a Model Context Protocol (MCP) HTTP transport.")
public class MCPHTTPTransport {

	@NotNull
	@JsonPropertyDescription("An URI or an object that references the MCP server endpoint to connect to. Supports runtime expressions.")
	private Either<String, Endpoint> endpoint;
	
	@JsonPropertyDescription("A key/value mapping of the HTTP headers to send with requests, if any.")
	private Map<String, String> headers;

	public Either<String, Endpoint> getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Either<String, Endpoint> endpoint) {
		this.endpoint = endpoint;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}
