package epf.cache.event;

import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryExpiredListener;
import javax.cache.event.CacheEntryListenerException;
import javax.enterprise.event.Event;

/**
 * @author PC
 *
 */
public class EntryExpiredListener extends EntryListener implements CacheEntryExpiredListener<String, Object> {

	/**
	 * @param event
	 */
	public EntryExpiredListener(final Event<CacheEntryEvent<? extends String, ? extends Object>> event) {
		super(event);
	}

	@Override
	public void onExpired(final Iterable<CacheEntryEvent<? extends String, ? extends Object>> events)
			throws CacheEntryListenerException {
		onEvent(events);
	}
}
