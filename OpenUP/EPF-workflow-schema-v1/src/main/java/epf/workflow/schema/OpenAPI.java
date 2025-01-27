package epf.workflow.schema;

import java.util.Map;
import org.eclipse.microprofile.graphql.Description;
import jakarta.validation.constraints.NotNull;

@Description("The OpenAPI Call enables workflows to interact with external services described by OpenAPI.")
public class OpenAPI {

	@NotNull
	@Description("The OpenAPI document that defines the operation to call.")
	private ExternalResource document;
	
	@NotNull
	@Description("The id of the OpenAPI operation to call.")
	private String operationId;
	
	@Description("A name/value mapping of the parameters, if any, of the OpenAPI operation to call.")
	private Map<? ,?> arguments;
	
	@Description("The authentication policy, or the name of the authentication policy, to use when calling the OpenAPI operation.")
	private Authentication authentication;
	
	@Description("The OpenAPI call's output format.")
	private String output;

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

	public Map<?, ?> getArguments() {
		return arguments;
	}

	public void setArguments(Map<?, ?> arguments) {
		this.arguments = arguments;
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
}
