package epf.lang;

import java.io.InputStream;
import java.util.List;
import org.eclipse.microprofile.health.Readiness;
import dev.langchain4j.data.segment.TextSegment;
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
	 * @param query
	 * @return
	 */
	@Path(Naming.PERSISTENCE)
	@GET
	@Produces(MediaType.TEXT_PLAIN)
    @RunOnVirtualThread
	public String executeQuery(
			@QueryParam("query") 
			final String query) {
		final List<TextSegment> segments = persistence.executeQuery(query);
		return messaging.injectPrompt(query, segments);
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
		final Link executeQueryLink = Link.fromUriBuilder(UriBuilder.fromPath(Naming.PERSISTENCE).queryParam("query", text)).rel(Naming.LANG).type(HttpMethod.GET).title("#1").build();
		final Link putLink = Link.fromUriBuilder(UriBuilder.fromPath(Naming.CACHE).queryParam("id", id)).rel(Naming.LANG).type(HttpMethod.PUT).title("#2").build();
		final Link ollamaLink = Link.fromPath("api/chat").rel(Naming.Lang.Internal.OLLAMA).type(HttpMethod.POST).param("volatile", id).title("#3").build();
		final Link streamLink = Link.fromPath("messaging/lang").rel(Naming.GATEWAY).type("ws").param("volatile", id).title("#4").build();
		return Response.ok().links(executeQueryLink, putLink, ollamaLink, streamLink).build();
	}
}
