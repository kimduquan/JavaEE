package epf.query;

import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.naming.Naming;
import epf.query.internal.EntityCache;
import epf.query.internal.QueryCache;
import epf.query.persistence.EntityPersistence;
import epf.schema.utility.EntityEvent;
import epf.util.logging.LogManager;
import io.smallrye.reactive.messaging.annotations.Blocking;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class Listener implements HealthCheck {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(Listener.class.getName());
	
	/**
	 * 
	 */
	@Inject @Readiness
	transient EntityCache entityCache;
	
	/**
	 * 
	 */
	@Inject @Readiness
	transient QueryCache queryCache;
	
	/**
	 * 
	 */
	@Inject @Readiness
	transient EntityPersistence persistence;

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-query-listener");
	}
	
	/**
	 * @param event
	 */
	@Incoming(Naming.Persistence.PERSISTENCE_ENTITY_LISTENERS)
	@Blocking
	public void postEvent(final EntityEvent event) {
		if(event != null) {
			LOGGER.info("[Listener.postEvent]" + event.toString());
			accept(event);
			LOGGER.info("[Listener.accept]" + event.toString());
		}
	}
	
	private void accept(final EntityEvent event) {
		persistence.accept(event);
		entityCache.accept(event);
		queryCache.accept(event);
	}
}
