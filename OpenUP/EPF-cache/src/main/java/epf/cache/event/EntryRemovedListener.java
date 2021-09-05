/**
 * 
 */
package epf.cache.event;

import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryRemovedListener;
import epf.util.websocket.MessageQueue;

/**
 * @author PC
 *
 */
public class EntryRemovedListener extends EntryListener implements CacheEntryRemovedListener<String, Object> {

	/**
	 * @param messages
	 */
	public EntryRemovedListener(final MessageQueue messages) {
		super(messages);
	}

	@Override
	public void onRemoved(final Iterable<CacheEntryEvent<? extends String, ? extends Object>> events)
			throws CacheEntryListenerException {
		events.forEach(entry -> {
			add(entry);
		});
	}
}
