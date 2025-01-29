package epf.workflow;

import jakarta.ws.rs.core.Response;

public class ResponseBuilder {

	public ResponseBuilder link(final String service, final String method, final String...path) {
		return this;
	}
	
	public ResponseBuilder entity(final Object entity) {
		return this;
	}
	
	public Response build() {
		return Response.ok().build();
	}
}
