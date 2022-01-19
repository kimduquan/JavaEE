package epf.shell.schema;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
