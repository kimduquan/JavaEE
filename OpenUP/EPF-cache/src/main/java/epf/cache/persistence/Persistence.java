/**
 * 
 */
package epf.cache.persistence;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.DeploymentException;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.cache.Manager;
import epf.client.messaging.Client;
import epf.client.messaging.Messaging;
import epf.schema.EntityEvent;
import epf.schema.PostLoad;
import epf.util.concurrent.ObjectQueue;
import epf.util.config.ConfigUtil;
import epf.util.logging.Logging;

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
	private static final Logger LOGGER = Logging.getLogger(Persistence.class.getName());
	
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
	private transient Client postLoadClient;

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
		try {
			cache = manager.getCache("persistence");
			entityCache = new EntityCache(cache);
			executor.submit(entityCache);
			final URI messagingUrl = ConfigUtil.getURI(Messaging.MESSAGING_URL);
			client = Messaging.connectToServer(messagingUrl.resolve("persistence"));
			client.onMessage(message -> {
				if(message instanceof EntityEvent) {
					entityCache.add((EntityEvent) message);
				}
			});
			postLoadClient = Messaging.connectToServer(messagingUrl.resolve("persistence/post-load"));
			postLoadClient.onMessage(message -> {
				if(message instanceof PostLoad) {
					entityCache.add((PostLoad) message);
				}
			});
		} 
		catch (URISyntaxException | DeploymentException | IOException e) {
			LOGGER.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		try {
			postLoadClient.close();
			client.close();
			entityCache.close();
		} 
		catch (Exception e) {
			LOGGER.throwing(client.getClass().getName(), "close", e);
		}
	}
	
	/**
	 * @param name
	 * @param entityId
	 * @return
	 */
	public Object getEntity(
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
	
	@Incoming("persistence")
	public void handleEvent(final EntityEvent event) {
		entityCache.add(event);
	}
}
