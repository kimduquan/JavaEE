package epf.shell.image;

import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.IMAGE)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface ImageClient {

	/**
	 * @param token
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@Path("contours")
	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Produces(MediaType.APPLICATION_JSON)
	Response findContours(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
			final InputStream input) throws Exception;
}
