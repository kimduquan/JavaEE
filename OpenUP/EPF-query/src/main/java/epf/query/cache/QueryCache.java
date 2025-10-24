package epf.query.cache;

import java.util.ArrayList;
import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import epf.naming.Naming;
import epf.query.client.Entity;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;

@ApplicationScoped
public class QueryCache {
	
	@CacheResult(cacheName = Naming.Query.QUERY_COUNT)
	public Integer executeCountQuery(
			@CacheKey
			final String organizationId,
			@CacheKey
			final String schema) throws Exception {
		return 0;
	}
	
	@CacheInvalidate(cacheName = Naming.Query.QUERY_COUNT)
	public void clearQueryCount(
			@CacheKey
			final String organizationId,
			@CacheKey
			final String schema) throws Exception {
	}
	
	@CacheResult(cacheName = Naming.Query.QUERY)
	public List<Entity> executeQuery(
			@CacheKey
			final String organizationId,
			@CacheKey
			final String schema) throws Exception {
		final List<Entity> entities = new ArrayList<>();
		return entities;
	}
	
	@CacheInvalidate(cacheName = Naming.Query.QUERY)
	public void clearQuery(
			@CacheKey
			final String organizationId,
			@CacheKey
			final String schema) throws Exception {
	}
}
