package epf.shell.schema;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.SCHEMA)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface SchemaClient {

	/**
	 * @param token
	 * @return
	 */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	Response getEntities(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token
    		);
	
	/**
	 * @param token
	 * @return
	 */
	@GET
    @Path("embeddable")
    @Produces(MediaType.APPLICATION_JSON)
    Response getEmbeddables(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token
    		);
}
