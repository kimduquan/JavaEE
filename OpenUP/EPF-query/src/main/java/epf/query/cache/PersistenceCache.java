package epf.query.cache;

import epf.naming.Naming;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheKey;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersistenceCache {
	
	@CacheInvalidate(cacheName = Naming.Query.PERSISTENCE_CACHE)
	public void clearEntity(@CacheKey final String organizationId, @CacheKey final String schema,  @CacheKey final String name, @CacheKey final String id) {
	}
	
	@CacheInvalidate(cacheName = Naming.Query.PERSISTENCE_COUNT_CACHE)
	public void clearEntityCount(@CacheKey final String organizationId, @CacheKey final String schema, @CacheKey final String name) {
	}
	
	@CacheInvalidate(cacheName = Naming.Query.QUERY_COUNT_CACHE)
	public void clearQueryCount(@CacheKey final String organizationId, @CacheKey final String schema) throws Exception {
	}
	
	@CacheInvalidate(cacheName = Naming.Query.QUERY_CACHE)
	public void clearQuery(@CacheKey final String organizationId, @CacheKey final String schema) throws Exception {
	}
}
