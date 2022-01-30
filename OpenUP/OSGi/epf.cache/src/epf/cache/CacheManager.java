package epf.cache;

import java.io.Closeable;

/**
 * @author PC
 *
 */
public interface CacheManager extends Closeable {

	/**
	 * @param name
	 * @return
	 */
	Cache createCache(final String cacheName);
	
	/**
	 * @param name
	 * @return
	 */
	Cache getCache(final String cacheName);
	
	/**
	 * @return
	 */
	Iterable<String> getCacheNames();
	
	/**
	 * @param cacheName
	 */
	void destroyCache(final String cacheName);
}
