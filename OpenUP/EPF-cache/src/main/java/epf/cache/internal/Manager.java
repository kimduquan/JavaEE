package epf.cache.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.cache.Cache;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;

/**
 * @author PC
 *
 */
public class Manager {
	
	/**
	 * 
	 */
	private transient final Map<String, Cache<String, Object>> caches = new ConcurrentHashMap<>();

	/**
	 * @param name
	 * @return
	 */
	public Cache<String, Object> getCache(final String name) {
		return caches.get(name);
	}
	
	/**
	 * 
	 */
	public void close() {
		caches.forEach((name, cache) -> {
			cache.close();
		});
	}
	
	/**
	 * @param name
	 * @return
	 */
	public Cache<String, Object> createCache(final String name) {
		return caches.computeIfAbsent(name, key -> {
			final MutableConfiguration<String, Object> config = new MutableConfiguration<>();
			return Caching.getCachingProvider().getCacheManager().createCache(name, config);
		});
	}
}
