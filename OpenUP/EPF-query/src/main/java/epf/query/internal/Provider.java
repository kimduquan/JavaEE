package epf.query.internal;

import javax.annotation.PostConstruct;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class Provider implements HealthCheck {
	
	/**
	 * 
	 */
	private transient CachingProvider provider;

	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		provider = Caching.getCachingProvider("com.hazelcast.cache.HazelcastMemberCachingProvider");
	}
	
	public CacheManager getManager() {
		return provider.getCacheManager();
	}

	@Override
	public HealthCheckResponse call() {
		if(provider == null) {
			return HealthCheckResponse.down("epf-query-cache-provider");
		}
		return HealthCheckResponse.up("epf-query-cache-provider");
	}
}
