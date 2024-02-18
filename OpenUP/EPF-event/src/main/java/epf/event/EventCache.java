package epf.event;

import java.util.Map;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming.Event.Internal;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * 
 */
@ApplicationScoped
@Readiness
public class EventCache implements HealthCheck {
	
	/**
	 * 
	 */
	private transient Cache<String, Map<String, Object>> eventCache;

	@Override
	public HealthCheckResponse call() {
		final CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
		eventCache = cacheManager.getCache(Internal.EPF_EVENT_CACHE);
		if(eventCache == null) {
			final MutableConfiguration<String, Map<String, Object>> config = new MutableConfiguration<>();
			eventCache = cacheManager.createCache(Internal.EPF_EVENT_CACHE, config);
		}
		if(eventCache != null) {
			return HealthCheckResponse.up(Internal.EPF_EVENT_CACHE);
		}

		return HealthCheckResponse.down(Internal.EPF_EVENT_CACHE);
	}
	
	/**
	 * @param key
	 * @param value
	 */
	public void put(final String key, final Map<String, Object> value) {
		eventCache.put(key, value);
	}
	
	/**
	 * @param key
	 * @return
	 */
	public Map<String, Object> remove(final String key) {
		return eventCache.getAndRemove(key);
	}
}
