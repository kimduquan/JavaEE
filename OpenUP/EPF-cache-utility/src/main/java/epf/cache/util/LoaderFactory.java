package epf.cache.util;

import java.util.logging.Logger;
import javax.cache.configuration.Factory;
import javax.cache.integration.CacheLoader;
import epf.util.logging.LogManager;

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
	private transient static final Logger LOGGER = LogManager.getLogger(LoaderFactory.class.getName());
	
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
	
	public LoaderFactory() {
		this.loader = new EmptyLoader<>();
	}

	@Override
	public CacheLoader<K, V> create() {
		if(loader == null) {
			LOGGER.warning("[LoaderFactory][create] Create empty cache loader");
			loader = new EmptyLoader<>();
		}
		return loader;
	}
}
