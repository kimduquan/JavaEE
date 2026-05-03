package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The OpenAPI Call enables workflows to interact with external services described by OpenAPI.")
public class OpenAPICall {

	@NotNull
	@JsonPropertyDescription("The OpenAPI document that defines the operation to call.")
	private ExternalResource document;
	
	@NotNull
	@JsonPropertyDescription("The id of the OpenAPI operation to call.")
	private String operationId;
	
	@JsonPropertyDescription("A name/value mapping of the parameters, if any, of the OpenAPI operation to call.")
	private Map<?, ?> parameters;
	
	@JsonPropertyDescription("The authentication policy, or the name of the authentication policy, to use when calling the OpenAPI operation.")
	private Authentication authentication;
	
	@JsonPropertyDescription("The OpenAPI call's output format. Defaults to content.")
	private String output = "content";
	
	@JsonPropertyDescription("Specifies whether redirection status codes (300–399) should be treated as errors. If set to false, runtimes must raise an error for response status codes outside the 200–299 range. If set to true, they must raise an error for status codes outside the 200–399 range. Defaults to false.")
	private Boolean redirect = false;

	public ExternalResource getDocument() {
		return document;
	}

	public void setDocument(ExternalResource document) {
		this.document = document;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public Map<?, ?> getParameters() {
		return parameters;
	}

	public void setParameters(Map<?, ?> parameters) {
		this.parameters = parameters;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
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
