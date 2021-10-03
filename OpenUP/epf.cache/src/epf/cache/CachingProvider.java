package epf.cache;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author PC
 *
 */
@ProviderType
public interface CachingProvider {

	CacheManager getCacheManager();
}
