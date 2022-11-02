package epf.shell.search;

import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.SEARCH)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface SearchClient {
	
	/**
	 * @param text
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    Response search(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		@QueryParam("text")
    		final String text, 
    		@QueryParam("first")
    		final Integer firstResult,
            @QueryParam("max")
    		final Integer maxResults);
	
	/**
	 * @param text
	 * @return
	 */
	@HEAD
	Response count(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
			@QueryParam("text")
			final String text);
}
