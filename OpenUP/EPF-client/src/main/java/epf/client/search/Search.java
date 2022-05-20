package epf.client.search;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.client.schema.EntityId;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.SEARCH)
public interface Search {

	/**
	 *
	 */
	String ENTITY_COUNT = "entity-count";
    
    /**
     * @param text
     * @param firstResult
     * @param maxResults
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response search(
    		@QueryParam("text")
    		final String text, 
    		@QueryParam("first")
    		final Integer firstResult,
            @QueryParam("max")
    		final Integer maxResults);
    
    /**
     * @param client
     * @param text
     * @param firstResult
     * @param maxResults
     * @return
     */
    static List<EntityId> search(
    		final Client client,
    		final String text, 
    		final Integer firstResult,
    		final Integer maxResults) {
    	return client.request(
    			target -> target.queryParam("text", text).queryParam("first", firstResult).queryParam("max", maxResults), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(new GenericType<List<EntityId>>() {});
    }
	
	/**
	 * @param text
	 * @return
	 */
	@HEAD
	Response count(
			@QueryParam("text")
			final String text);
    
    /**
     * @param client
     * @param text
     * @return
     */
    static Integer count(final Client client, final String text) {
    	return Integer.parseInt(client.request(target -> target.queryParam("text", text), req -> req).head().getHeaderString(ENTITY_COUNT));
    }
}
