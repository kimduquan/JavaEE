package epf.mail.client;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;

@Path(Naming.MAIL)
public interface Mail {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	Response send(final Message message) throws Exception;
	
	static Response send(final Client client, final Message message) {
		return client.request(
    			target -> target, 
    			req -> req
    			)
    			.post(Entity.json(message));
	}
}
