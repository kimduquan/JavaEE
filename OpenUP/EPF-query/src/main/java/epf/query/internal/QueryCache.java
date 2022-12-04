package epf.query.internal;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.cache.util.DefaultCacheProvider;
import epf.cache.util.Loader;
import epf.cache.util.LoaderFactory;
import epf.query.internal.event.QueryLoad;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.util.event.EventEmitter;
import epf.util.event.EventQueue;
import epf.util.logging.LogManager;

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
	private transient static final Logger LOGGER = LogManager.getLogger(QueryCache.class.getName());
	
	/**
	 * 
	 */
	private transient final DefaultCacheProvider provider = new DefaultCacheProvider();
	
	/**
	 * 
	 */
	private transient Cache<String, Integer> queryCache;
	
	/**
	 * 
	 */
	@Inject  @Readiness
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
	@PostConstruct
	protected void postConstruct() {
		try {
			final CacheManager manager = provider.getManager();
			executor.submit(eventQueue);
			final MutableConfiguration<String, Integer> config = new MutableConfiguration<>();
			config.setCacheLoaderFactory(new LoaderFactory<>(new Loader<String, Integer, QueryLoad>(new EventEmitter<QueryLoad>(eventQueue), QueryLoad::new)));
			config.setReadThrough(true);
			queryCache = manager.createCache(epf.query.Naming.QUERY_CACHE, config);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[QueryCache.queryCache]", ex);
		}
	}
	
	@PreDestroy
	protected void preDestroy() {
		queryCache.close();
	}

	/**
	 * @param event
	 */
	public void accept(final EntityEvent event) {
		final Optional<QueryKey> queryKey = schemaCache.getQueryKey(event.getTenant(), event.getEntity().getClass());
		if(queryKey.isPresent()) {
			final String key = queryKey.get().toString();
			final boolean hasLoaded = queryCache.containsKey(key);
			if(hasLoaded) {
				final Integer count = queryCache.get(key);
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
	 * @param tenant
	 * @param schema
	 * @param entity
	 * @return
	 */
	public Optional<Integer> countEntity(
    		final String tenant,
			final String schema,
            final String entity
            ) {
		final QueryKey queryKey = schemaCache.getQueryKey(tenant, schema, entity);
		return Optional.ofNullable(queryCache.get(queryKey.toString()));
	}

	@Override
	public HealthCheckResponse call() {
		if(executor.isShutdown() || executor.isTerminated() || queryCache == null || queryCache.isClosed()) {
			return HealthCheckResponse.down("EPF-query-query-cache");
		}
		return HealthCheckResponse.up("EPF-query-query-cache");
	}
}
