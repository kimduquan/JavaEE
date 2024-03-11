package epf.cache.event;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.cache.Cache;
import javax.cache.configuration.FactoryBuilder.SingletonFactory;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListener;
import javax.enterprise.event.Event;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
public abstract class EntryListener implements CacheEntryListener<String, Object> {
	
	/**
	 * 
	 */
	private static transient final Logger LOGGER = LogManager.getLogger(EntryListener.class.getName());

	/**
	 * 
	 */
	private transient final Event<CacheEntryEvent<? extends String, ? extends Object>> event;
	
	/**
	 * 
	 */
	private transient Optional<MutableCacheEntryListenerConfiguration<String, Object>> config = Optional.empty();
	
	/**
	 * 
	 */
	private transient final EntryFilter filter;
	
	/**
	 * @param event
	 */
	protected EntryListener(final Event<CacheEntryEvent<? extends String, ? extends Object>> event) {
		this.event = event;
		filter = new EntryFilter();
	}
	
	/**
	 * @param events
	 */
	protected void onEvent(final Iterable<CacheEntryEvent<? extends String, ? extends Object>> events) {
		events.forEach(event -> {
			try {
				this.event.fire(event);
			}
			catch(Exception ex) {
				LOGGER.log(Level.SEVERE, "add", ex);
			}
		});
	}
	
	/**
	 * @param listener
	 * @param filter
	 * @param cache
	 */
	public void register(final Cache<String, Object> cache) {
		if(!config.isPresent()) {
			final SingletonFactory<CacheEntryListener<String, Object>> factory = new SingletonFactory<CacheEntryListener<String, Object>>(this);
			final SingletonFactory<CacheEntryEventFilter<String, Object>> factory2 = new SingletonFactory<CacheEntryEventFilter<String, Object>>(filter);
			config = Optional.of(new MutableCacheEntryListenerConfiguration<String, Object>(factory, factory2, false, false));
		}
		cache.registerCacheEntryListener(config.get());
	}
	
	/**
	 * @param cache
	 */
	public void deregister(final Cache<String, Object> cache) {
		config.ifPresent(config -> {
			cache.deregisterCacheEntryListener(config);
		});
	}
}
