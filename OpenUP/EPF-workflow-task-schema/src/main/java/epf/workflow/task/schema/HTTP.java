package epf.workflow.task.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.Endpoint;
import epf.workflow.schema.util.Either;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Description("The HTTP Call enables workflows to interact with external services over HTTP.")
public class HTTP {

	@NotBlank
	@Description("The HTTP request method.")
	private String method;
	
	@NotNull
	@Description("An URI or an object that describes the HTTP endpoint to call.")
	private Either<String, Endpoint> endpoint;
	
	@Description("A name/value mapping of the HTTP headers to use, if any.")
	private Map<String, String> headers;
	
	@Description("The HTTP request body, if any.")
	private Object body;
	
	@Description("A name/value mapping of the query parameters to use, if any.")
	private Map<String, Object> query;
	
	@DefaultValue("content")
	@Description("The http call's output format.")
	private String output = "content";
	
	@DefaultValue("false")
	@Description("Specifies whether redirection status codes (300–399) should be treated as errors.")
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

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public Map<String, Object> getQuery() {
		return query;
	}

	public void setQuery(Map<String, Object> query) {
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
