package epf.query.client;

import java.util.List;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;

@Path(Naming.Query.SEARCH)
public interface Search {
	
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
	
	@HEAD
	Response count(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
			@QueryParam(Naming.Query.Client.TEXT)
			final String text);
    
    static Integer count(final Client client, final String text) {
    	return Integer.parseInt(client.request(target -> target.path(Naming.Query.SEARCH).queryParam(Naming.Query.Client.TEXT, text), req -> req).head().getHeaderString(Naming.Query.Client.ENTITY_COUNT));
    }
}
