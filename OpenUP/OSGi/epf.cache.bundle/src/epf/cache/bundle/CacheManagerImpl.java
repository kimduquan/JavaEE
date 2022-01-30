package epf.cache.bundle;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import epf.cache.Cache;
import epf.cache.CacheManager;

/**
 * @author PC
 *
 */
public class CacheManagerImpl implements CacheManager {
	
	/**
	 * 
	 */
	private transient final Map<String, Cache> caches = new ConcurrentHashMap<>();

	@Override
	public void close() throws IOException {
		for(Cache cache : caches.values()) {
			cache.close();
		}
	}

	@Override
	public Cache createCache(final String cacheName) {
		return caches.put(cacheName, new CacheImpl());
	}

	@Override
	public Cache getCache(final String cacheName) {
		return caches.get(cacheName);
	}

	@Override
	public Iterable<String> getCacheNames() {
		return caches.keySet();
	}

	@Override
	public void destroyCache(final String cacheName) {
		try {
			caches.remove(cacheName).close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
