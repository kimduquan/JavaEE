package epf.lang;

import java.io.InputStream;
import java.util.List;
import dev.langchain4j.data.segment.TextSegment;
import epf.naming.Naming;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
	 * @param input
	 */
	@Path("persistence")
	@POST
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public Response persist(final InputStream input) {
		persistence.persist(input);
		return Response.ok().build();
	}

	/**
	 * @param model
	 * @param query
	 * @return
	 */
	@Path("persistence/{model}")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String executeQuery(
			@PathParam(Naming.Lang.Internal.MODEL)
			final String model, 
			@QueryParam("query") 
			final String query) {
		final List<TextSegment> segments = persistence.executeQuery(query);
		return messaging.injectPrompt(model, query, segments);
	}
	
	/**
	 * @param id
	 * @param text
	 */
	@Path("messaging")
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	public Response send(
			@QueryParam("id")
			final String id, 
			final String text) {
		messaging.send(id, text);
		return Response.ok().build();
	}
}
