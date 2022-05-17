package epf.query.search;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.naming.Naming;

/**
 * 
 */
@ApplicationScoped
@Path(Naming.SEARCH)
public class Search implements epf.client.search.Search {

	@Override
	public Response search(
			final UriInfo uriInfo, 
			final String text, 
			final Integer firstResult, 
			final Integer maxResults,
			final SecurityContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}
