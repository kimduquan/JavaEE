package epf.query.client;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.Query.SEARCH)
public interface Search {
	
	/**
     * @param tenant
     * @param text
     * @param firstResult
     * @param maxResults
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response search(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
    		@QueryParam(Naming.Query.Client.TEXT)
    		final String text, 
    		@QueryParam(Naming.Query.Client.FIRST)
    		final Integer firstResult,
            @QueryParam(Naming.Query.Client.MAX)
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
    			target -> target.path(Naming.Query.SEARCH).queryParam(Naming.Query.Client.TEXT, text).queryParam(Naming.Query.Client.FIRST, firstResult).queryParam(Naming.Query.Client.MAX, maxResults), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(new GenericType<List<EntityId>>() {});
    }
	
	/**
	 * @param tenant
	 * @param text
	 * @return
	 */
	@HEAD
	Response count(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
			@QueryParam(Naming.Query.Client.TEXT)
			final String text);
    
    /**
     * @param client
     * @param text
     * @return
     */
    static Integer count(final Client client, final String text) {
    	return Integer.parseInt(client.request(target -> target.path(Naming.Query.SEARCH).queryParam(Naming.Query.Client.TEXT, text), req -> req).head().getHeaderString(Naming.Query.Client.ENTITY_COUNT));
    }
}
