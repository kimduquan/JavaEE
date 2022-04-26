package epf.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import epf.naming.Naming;
import epf.schema.utility.EntityEvent;
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
	@Channel(Naming.Persistence.PERSISTENCE_ENTITY_LISTENERS)
	transient Emitter<EntityEvent> emitter;
	
	/**
	 * @param event
	 */
	public void postPersist(@Observes final PostPersist event) {
		emitter.send(event);
	}
	
	/**
	 * @param event
	 */
	public void postRemove(@Observes final PostRemove event) {
		emitter.send(event);
	}
	
	/**
	 * @param event
	 */
	public void postUpdate(@Observes final PostUpdate event) {
		emitter.send(event);
	}
}
