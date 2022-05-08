package epf.cache.persistence.event;

import java.util.List;
import java.util.Map;

/**
 * 
 */
public class CacheLoad<K, V> {

	/**
	 *
	 */
	private List<K> keys;
	/**
	 *
	 */
	private Map<K, V> entries;
	
	public List<K> getKeys() {
		return keys;
	}
	public void setKeys(final List<K> keys) {
		this.keys = keys;
	}
	public Map<K, V> getEntries() {
		return entries;
	}
	public void setEntries(final Map<K, V> entries) {
		this.entries = entries;
	}
}
