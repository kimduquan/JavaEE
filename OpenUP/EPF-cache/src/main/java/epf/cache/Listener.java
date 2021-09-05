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
import epf.cache.event.EntryCreatedListener;
import epf.cache.event.EntryExpiredListener;
import epf.cache.event.EntryRemovedListener;
import epf.cache.event.EntryUpdatedListener;
import epf.client.messaging.Client;
import epf.client.messaging.Messaging;
import epf.util.config.ConfigUtil;
import epf.util.logging.Logging;
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
	private static transient final Logger LOGGER = Logging.getLogger(Listener.class.getName());
	
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
			final URI messagingUrl = ConfigUtil.getURI(Messaging.MESSAGING_URL);
			client = Messaging.connectToServer(messagingUrl.resolve("cache"));
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
	public void register(final CacheManager manager) {
		final Iterable<String> names = manager.getCacheNames();
		names.forEach(name -> {
			created.register(manager.getCache(name));
			expired.register(manager.getCache(name));
			removed.register(manager.getCache(name));
			updated.register(manager.getCache(name));
		});
	}
	
	/**
	 * @param manager
	 */
	public void deregister(final CacheManager manager) {
		final Iterable<String> names = manager.getCacheNames();
		names.forEach(name -> {
			created.deregister(manager.getCache(name));
			expired.deregister(manager.getCache(name));
			removed.deregister(manager.getCache(name));
			updated.deregister(manager.getCache(name));
		});
	}
}
