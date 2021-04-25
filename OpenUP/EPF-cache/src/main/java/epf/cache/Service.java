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
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import com.hazelcast.core.Hazelcast;
import epf.cache.persistence.Persistence;

/**
 * @author PC
 *
 */
@ApplicationScoped
@ApplicationPath("/cache")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Service extends Application {
	
	/**
	 * 
	 */
	private transient CacheManager manager;
	
	/**
	 * 
	 */
	private transient javax.cache.Cache<String, Object> persistenceCache;
	
	/**
	 * 
	 */
	@Inject
	private transient Persistence persistence;
	
	/**
	 * 
	 */
	@PostConstruct
	public void postConstruct() {
		Hazelcast.newHazelcastInstance();
		manager = Caching.getCachingProvider().getCacheManager();
		final MutableConfiguration<String, Object> config = new MutableConfiguration<>();
		persistenceCache = manager.createCache("persistence", config);
		persistence.setCache(persistenceCache);
	}
	
	/**
	 * 
	 */
	@PreDestroy
	public void preDestroy() {
		persistenceCache.close();
		manager.close();
		Hazelcast.shutdownAll();
	}
}
