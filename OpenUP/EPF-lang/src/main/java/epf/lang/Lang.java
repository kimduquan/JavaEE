package epf.lang;

import java.io.InputStream;
import org.eclipse.microprofile.health.Readiness;
import epf.lang.schema.ollama.ChatRequest;
import epf.naming.Naming;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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
	Messaging messaging;
	
	/**
	 * 
	 */
	@Inject
	@Readiness
	Cache cache;
	
	/**
	 * 
	 */
	@Inject
	@Readiness
	Graph graph;
	
	/**
	 * @param query
	 * @return
	 * @throws Exception 
	 */
	@Path(Naming.Lang.Internal.GRAPH)
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@RunOnVirtualThread
	public String generateQuery(
			@QueryParam("query") 
			final String query) throws Exception {
		return graph.generateQuery(query);
	}
	
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
	 * @param input
	 */
	@Path(Naming.PERSISTENCE)
	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @RunOnVirtualThread
	public Response persist(final InputStream input) {
		persistence.persist(input);
		return Response.ok().build();
	}
	
	/**
	 * @param id
	 * @param text
	 * @return
	 */
	@Path(Naming.CACHE)
	@PUT
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
	public Response put(
			@HeaderParam("id")
			final String id,
			final String text) {
		final ChatRequest chat = messaging.put(id, text);
		return Response.ok(chat).build();
	}
	
	/**
	 * @param id
	 * @param text
	 */
	@Path(Naming.MESSAGING)
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
    @RunOnVirtualThread
	public Response send(
			@HeaderParam("id")
			final String id, 
			final String text) {
		messaging.send(id, text);
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
