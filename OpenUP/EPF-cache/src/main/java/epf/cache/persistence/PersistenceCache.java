package epf.cache.persistence;

import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import epf.naming.Naming;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class PersistenceCache {
	
	/**
	 * 
	 */
	@PersistenceContext(unitName = "EPF_Cache")
	private transient EntityManager entityManager;
	
	/**
	 * 
	 */
	@Inject
	private transient SchemaCache schemaCache;

	/**
	 * 
	 */
	private transient CacheManager manager;
	
	/**
	 * 
	 */
	private transient Cache<String, Object> cache;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		manager = Caching.getCachingProvider().getCacheManager();
		final MutableConfiguration<String, Object> persistenceConfig = new MutableConfiguration<>();
		persistenceConfig.setCacheLoaderFactory(new EntityCacheLoaderFactory(entityManager, schemaCache));
		persistenceConfig.setReadThrough(true);
		cache = manager.createCache(Naming.PERSISTENCE, persistenceConfig);
	}
	
	/**
	 * @param event
	 */
	@Incoming(Naming.Persistence.PERSISTENCE_ENTITY_LISTENERS)
	@Transactional
	public void postEvent(final EntityEvent event) {
		if(event != null) {
			accept(event);
		}
	}
	
	/**
	 * @param event
	 */
	private void accept(final EntityEvent event) {
		if(event instanceof PostUpdate) {
			entityManager.merge(event.getEntity());
		}
		else if(event instanceof PostPersist) {
			entityManager.persist(event.getEntity());
		}
		else if(event instanceof PostRemove) {
			entityManager.remove(entityManager.merge(event.getEntity()));
		}
	}
	
	public Cache<String, Object> getCache(){
		return cache;
	}
}
