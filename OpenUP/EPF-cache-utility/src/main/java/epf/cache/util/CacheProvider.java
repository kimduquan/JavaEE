package epf.cache.util;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

/**
 * @author PC
 *
 */
public class CacheProvider {
	
	/**
	 * 
	 */
	private transient CachingProvider provider;

	/**
	 * @param classLoader
	 * @return
	 */
	public CacheManager getManager(final ClassLoader classLoader) {
		if(provider == null) {
			provider = Caching.getCachingProvider(classLoader);
		}
		return provider.getCacheManager(provider.getDefaultURI(), classLoader);
	}
	
	/**
	 * @param classLoader
	 */
	public void setDefaultClassLoader(final ClassLoader classLoader) {
		Caching.setDefaultClassLoader(classLoader);
	}
}
