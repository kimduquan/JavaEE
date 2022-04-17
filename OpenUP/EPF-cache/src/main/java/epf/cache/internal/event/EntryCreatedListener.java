package epf.cache.internal.event;

import javax.cache.event.CacheEntryCreatedListener;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryListenerException;
import epf.util.websocket.MessageQueue;

/**
 * @author PC
 *
 */
public class EntryCreatedListener extends EntryListener implements CacheEntryCreatedListener<String, Object> {

	/**
	 * @param messages
	 */
	public EntryCreatedListener(final MessageQueue messages) {
		super(messages);
	}

	@Override
	public void onCreated(final Iterable<CacheEntryEvent<? extends String, ? extends Object>> events)
			throws CacheEntryListenerException {
		events.forEach(entry -> {
			add(entry);
		});
	}
}
