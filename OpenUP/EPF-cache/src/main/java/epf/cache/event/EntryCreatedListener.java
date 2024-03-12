package epf.cache.event;

import javax.cache.event.CacheEntryCreatedListener;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryListenerException;
import jakarta.enterprise.event.Event;

/**
 * @author PC
 *
 */
public class EntryCreatedListener extends EntryListener implements CacheEntryCreatedListener<String, Object> {

	/**
	 * @param event
	 */
	public EntryCreatedListener(final Event<CacheEntryEvent<? extends String, ? extends Object>> event) {
		super(event);
	}

	@Override
	public void onCreated(final Iterable<CacheEntryEvent<? extends String, ? extends Object>> events)
			throws CacheEntryListenerException {
		onEvent(events);
	}
}
