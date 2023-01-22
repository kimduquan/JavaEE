package epf.query.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableConfiguration;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.cache.util.Loader;
import epf.query.cache.EntityLoad;
import epf.query.client.EntityId;
import epf.schema.utility.EntityEvent;
import epf.util.event.EventEmitter;
import epf.util.event.EventQueue;
import epf.util.logging.LogManager;

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
	private transient static final Logger LOGGER = LogManager.getLogger(EntityCache.class.getName());
	
	/**
	 * 
	 */
	@Inject @Readiness
	transient SchemaCache schemaCache;
	
	/**
	 *
	 */
	@Inject
	transient EventQueue<EntityLoad> eventQueue;
	
	/**
	 * 
	 */
	transient Cache<String, Object> entityCache;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			final CacheManager manager = Caching.getCachingProvider().getCacheManager();
			final MutableConfiguration<String, Object> config = new MutableConfiguration<>();
			config.setCacheLoaderFactory(FactoryBuilder.factoryOf(new Loader<>(new EventEmitter<EntityLoad>(eventQueue), EntityLoad::new)));
			config.setReadThrough(true);
			entityCache = manager.getCache(epf.query.Naming.ENTITY_CACHE);
			if(entityCache == null) {
				entityCache = manager.createCache(epf.query.Naming.ENTITY_CACHE, config);	
			}
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[EntityCache.entityCache]", ex);
		}
	}
	
	@PreDestroy
	protected void preDestroy() {
		eventQueue.close();
		entityCache.close();
	}

	/**
	 * @param event
	 */
	public void accept(final EntityEvent event) {
		final Optional<EntityKey> key = schemaCache.getKey(event.getTenant(), event.getEntity());
		if(key.isPresent()) {
			entityCache.remove(key.get().toString());
		}
	}
	
	/**
	 * @param name
	 * @param entityId
	 * @return
	 */
	public Optional<Object> getEntity(
			final String tenant,
			final String schema,
            final String name,
            final String entityId
            ) {
		final EntityKey key = schemaCache.getKey(tenant, schema, name, entityId);
		return Optional.ofNullable(entityCache.get(key.toString()));
	}
	
	/**
	 * @param entities
	 * @return
	 */
	public List<Object> getEntities(final String tenant, final List<EntityId> entityIds){
		final List<EntityKey> entityKeys = entityIds.stream().map(key -> schemaCache.getSearchKey(tenant, key)).collect(Collectors.toList());
		final List<String> keys = entityKeys.stream().map(EntityKey::toString).collect(Collectors.toList());
		final Map<String, Object> values = entityCache.getAll(keys.stream().collect(Collectors.toSet()));
		final List<Object> result = new ArrayList<>();
		keys.forEach(key -> {
			result.add(values.get(key));
		});
		return result;
	}

	@Override
	public HealthCheckResponse call() {
		if(entityCache == null || entityCache.isClosed()) {
			return HealthCheckResponse.down("EPF-query-entity-cache");
		}
		return HealthCheckResponse.up("EPF-query-entity-cache");
	}
	
	/**
	 * 
	 */
	public void submit(final ManagedExecutor executor) {
		executor.submit(eventQueue);
	}
}
