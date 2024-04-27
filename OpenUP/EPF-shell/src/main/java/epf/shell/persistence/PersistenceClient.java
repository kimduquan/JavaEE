package epf.shell.persistence;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
@Path(Naming.PERSISTENCE)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface PersistenceClient {

	/**
	 * @param token
	 * @param schema
	 * @param entity
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@POST
    @Path("{schema}/{entity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response persist(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		@PathParam("schema")
            final String schema,
            @PathParam("entity")
            final String entity,
            final String body
            ) throws Exception;
	
	/**
	 * @param token
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @param body
	 * @throws Exception
	 */
	@PUT
    @Path("{schema}/{entity}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    Response merge(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		@PathParam("schema")
            final String schema,
            @PathParam("entity")
            final String entity,
            @PathParam("id")
            final String entityId,
            final String body
            ) throws Exception;
	
	/**
	 * @param token
	 * @param schema
	 * @param entity
	 * @param entityId
	 */
	@DELETE
    @Path("{schema}/{entity}/{id}")
    void remove(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		@PathParam("schema")
            final String schema,
            @PathParam("entity")
            final String entity,
            @PathParam("id")
            final String entityId
            );
}
