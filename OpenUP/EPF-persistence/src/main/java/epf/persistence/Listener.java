package epf.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import epf.naming.Naming;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Listener {
	
	/**
	 *
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(Listener.class.getName());
	
	/**
	 * 
	 */
	@Channel(Naming.Persistence.PERSISTENCE_ENTITY_LISTENERS)
	transient Emitter<EntityEvent> emitter;
	
	/**
	 * @param event
	 */
	public void postPersist(@Observes final PostPersist event) {
		try {
			emitter.send(event);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[Listener.postPersist]", ex);
		}
	}
	
	/**
	 * @param event
	 */
	public void postRemove(@Observes final PostRemove event) {
		try {
			emitter.send(event);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[Listener.postRemove]", ex);
		}
	}
	
	/**
	 * @param event
	 */
	public void postUpdate(@Observes final PostUpdate event) {
		try {
			emitter.send(event);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[Listener.postUpdate]", ex);
		}
	}
}
