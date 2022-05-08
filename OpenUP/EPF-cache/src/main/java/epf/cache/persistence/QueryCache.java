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
import epf.cache.persistence.event.QueryLoad;
import epf.cache.util.EventQueue;
import epf.naming.Naming;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class QueryCache implements HealthCheck {
	
	/**
	 * 
	 */
	@Inject
	private transient SchemaCache schemaCache;
	

	
	/**
	 *
	 */
	@Inject
	private transient EventQueue<QueryLoad> eventQueue;
	
	/**
	 *
	 */
	@Inject
	private transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	private transient Cache<String, Integer> queryCache;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		executor.submit(eventQueue);
		final CacheManager manager = Caching.getCachingProvider().getCacheManager();
		final MutableConfiguration<String, Integer> config = new MutableConfiguration<>();
		config.setCacheLoaderFactory(new LoaderFactory<String, Integer, QueryLoad>(eventQueue.getEmitter(), QueryLoad::new));
		config.setReadThrough(true);
		queryCache = manager.createCache(Naming.Persistence.QUERY, config);
	}
	
	@PreDestroy
	protected void preDestroy() {
		queryCache.close();
	}

	/**
	 * @param event
	 */
	public void accept(final EntityEvent event) {
		final Optional<QueryKey> queryKey = schemaCache.getQueryKey(event.getEntity().getClass());
		if(queryKey.isPresent()) {
			final String key = queryKey.get().toString();
			final boolean hasLoaded = queryCache.containsKey(key);
			final Integer count = queryCache.get(key);
			if(hasLoaded) {
				if(event instanceof PostPersist) {
					queryCache.replace(key, count, count + 1);
				}
				else if(event instanceof PostRemove) {
					queryCache.replace(key, count, count - 1);
				}
			}
		}
	}
	
	/**
	 * @param schema
	 * @param entity
	 * @return
	 */
	public Optional<Integer> countEntity(
			final String schema,
            final String entity
            ) {
		final QueryKey queryKey = schemaCache.getQueryKey(schema, entity);
		return Optional.ofNullable(queryCache.get(queryKey.toString()));
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-persistence-query-cache");
	}
}
