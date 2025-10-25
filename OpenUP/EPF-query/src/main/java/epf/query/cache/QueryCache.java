package epf.query.cache;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.EntityType;
import jakarta.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import epf.naming.Naming;
import epf.query.client.Entity;
import epf.persistence.util.EntityUtil;
import epf.util.json.ext.JsonUtil;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;

@ApplicationScoped
public class QueryCache {
	
	@Inject
	transient EntityManager manager;

	@CacheResult(cacheName = Naming.Query.PERSISTENCE_CACHE)
	public CacheEntry getEntity(
			@CacheKey
			final String organizationId,
			@CacheKey
			final String schema, 
			@CacheKey
			final String name, 
			@CacheKey
			final String id,
			final EntityType<?> entityType) throws Exception {
		final Object entityId = EntityUtil.convertEntityId(entityType, id);
		final Class<?> entityClass = entityType.getJavaType();
		final Object entity = manager.find(entityClass, entityId);
		if(entity != null) {
			JsonUtil.toString(entity);
			manager.detach(entity);
			final CacheEntry entry = new CacheEntry();
			entry.setValue(entity);
			return entry;
		}
		throw new NotFoundException();
	}
	
	@CacheResult(cacheName = Naming.Query.PERSISTENCE_COUNT_CACHE)
	public Long countEntity(
			@CacheKey
			final String organizationId,
			@CacheKey
			final String schema, 
			@CacheKey
			final String name,
			final EntityType<?> entityType) throws Exception {
		final CriteriaBuilder builder = manager.getCriteriaBuilder();
		final CriteriaQuery<Long> query = builder.createQuery(Long.class);
		final Root<?> from = query.from(entityType);
		return manager.createQuery(query.select(builder.count(from))).getSingleResult();
	}
	
	@CacheResult(cacheName = Naming.Query.QUERY_COUNT_CACHE)
	public Integer executeCountQuery(
			@CacheKey
			final String organizationId,
			@CacheKey
			final String schema) throws Exception {
		return 0;
	}
	
	@CacheResult(cacheName = Naming.Query.QUERY_CACHE)
	public List<Entity> executeQuery(
			@CacheKey
			final String organizationId,
			@CacheKey
			final String schema) throws Exception {
		final List<Entity> entities = new ArrayList<>();
		return entities;
	}
}
