package epf.cache.persistence.event;

import java.util.List;

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
	private List<V> values;
	
	public List<K> getKeys() {
		return keys;
	}
	public void setKeys(final List<K> keys) {
		this.keys = keys;
	}
	public List<V> getValues() {
		return values;
	}
	public void setValues(final List<V> values) {
		this.values = values;
	}
}
