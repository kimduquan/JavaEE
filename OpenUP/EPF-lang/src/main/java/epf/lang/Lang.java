package epf.lang;

import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

/**
 * 
 */
@ApplicationScoped
@Path(Naming.LANG)
public class Lang {
	
	/**
	 * 
	 */
	@Inject
	@Readiness
	Persistence persistence;
	
	/**
	 * 
	 */
	@Inject
	@Readiness
	Cache cache;
	
	/**
	 * @param query
	 * @return
	 */
	@Path(Naming.Lang.Internal.GRAPH)
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@RunOnVirtualThread
	public Response executeQuery(final String query) {
		return Response.ok().build();
	}
	
	/**
	 * @param id
	 * @param text
	 * @return
	 */
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
    @RunOnVirtualThread
	public Response chat(
			@HeaderParam("id")
			final String id, 
			final String text) {
		final Link generateQueryLink = Link.fromUriBuilder(UriBuilder.fromPath(Naming.Lang.Internal.GRAPH).queryParam("query", text)).rel(Naming.LANG).type(HttpMethod.GET).title("#1").build();
		final Link executeQueryLink = Link.fromUriBuilder(UriBuilder.fromPath(Naming.Lang.Internal.GRAPH)).rel(Naming.LANG).type(HttpMethod.POST).title("#2").build();
		return Response.ok().links(generateQueryLink, executeQueryLink).build();
	}
}
