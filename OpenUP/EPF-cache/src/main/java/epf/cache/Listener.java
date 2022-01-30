/**
 * 
 */
package epf.cache;

import java.net.URI;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.CacheManager;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.cache.internal.event.EntryCreatedListener;
import epf.cache.internal.event.EntryExpiredListener;
import epf.cache.internal.event.EntryRemovedListener;
import epf.cache.internal.event.EntryUpdatedListener;
import epf.naming.Naming;
import epf.util.config.ConfigUtil;
import epf.util.logging.LogManager;
import epf.util.websocket.Client;
import epf.util.websocket.MessageQueue;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class Listener {
	
	/**
	 * 
	 */
	private static transient final Logger LOGGER = LogManager.getLogger(Listener.class.getName());
	
	/**
	 * 
	 */
	private transient MessageQueue events;
	
	/**
	 * 
	 */
	private transient Client client;
	
	/**
	 * 
	 */
	private transient EntryCreatedListener created;
	
	/**
	 * 
	 */
	private transient EntryExpiredListener expired;
	
	/**
	 * 
	 */
	private transient EntryRemovedListener removed;
	
	/**
	 * 
	 */
	private transient EntryUpdatedListener updated;
	
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
			final URI messagingUrl = ConfigUtil.getURI(Naming.Messaging.MESSAGING_URL);
			client = Client.connectToServer(messagingUrl.resolve(Naming.CACHE));
			events = new MessageQueue(client.getSession());
			executor.submit(events);
			created = new EntryCreatedListener(events);
			expired = new EntryExpiredListener(events);
			removed = new EntryRemovedListener(events);
			updated = new EntryUpdatedListener(events);
		}
		catch(Exception ex) {
			LOGGER.throwing(LOGGER.getName(), "postConstruct", ex);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		events.close();
		try {
			client.close();
		} 
		catch (Exception e) {
			LOGGER.throwing(LOGGER.getName(), "preDestroy", e);
		}
	}
	
	/**
	 * @param manager
	 */
	public void registerEvents(final CacheManager manager) {
		final Iterable<String> names = manager.getCacheNames();
		names.forEach(name -> {
			final javax.cache.Cache<String, Object> cache = manager.getCache(name);
			created.register(cache);
			expired.register(cache);
			removed.register(cache);
			updated.register(cache);
		});
	}
	
	/**
	 * @param manager
	 */
	public void deregisterEvents(final CacheManager manager) {
		final Iterable<String> names = manager.getCacheNames();
		names.forEach(name -> {
			final javax.cache.Cache<String, Object> cache = manager.getCache(name);
			created.deregister(cache);
			expired.deregister(cache);
			removed.deregister(cache);
			updated.deregister(cache);
		});
	}
}
