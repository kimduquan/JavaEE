package epf.workflow.task.call.schema;

import java.net.URI;
import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Describes an HTTP request.")
public class HTTPRequest {

	@NotNull
	@Description("The request's method.")
	private String method;
	
	@NotNull
	@Description("The request's URI.")
	private URI uri;
	
	@Description("The HTTP request headers, if any.")
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
