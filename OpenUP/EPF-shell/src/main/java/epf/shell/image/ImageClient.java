package epf.shell.image;

import java.io.InputStream;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;

@Path(Naming.IMAGE)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface ImageClient {

	@Path("contours")
	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@Produces(MediaType.APPLICATION_JSON)
	Response findContours(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
			final InputStream input) throws Exception;
}
