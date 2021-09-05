/**
 * 
 */
package epf.cache.event;

import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryUpdatedListener;
import epf.util.websocket.MessageQueue;

/**
 * @author PC
 *
 */
public class EntryUpdatedListener extends EntryListener implements CacheEntryUpdatedListener<String, Object> {

	/**
	 * @param messages
	 */
	public EntryUpdatedListener(final MessageQueue messages) {
		super(messages);
	}

	@Override
	public void onUpdated(final Iterable<CacheEntryEvent<? extends String, ? extends Object>> events)
			throws CacheEntryListenerException {
		events.forEach(entry -> {
			add(entry);
		});
	}
}
