package epf.persistence;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import epf.naming.Naming;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostLoad;
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
	private static final Logger LOGGER = LogManager.getLogger(Listener.class.getName());
	
	/**
	 * 
	 */
	@Channel(Naming.Persistence.PERSISTENCE_ENTITY_LISTENERS)
	transient Emitter<EntityEvent> emitter;
	
	/**
	 * 
	 */
	@Channel(Naming.Persistence.PERSISTENCE_ENTITY_LISTENERS_POSTLOAD)
	transient Emitter<PostLoad> postLoadEmitter;
	
	/**
	 * @param event
	 */
	protected void submit(final EntityEvent event) {
		try(Jsonb jsonb = JsonbBuilder.create()){
			jsonb.toJson(event);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, event.toString(), e);
		}
		emitter.send(event);
	}
	
	/**
	 * @param event
	 */
	public void postPersist(@Observes final PostPersist event) {
		submit(event);
	}
	
	/**
	 * @param event
	 */
	public void postRemove(@Observes final PostRemove event) {
		submit(event);
	}
	
	/**
	 * @param event
	 */
	public void postUpdate(@Observes final PostUpdate event) {
		submit(event);
	}
	
	/**
	 * @param event
	 */
	public void postLoad(@Observes final PostLoad event) {
		try(Jsonb jsonb = JsonbBuilder.create()){
			jsonb.toJson(event);
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, event.toString(), e);
		}
		postLoadEmitter.send(event);
	}
}
