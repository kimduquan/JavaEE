/**
 * 
 */
package epf.cache;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.DeploymentException;
import javax.ws.rs.Path;
import epf.client.messaging.Client;
import epf.client.messaging.Messaging;

/**
 * @author PC
 *
 */
@Path("cache")
@ApplicationScoped
public class Manager {
	
	/**
	 * 
	 */
	private transient final Map<String, Cache<Object, Object>> caches = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Map<String, Client> clients = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@Inject
	private transient Logger logger;

	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		final MutableConfiguration<Object, Object> config = new MutableConfiguration<>();
		final String cacheName = "persistence";
		final Cache<Object, Object> cache = Caching.getCachingProvider().getCacheManager().createCache(cacheName, config);
		caches.put(cacheName, cache);
		try {
			final URI messagingUrl = new URI(System.getenv("epf.messaging.url"));
			final Client client = Messaging.connectToServer(messagingUrl.resolve(cacheName));
			clients.put(cacheName, client);
		}
		catch (URISyntaxException | DeploymentException | IOException e) {
			logger.throwing(getClass().getName(), "postConstruct", e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		clients.forEach((name, client) -> {
			try {
				client.close();
			} 
			catch (Exception e) {
				logger.throwing(Client.class.getName(), "close", e);
			}
		});
		caches.forEach((name, cache) -> cache.close());
	}
}
