/**
 * 
 */
package epf.service.cache;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import epf.schema.PostPersist;
import epf.schema.PostRemove;
import epf.schema.PostUpdate;
import epf.service.messaging.Messaging;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Cache {
	
	/**
	 * 
	 */
	@Inject
	private transient Messaging messaging;
	
	/**
	 * @param event
	 */
	public void postPersist(@Observes final PostPersist event) {
		messaging.forEach("cache", session -> session.getAsyncRemote().sendObject(event));
	}
	
	/**
	 * @param event
	 */
	public void postRemove(@Observes final PostRemove event) {
		messaging.forEach("cache", session -> session.getAsyncRemote().sendObject(event));
	}
	
	/**
	 * @param event
	 */
	public void postUpdate(@Observes final PostUpdate event) {
		messaging.forEach("cache", session -> session.getAsyncRemote().sendObject(event));
	}
}
