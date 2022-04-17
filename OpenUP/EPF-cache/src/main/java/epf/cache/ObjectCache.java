package epf.cache;

import java.util.Objects;
import epf.util.concurrent.ObjectQueue;

/**
 * @author PC
 *
 */
public abstract class ObjectCache<T extends Object> extends ObjectQueue<T> {
	
	/**
	 * 
	 */
	private transient final javax.cache.Cache<String, Object> cache;
	
	/**
	 * @param cache
	 */
	public ObjectCache(final javax.cache.Cache<String, Object> cache) {
		Objects.requireNonNull(cache, "Cache");
		this.cache = cache;
	}
	
	/**
	 * @return
	 */
	protected javax.cache.Cache<String, Object> getCache(){
		return cache;
	}
	
	/**
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T get(final String key) {
		return (T) cache.get(key);
	}
}
