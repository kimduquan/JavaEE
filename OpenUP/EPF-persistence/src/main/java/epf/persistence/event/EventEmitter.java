package epf.persistence.event;

import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import epf.naming.Naming;
import epf.schema.utility.EntityEvent;
import epf.util.logging.LogManager;

/**
 * 
 */
@ApplicationScoped
@Readiness
public class EventEmitter implements HealthCheck {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(EventEmitter.class.getName());

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
		emitter.send(event.getEvent()).whenComplete((v, e) -> {
			if(e != null) {
				LOGGER.log(Level.SEVERE, "[EventEmitter.send]" + event.getEvent().toString(), e);
			}
			else {
				LOGGER.info("[EventEmitter.send]" + event.getEvent().toString());
			}
		});
	}

	@Override
	public HealthCheckResponse call() {
		if(emitter.isCancelled()) {
			return HealthCheckResponse.down("epf-persistence-event");
		}
		return HealthCheckResponse.up("epf-persistence-event");
	}
}
