package epf.workflow.task.call.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.Authentication;
import epf.workflow.schema.ExternalResource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Description("The OpenAPI Call enables workflows to interact with external services described by OpenAPI.")
public class OpenAPI {

	@NotNull
	@Description("The OpenAPI document that defines the operation to call.")
	private ExternalResource document;
	
	@NotBlank
	@Description("The id of the OpenAPI operation to call.")
	private String operationId;
	
	@Description("A name/value mapping of the parameters, if any, of the OpenAPI operation to call.")
	private Map<String, Object> arguments;
	
	@Description("The authentication policy, or the name of the authentication policy, to use when calling the OpenAPI operation.")
	private Authentication authentication;
	
	@DefaultValue("content")
	@Description("The OpenAPI call's output format.")
	private String output = "content";
	
	@DefaultValue("false")
	@Description("Specifies whether redirection status codes (300–399) should be treated as errors.")
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

	public void setArguments(Map<String, Object> arguments) {
		this.arguments = arguments;
	}
}
