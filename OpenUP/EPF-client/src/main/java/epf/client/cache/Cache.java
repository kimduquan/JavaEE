package epf.client.cache;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import epf.client.util.Client;
import epf.security.schema.Token;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.CACHE)
public interface Cache {
	
	/**
	 * @param tokenId
	 * @return
	 */
	@GET
    @Path(Naming.SECURITY)
	@Produces(MediaType.APPLICATION_JSON)
	Token getToken(@QueryParam("tid") final String tokenId);
	
	/**
	 * @param client
	 * @param tokenId
	 * @return
	 */
	static Token getToken(final Client client, final String tokenId) {
		return client
				.request(target -> target.path(Naming.SECURITY).queryParam("tid", tokenId), 
						req -> req.accept(MediaType.APPLICATION_JSON)
						)
				.get(Token.class);
	}
}
