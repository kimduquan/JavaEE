package epf.shell.mail;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.mail.client.Message;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.MAIL)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface MailClient {

	/**
	 * @param message
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	Response send(final Message message) throws Exception;
}
