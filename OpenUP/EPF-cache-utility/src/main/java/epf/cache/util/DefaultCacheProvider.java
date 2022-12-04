package epf.cache.util;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

/**
 * @author PC
 *
 */
public class DefaultCacheProvider implements CacheProvider {
	
	/**
	 * 
	 */
	private transient CachingProvider provider;

	@Override
	public CacheManager getManager() {
		if(provider == null) {
			provider = Caching.getCachingProvider();
		}
		return provider.getCacheManager();
	}
}
