package epf.security.auth.openid.spi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author PC
 *
 */
@Path(".well-known")
public interface Discovery {

	/**
	 * @return
	 */
	@Path("openid-configuration")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	ProviderMetadata getOpenIDConfiguration();
}
