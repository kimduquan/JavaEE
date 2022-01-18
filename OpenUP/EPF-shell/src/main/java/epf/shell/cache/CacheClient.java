package epf.shell.cache;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.InboundSseEvent;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.reactivestreams.Publisher;
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
	 * @param token
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @return
	 */
	@GET
    @Path("persistence/{schema}/{entity}/{id}")
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
	 * @param token
	 * @param schema
	 * @param entity
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	@GET
    @Path("persistence/{schema}/{entity}")
	@Produces(MediaType.APPLICATION_JSON)
    Response getEntities(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		@PathParam("schema")
            final String schema,
            @PathParam("entity")
            final String entity,
            @QueryParam("firstResult")
            final Integer firstResult,
            @QueryParam("maxResults")
    		final Integer maxResults
            );
	
	/**
	 * @param token
	 * @param schema
	 * @param entity
	 * @return
	 */
	@GET
	@Path("persistence/{schema}")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	Publisher<InboundSseEvent> forEachEntity(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
			@PathParam("schema")
            final String schema,
			@QueryParam("entity")
			final String entity);
	
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
	
	/**
	 * @param id
	 * @return
	 */
	@GET
    @Path("net/url")
	@Produces(MediaType.TEXT_PLAIN)
	String getUrl(
			@QueryParam("id")
			final String id);
}
