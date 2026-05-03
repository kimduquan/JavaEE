package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import epf.workflow.schema.util.Either;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The AsyncAPI Call enables workflows to interact with external services described by AsyncAPI.")
public class AsyncAPICall {

	@NotNull
	@JsonPropertyDescription("The AsyncAPI document that defines the operation to call.")
	private ExternalResource document;
	
	@NotNull
	@JsonPropertyDescription("The name of the channel on which to perform the operation. The operation to perform is defined by declaring either message, in which case the channel's publish operation will be executed, or subscription, in which case the channel's subscribe operation will be executed.")
	private String channel;
	
	@NotNull
	@JsonPropertyDescription("A reference to the AsyncAPI operation to call.")
	private String operation;
	
	@JsonPropertyDescription("An object used to configure to the server to call the specified AsyncAPI operation on. If not set, default to the first server matching the operation's channel.")
	private AsyncAPIServer server;
	
	@JsonPropertyDescription("The protocol to use to select the target server. Ignored if server has been set. Supported values are: amqp, amqp1, anypointmq, googlepubsub, http, ibmmq, jms, kafka, mercure, mqtt, mqtt5, nats, pulsar, redis, sns, solace, sqs, stomp and ws")
	private String protocol;
	
	@JsonPropertyDescription("An object used to configure the message to publish using the target operation. Required if subscription has not been set.")
	private AsyncAPIOutboundMessage message;
	
	@JsonPropertyDescription("An object used to configure the subscription to messages consumed using the target operation. Required if message has not been set.")
	private AsyncAPISubscription subscription;
	
	@JsonPropertyDescription("The authentication policy, or the name of the authentication policy, to use when calling the AsyncAPI operation.")
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
