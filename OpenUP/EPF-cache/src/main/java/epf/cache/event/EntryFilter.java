/**
 * 
 */
package epf.cache.event;

import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListenerException;

/**
 * @author PC
 *
 */
public class EntryFilter implements CacheEntryEventFilter<String, Object> {

	@Override
	public boolean evaluate(final CacheEntryEvent<? extends String, ? extends Object> event)
			throws CacheEntryListenerException {
		return true;
	}
}
