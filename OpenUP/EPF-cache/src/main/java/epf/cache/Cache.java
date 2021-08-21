/**
 * 
 */
package epf.cache;

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
import epf.cache.persistence.Persistence;
import epf.client.security.Security;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path("cache")
@RolesAllowed(Security.DEFAULT_ROLE)
public class Cache implements epf.client.cache.Cache {

	/**
	 * 
	 */
	@Inject
	private transient Persistence persistence;
	
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

	@Override
	public void forEachEntity(final String entity, final SseEventSink sseEventSink, final Sse sse) {
		final Builder builder = sse.newEventBuilder();
		persistence.forEachEntity(entity, entry -> {
			final OutboundSseEvent event = builder
					.name(entry.getValue().getClass().getName())
					.data(entry.getValue())
					.id(entry.getKey())
					.mediaType(MediaType.APPLICATION_JSON_TYPE)
					.build();
			sseEventSink.send(event);
		});
		sseEventSink.close();
	}
}
