package epf.shell.cache;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;
import epf.security.schema.Token;

/**
 * @author PC
 *
 */
@Path(Naming.FILE)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface CacheClient {
	
	/**
	 * @param tokenId
	 * @return
	 */
	@GET
    @Path("security")
	@Produces(MediaType.APPLICATION_JSON)
	Token getToken(
			@QueryParam("tid") 
			final String tokenId);
}
