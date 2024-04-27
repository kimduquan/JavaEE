package epf.shell.query;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.QUERY)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface SearchClient {
	
	/**
	 * @param text
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	@GET
	@Path(Naming.Query.SEARCH)
    @Produces(MediaType.APPLICATION_JSON)
    Response search(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		@QueryParam(Naming.Query.Client.TEXT)
    		final String text, 
    		@QueryParam(Naming.Query.Client.FIRST)
    		final Integer firstResult,
            @QueryParam(Naming.Query.Client.MAX)
    		final Integer maxResults);
	
	/**
	 * @param text
	 * @return
	 */
	@HEAD
	@Path(Naming.Query.SEARCH)
	Response count(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
			@QueryParam(Naming.Query.Client.TEXT)
			final String text);
}
