package epf.cache.bundle;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import epf.cache.Cache;

/**
 * @author PC
 *
 */
public class CacheImpl implements Cache {
	
	/**
	 * 
	 */
	private transient final Map<String, Object> cache = new ConcurrentHashMap<>();

	@Override
	public void close() throws IOException {
		cache.clear();
	}

	@Override
	public Object get(final String key) {
		return cache.get(key);
	}

	@Override
	public Map<String, Object> getAll(final Set<String> keys) {
		return cache;
	}

	@Override
	public void put(final String key, Object value) {
		cache.put(key, value);
	}

	@Override
	public void putAll(final Map<String, Object> map) {
		cache.putAll(map);
	}

	@Override
	public boolean remove(final String key) {
		final boolean isContainsKey = cache.containsKey(key);
		if(isContainsKey) {
			cache.remove(key);
		}
		return isContainsKey;
	}

	@Override
	public void removeAll(final Set<String> keys) {
		keys.forEach(cache::remove);
	}

	@Override
	public void removeAll() {
		cache.clear();
	}

}
