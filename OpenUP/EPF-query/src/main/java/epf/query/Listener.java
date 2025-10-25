package epf.query;

import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.naming.Naming;
import epf.query.cache.PersistenceCache;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.util.logging.LogManager;
import io.smallrye.common.annotation.RunOnVirtualThread;

@ApplicationScoped
public class Listener {
	
	private transient static final Logger LOGGER = LogManager.getLogger(Listener.class.getName());
	
	@Inject
	transient PersistenceCache persistenceCache;
	
	@Incoming(Naming.Persistence.PERSISTENCE_EVENT)
	@RunOnVirtualThread
	public void postEvent(final EntityEvent event) throws Exception {
		LOGGER.info("[Listener.postEvent]" + event.toString());
		if(event instanceof PostRemove || event instanceof PostUpdate) {
			persistenceCache.clearEntity(event.getOrganization(), event.getSchema(), event.getName(), event.getId());
		}
		if(event instanceof PostPersist || event instanceof PostRemove) {
			persistenceCache.clearEntityCount(event.getOrganization(), event.getSchema(), event.getName());
		}
	}
}
