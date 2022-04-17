package epf.cache.internal.event;

import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryExpiredListener;
import javax.cache.event.CacheEntryListenerException;
import epf.util.websocket.MessageQueue;

/**
 * @author PC
 *
 */
public class EntryExpiredListener extends EntryListener implements CacheEntryExpiredListener<String, Object> {

	/**
	 * @param messages
	 */
	public EntryExpiredListener(final MessageQueue messages) {
		super(messages);
	}

	@Override
	public void onExpired(final Iterable<CacheEntryEvent<? extends String, ? extends Object>> events)
			throws CacheEntryListenerException {
		events.forEach(entry -> {
			add(entry);
		});
	}
}
