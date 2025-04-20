package epf.query.persistence;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface Loader<K, V> {

	V load(final K key) throws Exception;
	
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
