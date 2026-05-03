package epf.workflow.schema;

import java.net.URI;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Describes an HTTP request.")
public class HTTPRequest {

	@NotNull
	@JsonPropertyDescription("The request's method.")
	private String method;
	
	@NotNull
	@JsonPropertyDescription("The request's URI.")
	private URI uri;
	
	@JsonPropertyDescription("The HTTP request headers, if any.")
	private Map<String, String> headers;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}
