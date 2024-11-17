package epf.lang.messaging.messenger;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.lang.messaging.messenger.client.schema.MessageRef;
import epf.lang.messaging.messenger.client.schema.ResponseMessage;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@RegisterRestClient(configKey = "epf.lang.messaging.messenger.client")
public interface Client {

	@Path("{page_id}/messages")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	MessageRef send(
			@PathParam("page_id")
			final String page_id,
			@QueryParam("access_token") 
			final String access_token, 
			final ResponseMessage responseMessage) throws Exception;
}
