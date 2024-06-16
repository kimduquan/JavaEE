package epf.lang;

import java.io.InputStream;
import java.util.List;
import dev.langchain4j.data.segment.TextSegment;
import epf.lang.schema.ollama.ChatRequest;
import epf.lang.schema.ollama.Message;
import epf.lang.schema.ollama.Role;
import epf.naming.Naming;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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
	Persistence persistence;
	
	/**
	 * 
	 */
	@Inject
	Messaging messaging;
	
	/**
	 * 
	 */
	@Inject
	Cache cache;
	
	/**
	 * @param input
	 */
	@Path(Naming.PERSISTENCE)
	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
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
	public Response put(
			@QueryParam("id")
			final String id,
			final String text) {
		final ChatRequest chat = cache.get(id);
		final Message message = new Message();
		message.setRole(Role.user);
		message.setContent(text);
		chat.getMessages().add(message);
		cache.put(id, chat);
		return Response.ok(chat).build();
	}
	
	/**
	 * @param id
	 * @param text
	 */
	@Path(Naming.MESSAGING)
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public Response send(
			@QueryParam("id")
			final String id, 
			final String text) {
		final Link executeQuery = Link.fromUriBuilder(UriBuilder.fromPath(Naming.PERSISTENCE).queryParam("query", text)).rel("_self").type(HttpMethod.GET).title("#1").build();
		final Link put = Link.fromUriBuilder(UriBuilder.fromPath(Naming.CACHE).queryParam("id", id)).rel("_self").type(HttpMethod.PUT).title("#2").build();
		final Link ollamaLink = Link.fromPath("api/chat").rel(Naming.Lang.Internal.OLLAMA).type(HttpMethod.POST).param("volatile", id).title("#3").build();
		return Response.ok().links(executeQuery, put, ollamaLink).build();
	}
}
