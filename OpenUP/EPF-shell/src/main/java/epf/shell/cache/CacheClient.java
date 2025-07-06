package epf.shell.cache;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;
import epf.security.schema.Token;

@Path(Naming.FILE)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface CacheClient {
	
	@GET
    @Path("security")
	@Produces(MediaType.APPLICATION_JSON)
	Token getToken(
			@QueryParam("tid") 
			final String tokenId);
}
