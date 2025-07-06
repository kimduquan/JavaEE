package epf.shell.lang;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path(Naming.LANG)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface LangClient {

	@Path(Naming.MESSAGING)
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	void send(
			@QueryParam("id")
			final String id, 
			final String text);
}
