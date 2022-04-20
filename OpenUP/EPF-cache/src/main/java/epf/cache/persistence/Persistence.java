package epf.cache.persistence;

import java.net.URI;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.cache.Manager;
import epf.messaging.client.Client;
import epf.messaging.client.Messaging;
import epf.naming.Naming;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import epf.util.websocket.Message;
import epf.util.websocket.MessageQueue;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class Persistence implements HealthCheck {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Persistence.class.getName());
	
	/**
	 * 
	 */
	private transient Cache<String, Object> cache;
	
	/**
	 * 
	 */
	private transient EntityCache entityCache;
	
	/**
	 * 
	 */
	private transient Client client;
	
	/**
	 * 
	 */
	private transient MessageQueue messages;

	/**
	 * 
	 */
	@Inject @Readiness
	private transient Manager manager;
	
	/**
	 * 
	 */
	@Inject
	private transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@PersistenceContext(unitName = "EPF_Cache")
	private transient EntityManager entityManager;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		cache = manager.getCache(Naming.PERSISTENCE);
		entityCache = new EntityCache(cache);
		try {
			final URI messagingUrl = ConfigUtil.getURI(Naming.Messaging.MESSAGING_URL);
			client = Messaging.connectToServer(messagingUrl.resolve(Naming.PERSISTENCE));
			client.onMessage(msg -> {});
			messages = new MessageQueue(client.getSession());
			executor.submit(messages);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[Persistence.postConstruct]", ex);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		try {
			entityCache.close();
			messages.close();
			client.close();
		} 
		catch (Exception e) {
			LOGGER.log(Level.SEVERE, "[Persistence.preDestroy]", e);
		}
	}
	
	/**
	 * @param schema
	 * @param name
	 * @param entityId
	 * @return
	 */
	public Optional<Object> getEntity(
			final String schema,
            final String name,
            final String entityId
            ) {
		return entityCache.getEntity(schema, name, entityId);
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-persistence-cache");
	}
	
	/**
	 * @param event
	 */
	@Incoming(Naming.Persistence.PERSISTENCE_ENTITY_LISTENERS)
	@Transactional
	public void postEvent(final EntityEvent event) {
		if(event != null) {
			entityCache.accept(event);
			accept(event);
			messages.add(new Message(event));
		}
	}
	
	/**
	 * @param event
	 */
	private void accept(final EntityEvent event) {
		if(event instanceof PostUpdate) {
			entityManager.merge(event.getEntity());
		}
		else if(event instanceof PostPersist) {
			entityManager.persist(event.getEntity());
		}
		else if(event instanceof PostRemove) {
			entityManager.remove(entityManager.merge(event.getEntity()));
		}
	}
}
