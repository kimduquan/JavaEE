package epf.search;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.naming.Naming;

@Path(Naming.SEARCH)
@ApplicationScoped
public class Search implements epf.client.search.Search {

	@Override
	public Response search(
			final UriInfo uriInfo, 
			final String text, 
			final Integer firstResult, 
			final Integer maxResults,
			final SecurityContext context) {
		return null;
	}
	
}