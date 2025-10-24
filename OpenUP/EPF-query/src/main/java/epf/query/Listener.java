package epf.query;

import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.naming.Naming;
import epf.query.cache.EntityCache;
import epf.query.cache.QueryCache;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.util.logging.LogManager;

@ApplicationScoped
public class Listener {
	
	private transient static final Logger LOGGER = LogManager.getLogger(Listener.class.getName());
	
	@Inject
	transient EntityCache entityCache;
	
	@Inject
	transient QueryCache queryCache;
	
	@Incoming(Naming.Persistence.ENTITY_LISTENERS)
	public void postEvent(final EntityEvent event) throws Exception {
		if(event != null) {
			LOGGER.info("[Listener.postEvent]" + event.toString());
			if(event instanceof PostRemove || event instanceof PostUpdate) {
				entityCache.clearEntity(event.getOrganization(), event.getSchema(), event.getName(), event.getId());
			}
			if(event instanceof PostPersist || event instanceof PostRemove) {
				entityCache.clearEntityCount(event.getOrganization(), event.getSchema(), event.getName());
			}
			queryCache.clearQueryCount(event.getOrganization(), event.getSchema());
			queryCache.clearQuery(event.getOrganization(), event.getSchema());
			LOGGER.info("[Listener.accept]" + event.toString());
		}
	}
}
