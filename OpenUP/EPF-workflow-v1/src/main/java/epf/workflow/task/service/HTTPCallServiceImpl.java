package epf.workflow.task.service;

import java.net.URI;
import epf.workflow.schema.Error;
import epf.workflow.schema.HTTP;
import epf.workflow.task.spi.HTTPCallService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class HTTPCallServiceImpl implements HTTPCallService {

	@Override
	public Object call(final HTTP http, final Object input) throws Error {
		final Client client = ClientBuilder.newClient();
		WebTarget target = null;
		if(http.getEndpoint().isLeft()) {
			target = client.target(URI.create(http.getEndpoint().getLeft()));
		}
		else {
			target = client.target(URI.create(http.getEndpoint().getRight().getUri()));
		}
		Builder builder = target.request();
		Response response = null;
		if(input != null) {
			response = builder.method(http.getMethod(), Entity.entity(input, MediaType.APPLICATION_JSON_TYPE));
		}
		else {
			response = builder.method(http.getMethod());
		}
		return response.readEntity(Object.class);
	}
}
