package epf.lang.messaging.messenger;

import java.util.logging.Logger;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.lang.messaging.messenger.client.schema.Message;
import epf.lang.messaging.messenger.client.schema.MessageRef;
import epf.lang.messaging.messenger.client.schema.ResponseMessage;
import epf.lang.messaging.messenger.shema.Pages;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("lang/messaging/messenger")
public class Messenger {
	
	private static final Logger LOGGER = Logger.getLogger(Messenger.class.getName());
	
	@Inject
	@ConfigProperty(name = "epf.lang.messaging.messenger.token")
	String token;
	
	@RestClient
	transient Client client;
	
	@GET
	public Response verify(
			@QueryParam("hub.mode")
			final String hub_mode,
			@QueryParam("hub.verify_token")
			final String hub_verify_token,
			@QueryParam("hub.challenge")
			final String hub_challenge) throws Exception {
		if("subscribe".equals(hub_mode) && token.equals(hub_verify_token)) {
			return Response.ok(hub_challenge).build();
		}
		throw new ForbiddenException();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response subscribe(final Pages data) throws Exception {
		final ResponseMessage response = new ResponseMessage();
		response.setRecipient(data.getEntry().getFirst().getMessaging().getFirst().getSender());
		final Message message = new Message();
		message.setText(data.getEntry().getFirst().getMessaging().getFirst().getMessage().getText());
		response.setMessage(message);
		final MessageRef msgRef = client.send(data.getEntry().getFirst().getId(), token, response);
		LOGGER.info(String.format("response[%s]:%s", msgRef.getRecipient_id(), msgRef.getMessage_id()));
		return Response.ok().build();
	}

}
