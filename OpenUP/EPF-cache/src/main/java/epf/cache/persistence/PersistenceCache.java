package epf.cache.persistence;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class PersistenceCache implements HealthCheck {
	
	/**
	 *
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(PersistenceCache.class.getName());
	
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
	@Transactional
	public void accept(final EntityEvent event) {
		try {
			if(event instanceof PostUpdate) {
				entityManager.merge(event.getEntity());
			}
			else if(event instanceof PostPersist) {
				entityManager.persist(event.getEntity());
			}
			else if(event instanceof PostRemove) {
				final Optional<Object> entityId = schemaCache.getEntityId(event.getEntity());
				if(entityId.isPresent()) {
					entityManager.remove(entityManager.getReference(event.getEntity().getClass(), entityId.get()));
				}
			}
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[PersistenceCache.accept]", ex);
		}
	}
	
	public Cache<String, Object> getCache(){
		return cache;
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-persistence-cache");
	}
}
