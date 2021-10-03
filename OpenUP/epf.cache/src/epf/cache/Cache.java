package epf.cache;

import java.io.Closeable;
import java.util.Map;
import java.util.Set;

/**
 * @author PC
 *
 */
public interface Cache extends Closeable {

	/**
	 * @param key
	 * @return
	 */
	Object get(final String key);
	
	/**
	 * @param keys
	 * @return
	 */
	Map<String, Object> getAll(final Set<String> keys);
	
	/**
	 * @param key
	 * @param value
	 */
	void put(final String key, final Object value);
	
	/**
	 * @param map
	 */
	void putAll(final Map<String, Object> map);
	
	/**
	 * @param key
	 * @return
	 */
	boolean remove(final String key);
	
	/**
	 * @param keys
	 */
	void removeAll(final Set<String> keys);
	
	/**
	 * 
	 */
	void removeAll();
}
