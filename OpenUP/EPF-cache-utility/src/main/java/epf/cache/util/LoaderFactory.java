package epf.cache.util;

import javax.cache.configuration.Factory;
import javax.cache.integration.CacheLoader;

/**
 * 
 */
public class LoaderFactory<K, V> implements Factory<CacheLoader<K, V>> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *
	 */
	private transient CacheLoader<K, V> loader;
	
	/**
	 * @param loader
	 */
	public LoaderFactory(final CacheLoader<K, V> loader) {
		this.loader = loader;
	}

	@Override
	public CacheLoader<K, V> create() {
		return loader;
	}
}
