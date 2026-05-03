package epf.workflow.schema;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

@JsonClassDescription("Configures an AsyncAPI message to publish.")
public class AsyncAPIOutboundMessage {

	@JsonPropertyDescription("The message's payload, if any.")
	private Object payload;
	
	@JsonPropertyDescription("The message's headers, if any.")
	private Object headers;

	public Object getPayload() {
		return payload;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public Object getHeaders() {
		return headers;
	}

	public void setHeaders(Object headers) {
		this.headers = headers;
	}
}
