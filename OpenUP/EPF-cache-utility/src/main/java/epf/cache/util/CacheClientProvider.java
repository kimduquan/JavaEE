package epf.cache.util;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

/**
 * @author PC
 *
 */
public class CacheClientProvider implements CacheProvider {
	
	/**
	 * 
	 */
	private transient CachingProvider provider;

	@Override
	public CacheManager getManager() {
		if(provider == null) {
			provider = Caching.getCachingProvider(CLIENT_PROVIDER);
		}
		return provider.getCacheManager();
	}
}
