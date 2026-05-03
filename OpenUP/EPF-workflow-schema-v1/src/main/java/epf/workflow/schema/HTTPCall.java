package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import epf.workflow.schema.util.Either;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The HTTP Call enables workflows to interact with external services over HTTP.")
public class HTTPCall {

	@NotNull
	@JsonPropertyDescription("The HTTP request method.")
	private String method;
	
	@NotNull
	@JsonPropertyDescription("An URI or an object that describes the HTTP endpoint to call.")
	private Either<String, Endpoint> endpoint;
	
	@JsonPropertyDescription("A name/value mapping of the HTTP headers to use, if any.")
	private Map<?, ?> headers;
	
	@JsonPropertyDescription("The HTTP request body, if any.")
	private Object body;
	
	@JsonPropertyDescription("A name/value mapping of the query parameters to use, if any.")
	private Map<String, ?> query;
	
	@JsonPropertyDescription("The http call's output format. Defaults to content.")
	private String output = "content";
	
	@JsonPropertyDescription("Specifies whether redirection status codes (300–399) should be treated as errors. If set to false, runtimes must raise an error for response status codes outside the 200–299 range. If set to true, they must raise an error for status codes outside the 200–399 range. Defaults to false.")
	private Boolean redirect = false;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Either<String, Endpoint> getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Either<String, Endpoint> endpoint) {
		this.endpoint = endpoint;
	}

	public Map<?, ?> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<?, ?> headers) {
		this.headers = headers;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public Map<String, ?> getQuery() {
		return query;
	}

	public void setQuery(Map<String, ?> query) {
		this.query = query;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public Boolean getRedirect() {
		return redirect;
	}

	public void setRedirect(Boolean redirect) {
		this.redirect = redirect;
	}
}
