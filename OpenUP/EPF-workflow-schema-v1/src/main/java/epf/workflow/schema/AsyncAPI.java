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
	@Description("The name of the channel on which to perform the operation.")
	private String channel;
	
	@NotNull
	@Description("A reference to the AsyncAPI operation to call.")
	private String operation;
	
	@Description("An object used to configure to the server to call the specified AsyncAPI operation on.")
	private AsyncAPIServer server;
	
	@Description("The protocol to use to select the target server. Ignored if server has been set.")
	private String protocol;
	
	@Description("An object used to configure the message to publish using the target operation.")
	private AsyncAPIOutboundMessage message;
	
	@Description("An object used to configure the subscription to messages consumed using the target operation.")
	private AsyncAPISubscription subscription;
	
	@Description("The authentication policy, or the name of the authentication policy, to use when calling the AsyncAPI operation.")
	private Either<String, Authentication> authentication;

	public ExternalResource getDocument() {
		return document;
	}

	public void setDocument(ExternalResource document) {
		this.document = document;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public AsyncAPIServer getServer() {
		return server;
	}

	public void setServer(AsyncAPIServer server) {
		this.server = server;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public AsyncAPIOutboundMessage getMessage() {
		return message;
	}

	public void setMessage(AsyncAPIOutboundMessage message) {
		this.message = message;
	}

	public AsyncAPISubscription getSubscription() {
		return subscription;
	}

	public void setSubscription(AsyncAPISubscription subscription) {
		this.subscription = subscription;
	}

	public Either<String, Authentication> getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Either<String, Authentication> authentication) {
		this.authentication = authentication;
	}
}
