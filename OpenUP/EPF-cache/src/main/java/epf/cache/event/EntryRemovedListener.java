package epf.cache.event;

import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryRemovedListener;
import jakarta.enterprise.event.Event;

/**
 * @author PC
 *
 */
public class EntryRemovedListener extends EntryListener implements CacheEntryRemovedListener<String, Object> {

	/**
	 * @param event
	 */
	public EntryRemovedListener(final Event<CacheEntryEvent<? extends String, ? extends Object>> event) {
		super(event);
	}

	@Override
	public void onRemoved(final Iterable<CacheEntryEvent<? extends String, ? extends Object>> events)
			throws CacheEntryListenerException {
		onEvent(events);
	}
}
