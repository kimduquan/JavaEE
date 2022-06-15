package epf.client.mail;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.MAIL)
public interface Mail {

	/**
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	Response send(final Message message) throws Exception;
	
	/**
	 * @param client
	 * @param message
	 * @return
	 * @throws Exception
	 */
	static Response send(final Client client, final Message message) {
		return client.request(
    			target -> target, 
    			req -> req
    			)
    			.post(Entity.json(message));
	}
}
