package epf.query;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.naming.Naming;
import epf.query.cache.EntityCache;
import epf.query.cache.QueryCache;
import epf.query.persistence.PersistenceCache;
import epf.schema.utility.EntityEvent;
import epf.util.logging.LogManager;

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
	private static final Logger LOGGER = LogManager.getLogger(Listener.class.getName());
	
	/**
	 * 
	 */
	@Inject @Readiness
	private transient EntityCache entityCache;
	
	/**
	 * 
	 */
	@Inject @Readiness
	private transient QueryCache queryCache;
	
	/**
	 * 
	 */
	@Inject @Readiness
	private transient PersistenceCache cache;

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-persistence-listener");
	}
	
	/**
	 * @param event
	 */
	@Incoming(Naming.Persistence.PERSISTENCE_ENTITY_LISTENERS)
	public void postEvent(final EntityEvent event) {
		if(event != null) {
			try {
				cache.accept(event);
				entityCache.accept(event);
				queryCache.accept(event);
			}
			catch(Exception ex) {
				LOGGER.log(Level.SEVERE, "[Listener.postEvent]", ex);
			}
		}
	}
}
