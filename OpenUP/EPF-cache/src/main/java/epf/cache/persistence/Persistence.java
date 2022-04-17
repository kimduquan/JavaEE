package epf.cache.persistence;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
import epf.util.concurrent.ObjectQueue;
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
	@PostConstruct
	protected void postConstruct() {
		cache = manager.getCache(Naming.PERSISTENCE);
		entityCache = new EntityCache(cache);
		executor.submit(entityCache);
		try {
			final URI messagingUrl = ConfigUtil.getURI(Naming.Messaging.MESSAGING_URL);
			client = Messaging.connectToServer(messagingUrl.resolve(Naming.PERSISTENCE));
			client.onMessage(msg -> {});
			messages = new MessageQueue(client.getSession());
			executor.submit(messages);
		}
		catch(Exception ex) {
			LOGGER.throwing(getClass().getName(), "postConstruct", ex);
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
			LOGGER.throwing(getClass().getName(), "preDestroy", e);
		}
	}
	
	/**
	 * @param name
	 * @param entityId
	 * @return
	 */
	public Optional<Object> getEntity(
            final String name,
            final String entityId
            ) {
		return entityCache.getEntity(name, entityId);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public List<Entry<String, Object>> getEntities(final String name){
		return entityCache.getEntities(name);
	}
	
	/**
	 * @param name
	 * @return
	 */
	public void forEachEntity(final String name, final ObjectQueue<Entry<String, Object>> queue) {
		entityCache.forEachEntity(name, queue);
		queue.close();
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-persistence-cache");
	}
	
	@Incoming(Naming.Persistence.PERSISTENCE_ENTITY_LISTENERS)
	public void postEvent(final EntityEvent event) {
		if(event != null) {
			entityCache.add(event);
			messages.add(new Message(event));
		}
	}
}
