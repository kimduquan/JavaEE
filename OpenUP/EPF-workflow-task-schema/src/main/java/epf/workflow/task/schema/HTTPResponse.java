package epf.workflow.task.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("Describes an HTTP response.")
public class HTTPResponse {

	@NotNull
	@Description("The HTTP request associated with the HTTP response.")
	private HTTPRequest request;
	
	@NotNull
	@Description("The HTTP response status code.")
	private Integer statusCode;
	
	@Description("The HTTP response headers, if any.")
	private Map<String, String> headers;
	
	@Description("The HTTP response content, if any.")
	private Object content;

	public HTTPRequest getRequest() {
		return request;
	}

	public void setRequest(HTTPRequest request) {
		this.request = request;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
}
