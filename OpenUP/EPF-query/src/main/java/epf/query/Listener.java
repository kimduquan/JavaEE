package epf.query;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.naming.Naming;
import epf.query.internal.EntityCache;
import epf.query.internal.QueryCache;
import epf.query.internal.messaging.Messaging;
import epf.query.internal.persistence.PersistenceCache;
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
	
	/**
	 * 
	 */
	@Inject @Readiness
	private transient Messaging messaging;
	
	/**
	 * 
	 */
	@Inject
	private transient ManagedExecutor executor;

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
			LOGGER.info("[Listener.postEvent]" + event.toString());
			executor.supplyAsync(() -> {
				accept(event);
				return null;
			}).whenComplete((res, ex) -> {
				if(ex != null) {
					LOGGER.log(Level.SEVERE, "[Listener.accept]", ex);
				}
				else {
					LOGGER.info("[Listener.accept]" + event.toString());
				}
			});
		}
	}
	
	private void accept(final EntityEvent event) {
		cache.accept(event);
		entityCache.accept(event);
		queryCache.accept(event);
		messaging.accept(event);
	}
}
