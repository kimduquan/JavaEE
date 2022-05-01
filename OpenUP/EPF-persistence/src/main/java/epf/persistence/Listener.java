package epf.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;

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
	transient Event<EmitterEvent> emitter;
	
	/**
	 * @param event
	 */
	public void postPersist(@Observes final PostPersist event) {
		emitter.fireAsync(new EmitterEvent(event));
	}
	
	/**
	 * @param event
	 */
	public void postRemove(@Observes final PostRemove event) {
		emitter.fireAsync(new EmitterEvent(event));
	}
	
	/**
	 * @param event
	 */
	public void postUpdate(@Observes final PostUpdate event) {
		emitter.fireAsync(new EmitterEvent(event));
	}
}
