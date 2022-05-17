package epf.client.search;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.SEARCH)
public interface Search {


    
    /**
     * @param uriInfo
     * @param text
     * @param firstResult
     * @param maxResults
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response search(
    		@Context
    		final UriInfo uriInfo,
    		@QueryParam("text")
    		final String text, 
    		@QueryParam("first")
    		final Integer firstResult,
            @QueryParam("max")
    		final Integer maxResults,
            @Context
            final SecurityContext context);
    
    /**
     * @param client
     * @param text
     * @param firstResult
     * @param maxResults
     * @return
     */
    static List<SearchEntity> search(
    		final Client client,
    		final String text, 
    		final Integer firstResult,
    		final Integer maxResults) {
    	return client.request(
    			target -> target.queryParam("text", text).queryParam("first", firstResult).queryParam("max", maxResults), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(new GenericType<List<SearchEntity>>() {});
    }
}
