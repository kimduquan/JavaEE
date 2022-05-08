package epf.persistence.event;

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
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void send(@ObservesAsync final AsyncEvent event) throws Exception {
		emitter.send(event.getEvent()).toCompletableFuture().get();
	}
}
