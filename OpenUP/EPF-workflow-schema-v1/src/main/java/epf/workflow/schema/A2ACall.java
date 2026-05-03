package epf.workflow.schema;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import epf.workflow.schema.util.Either;
import jakarta.validation.constraints.NotNull;

@JsonClassDescription("The A2A Call enables workflows to interact with AI agents described by A2A.")
public class A2ACall {

	@NotNull
	@JsonPropertyDescription("The A2A JSON-RPC method to send. Supported values are: message/send, message/stream, tasks/get, tasks/list, tasks/cancel, tasks/resubscribe, tasks/pushNotificationConfig/set, tasks/pushNotificationConfig/get, tasks/pushNotificationConfig/list, tasks/pushNotificationConfig/delete, and agent/getAuthenticatedExtendedCard")
	private String method;
	
	@JsonPropertyDescription("The AgentCard resource that describes the agent to call. Required if server has not been set.")
	private ExternalResource agentCard;
	
	@JsonPropertyDescription("An URI or an object that describes the A2A server to call. Required if agentCard has not been set, otherwise ignored")
	private Either<String, Endpoint> server;
	
	@JsonPropertyDescription("The parameters for the A2A RPC method. For the message/send and message/stream methods, runtimes must default message.messageId to a uuid and message.role to user. Supports runtime expressions.")
	private Either<Map<?, ?>, String> parameters;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public ExternalResource getAgentCard() {
		return agentCard;
	}

	public void setAgentCard(ExternalResource agentCard) {
		this.agentCard = agentCard;
	}

	public Either<String, Endpoint> getServer() {
		return server;
	}

	public void setServer(Either<String, Endpoint> server) {
		this.server = server;
	}

	public Either<Map<?, ?>, String> getParameters() {
		return parameters;
	}

	public void setParameters(Either<Map<?, ?>, String> parameters) {
		this.parameters = parameters;
	}
}
