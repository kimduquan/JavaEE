package epf.shell.query;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.QUERY)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface QueryClient {
	
	/**
	 *
	 */
	String ENTITY = "entity";
	
	/**
	 * 
	 */
	String FIRST = "first";
	/**
	 * 
	 */
	String MAX = "max";
	
	/**
	 *
	 */
	String SORT = "sort";
	
	/**
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @return
	 */
	@GET
    @Path("entity/{schema}/{entity}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    Response getEntity(
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
	 * @param schema
	 * @param entity
	 * @return
	 */
	@HEAD
	@Path("entity/{schema}/{entity}")
    Response countEntity(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		@PathParam("schema")
            final String schema,
            @PathParam("entity")
            final String entity
            );
	
	/**
	 * @param entityIds
	 * @return
	 */
	@PATCH
    @Path(ENTITY)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response fetchEntities(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		final String entityIds);
}
