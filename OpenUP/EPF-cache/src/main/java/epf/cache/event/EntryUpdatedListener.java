package epf.cache.event;

import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryUpdatedListener;
import jakarta.enterprise.event.Event;

/**
 * @author PC
 *
 */
public class EntryUpdatedListener extends EntryListener implements CacheEntryUpdatedListener<String, Object> {

	/**
	 * @param event
	 */
	public EntryUpdatedListener(final Event<CacheEntryEvent<? extends String, ? extends Object>> event) {
		super(event);
	}

	@Override
	public void onUpdated(final Iterable<CacheEntryEvent<? extends String, ? extends Object>> events)
			throws CacheEntryListenerException {
		onEvent(events);
	}
}
