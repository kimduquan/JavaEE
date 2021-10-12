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
import javax.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;

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
	@Inject
	private transient Listener listener;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		manager = Caching.getCachingProvider().getCacheManager();
		final MutableConfiguration<String, Object> config = new MutableConfiguration<>();
		manager.createCache(Naming.PERSISTENCE, config);
		manager.createCache(Naming.SECURITY, config);
		listener.registerEvents(manager);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		listener.deregisterEvents(manager);
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
