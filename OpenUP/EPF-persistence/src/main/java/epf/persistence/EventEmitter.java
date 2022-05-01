package epf.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import epf.naming.Naming;
import epf.schema.utility.EntityEvent;

/**
 * 
 */
@ApplicationScoped
public class EventEmitter {

	/**
	 * 
	 */
	@Channel(Naming.Persistence.PERSISTENCE_ENTITY_LISTENERS)
	transient Emitter<EntityEvent> emitter;
	
	/**
	 * @param event
	 */
	public void send(@ObservesAsync final EmitterEvent event) {
		emitter.send(event.getEvent());
	}
}
