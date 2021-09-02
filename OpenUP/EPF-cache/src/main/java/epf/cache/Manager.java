/**
 * 
 */
package epf.cache;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
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
public class Manager implements HealthCheck {

	/**
	 * 
	 */
	private transient CacheManager manager;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		manager = Caching.getCachingProvider().getCacheManager();
		final MutableConfiguration<String, Object> config = new MutableConfiguration<>();
		manager.createCache("persistence", config);
		manager.createCache("security", config);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		manager.close();
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-cache");
	}
	
	/**
	 * @param name
	 * @return
	 */
	public javax.cache.Cache<String, Object> getCache(final String name){
		return manager.getCache(name);
	}
}
