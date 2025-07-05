package epf.query.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.cache.Cache;
import javax.cache.Caching;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableConfiguration;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import epf.cache.util.Loader;
import epf.query.Naming;
import epf.util.concurrent.ext.EventEmitter;

@ApplicationScoped
public class CachingManager {
	
	@Inject
	transient Event<EntityLoad> entityLoad;
	
	@Inject
	transient Event<QueryLoad> queryLoad;
	
	private transient final Map<String, Cache<String, Object>> entityCaches = new ConcurrentHashMap<>();
	
	private transient final Map<String, Cache<String, Integer>> queryCaches = new ConcurrentHashMap<>();

	public Cache<String, Object> getEntityCache(final String tenant){
		return entityCaches.computeIfAbsent(tenant != null ? String.join("-", Naming.ENTITY_CACHE, tenant) : Naming.ENTITY_CACHE, cacheName -> {
			final MutableConfiguration<String, Object> config = new MutableConfiguration<>();
			config.setCacheLoaderFactory(FactoryBuilder.factoryOf(new Loader<>(tenant, new EventEmitter<EntityLoad>(entityLoad), EntityLoad::new)));
			config.setReadThrough(true);
			return Caching.getCachingProvider().getCacheManager().createCache(cacheName, config);
		});
	}
	
	public Cache<String, Integer> getQueryCache(final String tenant){
		return queryCaches.computeIfAbsent(tenant != null ? String.join("-", Naming.QUERY_CACHE, tenant) : Naming.QUERY_CACHE, cacheName -> {
			final MutableConfiguration<String, Integer> config = new MutableConfiguration<>();
			config.setCacheLoaderFactory(FactoryBuilder.factoryOf(new Loader<>(tenant, new EventEmitter<QueryLoad>(queryLoad), QueryLoad::new)));
			config.setReadThrough(true);
			return Caching.getCachingProvider().getCacheManager().createCache(cacheName, config);
		});
	}
}
