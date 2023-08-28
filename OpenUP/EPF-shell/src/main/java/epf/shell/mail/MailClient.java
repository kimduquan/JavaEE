package epf.shell.mail;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
