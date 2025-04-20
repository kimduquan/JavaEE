package epf.cache.util;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

public class CacheProvider {
	
	private transient CachingProvider provider;

	public CacheManager getManager(final ClassLoader classLoader) {
		if(provider == null) {
			provider = Caching.getCachingProvider(classLoader);
		}
		return provider.getCacheManager(provider.getDefaultURI(), classLoader);
	}
	
	public void setDefaultClassLoader(final ClassLoader classLoader) {
		Caching.setDefaultClassLoader(classLoader);
	}
}
