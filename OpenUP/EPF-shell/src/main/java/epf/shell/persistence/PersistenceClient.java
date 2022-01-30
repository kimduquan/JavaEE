package epf.shell.persistence;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    void merge(
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
	
	/**
	 * @param token
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @return
	 */
	@POST
    @Path("{schema}/{entity}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response find(
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
