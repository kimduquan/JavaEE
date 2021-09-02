/**
 * 
 */
package epf.cache;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.OutboundSseEvent.Builder;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.cache.persistence.Persistence;
import epf.cache.security.Security;
import epf.client.security.Token;
import epf.util.concurrent.ObjectQueue;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path("cache")
@RolesAllowed(epf.client.security.Security.DEFAULT_ROLE)
public class Cache implements epf.client.cache.Cache {
	
	/**
	 * 
	 */
	@Inject
	private transient Security security;

	/**
	 * 
	 */
	@Inject
	private transient Persistence persistence;
	
	/**
	 * 
	 */
	@Inject
	private transient ManagedExecutor executor;
	
	/**
	 *
	 */
	@Override
    public Response getEntity(
            final String name,
            final String entityId
            ) {
		final Object entity = persistence.getEntity(name, entityId);
		if(entity != null) {
			return Response.ok(entity).build();
		}
		throw new NotFoundException();
	}

	@PermitAll
	@Override
	public void forEachEntity(final String entity, final SseEventSink sseEventSink, final Sse sse) {
		final Builder builder = sse.newEventBuilder();
		final ObjectQueue<Entry<String, Object>> queue = new ObjectQueue<Entry<String, Object>>() {
			@Override
			public void accept(final Entry<String, Object> entry) {
				final OutboundSseEvent event = builder
						.name(entry.getValue().getClass().getName())
						.data(entry.getValue())
						.id(entry.getKey())
						.mediaType(MediaType.APPLICATION_JSON_TYPE)
						.build();
				sseEventSink.send(event);
			}};
		executor.submit(() -> persistence.forEachEntity(entity, queue));
		queue.run();
		sseEventSink.close();
	}

	@Override
	public Response getEntities(final String name, final Integer firstResult, final Integer maxResults) {
		List<Entry<String,Object>> entities = persistence.getEntities(name);
		if(entities != null) {
			Stream<Entry<String,Object>> stream = entities.stream();
			if(firstResult != null) {
				stream = stream.skip(firstResult);
			}
			if(maxResults != null) {
				stream = stream.limit(maxResults);
			}
			final List<Object> objects = stream.map(entry -> entry.getValue()).collect(Collectors.toList());
			return Response.ok(objects).build();
		}
		throw new NotFoundException();
	}

	@Override
	public Token getToken(final String tokenId) {
		return security.getToken(tokenId);
	}
}
