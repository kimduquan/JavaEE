/**
 * 
 */
package epf.cache;

import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.security.PermitAll;
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
import org.eclipse.microprofile.health.Readiness;
import epf.cache.persistence.Persistence;
import epf.cache.security.Security;
import epf.net.schema.Net;
import epf.net.schema.URL;
import epf.security.schema.Token;
import epf.util.concurrent.ObjectQueue;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.CACHE)
public class Cache implements epf.client.cache.Cache {
	
	/**
	 * 
	 */
	@Inject @Readiness
	private transient Security security;

	/**
	 * 
	 */
	@Inject @Readiness
	private transient Persistence persistence;
	
	/**
	 * 
	 */
	@Inject
	private transient ManagedExecutor executor;
	
	@Override
    public Response getEntity(
    		final String schema,
            final String name,
            final String entityId
            ) {
		final Optional<Object> entity = persistence.getEntity(name, entityId);
		return Response.ok(entity.orElseThrow(NotFoundException::new)).build();
	}

	@PermitAll
	@Override
	public void forEachEntity(final String schema, final String entity, final SseEventSink sseEventSink, final Sse sse) {
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
	public Response getEntities(final String schema, final String name, final Integer firstResult, final Integer maxResults) {
		final List<Entry<String,Object>> entities = persistence.getEntities(name);
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

	@Override
	public String getUrl(final String id) {
		final Optional<Object> entity = persistence.getEntity(Net.URL, id);
		entity.orElseThrow(NotFoundException::new);
		if(entity.get() instanceof URL) {
			final URL url = (URL) entity.get();
			return url.getString();
		}
		throw new NotFoundException();
	}
}
