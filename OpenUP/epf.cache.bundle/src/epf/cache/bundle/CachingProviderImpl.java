package epf.cache.bundle;

import org.osgi.service.component.annotations.*;
import epf.cache.CacheManager;
import epf.cache.CachingProvider;

/**
 * @author PC
 *
 */
@Component
public class CachingProviderImpl implements CachingProvider {
	
	/**
	 * 
	 */
	private transient final CacheManager cacheManager = new CacheManagerImpl();

	@Override
	public CacheManager getCacheManager() {
		return cacheManager;
	}
}
