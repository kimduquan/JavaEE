package epf.cache.persistence;

import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.cache.persistence.event.EntityLoad;
import epf.cache.util.EventQueue;
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
public class EntityCache implements HealthCheck {
	
	/**
	 * 
	 */
	@Inject
	private transient SchemaCache schemaCache;
	
	/**
	 *
	 */
	@Inject
	private transient EventQueue<EntityLoad> eventQueue;
	
	/**
	 *
	 */
	@Inject
	private transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	private transient Cache<String, Object> entityCache;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		executor.submit(eventQueue);
		final CacheManager manager = Caching.getCachingProvider().getCacheManager();
		final MutableConfiguration<String, Object> config = new MutableConfiguration<>();
		config.setCacheLoaderFactory(new LoaderFactory<String, Object, EntityLoad>(eventQueue.getEmitter(), EntityLoad::new));
		config.setReadThrough(true);
		entityCache = manager.createCache(Naming.PERSISTENCE, config);
	}
	
	@PreDestroy
	protected void preDestroy() {
		entityCache.close();
	}

	/**
	 * @param event
	 */
	public void accept(final EntityEvent event) {
		final Optional<EntityKey> key = schemaCache.getKey(event.getEntity());
		if(key.isPresent()) {
			if(event instanceof PostUpdate) {
				entityCache.replace(key.get().toString(), event.getEntity());
			}
			else if(event instanceof PostPersist) {
				entityCache.put(key.get().toString(), event.getEntity());
			}
			else if(event instanceof PostRemove) {
				entityCache.remove(key.get().toString());
			}
		}
	}
	
	/**
	 * @param name
	 * @param entityId
	 * @return
	 */
	public Optional<Object> getEntity(
			final String schema,
            final String name,
            final String entityId
            ) {
		final EntityKey key = schemaCache.getKey(schema, name, entityId);
		return Optional.ofNullable(entityCache.get(key.toString()));
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-persistence-entity-cache");
	}
}
