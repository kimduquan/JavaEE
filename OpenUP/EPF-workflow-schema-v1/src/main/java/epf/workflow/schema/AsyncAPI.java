package epf.workflow.schema;

import org.eclipse.microprofile.graphql.Description;
import epf.workflow.schema.util.Either;
import jakarta.validation.constraints.NotNull;

@Description("The AsyncAPI Call enables workflows to interact with external services described by AsyncAPI.")
public class AsyncAPI {

	@NotNull
	@Description("The AsyncAPI document that defines the operation to call.")
	private ExternalResource document;
	
	@NotNull
	@Description("A reference to the AsyncAPI operation to call.")
	private String operationRef;
	
	@Description("A reference to the server to call the specified AsyncAPI operation on. If not set, default to the first server matching the operation's channel.")
	private String server;
	
	@Description("The name of the message to use. If not set, defaults to the first message defined by the operation.")
	private String message;
	
	@Description("The name of the binding to use. If not set, defaults to the first binding defined by the operation")
	private String binding;
	
	@Description("The operation's payload, as defined by the configured message")
	private Object payload;
	
	@Description("The authentication policy, or the name of the authentication policy, to use when calling the AsyncAPI operation.")
	private Either<String, Authentication> authentication;

	public ExternalResource getDocument() {
		return document;
	}

	public void setDocument(ExternalResource document) {
		this.document = document;
	}

	public String getOperationRef() {
		return operationRef;
	}

	public void setOperationRef(String operationRef) {
		this.operationRef = operationRef;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public Either<String, Authentication> getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Either<String, Authentication> authentication) {
		this.authentication = authentication;
	}
}
