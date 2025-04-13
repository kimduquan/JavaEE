package epf.cache;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Readiness
public class Cache implements HealthCheck {
	
	private javax.cache.Cache<String, Object> eventCache;
	
	@PostConstruct
	protected void postConstruct() {
		final CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
		eventCache = cacheManager.getCache(Naming.Event.Internal.EPF_EVENT_CACHE);
		if(eventCache == null) {
			final MutableConfiguration<String, Object> config = new MutableConfiguration<>();
			eventCache = cacheManager.createCache(Naming.Event.Internal.EPF_EVENT_CACHE, config);
		}
	}

	@Override
	public HealthCheckResponse call() {
		if(eventCache == null) {
			return HealthCheckResponse.down(Naming.Event.Internal.EPF_EVENT_CACHE);
		}
		return HealthCheckResponse.up(Naming.Event.Internal.EPF_EVENT_CACHE);
	}
}
