package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("Describes an HTTP response.")
public class HTTPResponse {

	@NotNull
	@JsonPropertyDescription("The HTTP request associated with the HTTP response.")
	private HTTPRequest request;
	
	@NotNull
	@JsonPropertyDescription("The HTTP response status code.")
	private Integer statusCode;
	
	@JsonPropertyDescription("The HTTP response headers, if any.")
	private Map<String, String> headers;
	
	@JsonPropertyDescription("The HTTP response content, if any. If the request's content type is one of the following, should contain the deserialized response content. Otherwise, should contain the base-64 encoded response content, if any.")
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
