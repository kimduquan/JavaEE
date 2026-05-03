package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Defines the transport to use for a Model Context Protocol (MCP) call.")
public class MCPTransport {
	
	@JsonPropertyDescription("The definition of the HTTP transport to use. Required if stdio has not been set.")
	private MCPHTTPTransport http;
	
	@JsonPropertyDescription("The definition of the STDIO transport to use. Required if http has not been set.")
	private MCPSTDIOTransport stdio;
	
	@JsonPropertyDescription("A key/value mapping containing additional transport-specific configuration options, if any.")
	private Map<String, String> options;

	public MCPHTTPTransport getHttp() {
		return http;
	}

	public void setHttp(MCPHTTPTransport http) {
		this.http = http;
	}

	public MCPSTDIOTransport getStdio() {
		return stdio;
	}

	public void setStdio(MCPSTDIOTransport stdio) {
		this.stdio = stdio;
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOptions(Map<String, String> options) {
		this.options = options;
	}
}
