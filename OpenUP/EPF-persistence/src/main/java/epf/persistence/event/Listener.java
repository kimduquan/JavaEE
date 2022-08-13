package epf.persistence.event;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.schema.utility.Request;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Listener {
	
	/**
	 * 
	 */
	@Inject
	transient Event<AsyncEvent> emitter;
	
	/**
     * 
     */
    @Inject
    transient Request request;
	
	/**
	 * @param event
	 * @throws Exception
	 */
	public void postPersist(@Observes final PostPersist event) throws Exception {
		event.setSchema(request.getSchema());
		event.setTenant(request.getTenant());
		emitter.fireAsync(new AsyncEvent(event)).toCompletableFuture().get();
	}
	
	/**
	 * @param event
	 */
	public void postRemove(@Observes final PostRemove event) throws Exception {
		event.setSchema(request.getSchema());
		event.setTenant(request.getTenant());
		emitter.fireAsync(new AsyncEvent(event)).toCompletableFuture().get();
	}
	
	/**
	 * @param event
	 */
	public void postUpdate(@Observes final PostUpdate event) throws Exception {
		event.setSchema(request.getSchema());
		event.setTenant(request.getTenant());
		emitter.fireAsync(new AsyncEvent(event)).toCompletableFuture().get();
	}
}
