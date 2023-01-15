package epf.cache.internal.event;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.Optional;
import java.util.logging.Logger;
import javax.cache.Cache;
import javax.cache.configuration.FactoryBuilder.SingletonFactory;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListener;
import epf.util.logging.LogManager;
import epf.util.websocket.Message;
import epf.util.websocket.MessageQueue;

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
	private transient final MessageQueue messages;
	
	/**
	 * 
	 */
	private transient Optional<MutableCacheEntryListenerConfiguration<String, Object>> config = Optional.empty();
	
	/**
	 * 
	 */
	private transient final EntryFilter filter;
	
	/**
	 * @param messages
	 */
	protected EntryListener(final MessageQueue messages) {
		this.messages = messages;
		filter = new EntryFilter();
	}
	
	/**
	 * @param event
	 */
	protected void add(final CacheEntryEvent<? extends String, ? extends Object> event) {
		try(Jsonb jsonb = JsonbBuilder.create()){
			messages.add(new Message(jsonb.toJson(event)));
		} 
		catch (Exception e) {
			LOGGER.throwing(LOGGER.getName(), "add", e);
		}
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
