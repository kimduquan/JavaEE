package epf.query.persistence;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @param <K>
 * @param <V>
 */
public interface Loader<K, V> {

	/**
	 * @param tenant
	 * @param key
	 * @return
	 * @throws Exception
	 */
	V load(final K key) throws Exception;
	
	/**
	 * @param tenant
	 * @param keys
	 * @return
	 * @throws Exception
	 */
	default Map<K, V> loadAll(final Iterable<? extends K> keys) throws Exception {
		final Map<K, V> entries = new ConcurrentHashMap<>();
		final Iterator<? extends K> it = keys.iterator();
		while(it.hasNext()) {
			final K key = it.next();
			final V value = load(key);
			if(value != null) {
				entries.put(key, value);
			}
		}
		return entries;
	}
}
