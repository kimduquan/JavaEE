package epf.query.internal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.EntityType;
import jakarta.ws.rs.NotFoundException;
import epf.naming.Naming;
import epf.persistence.util.EntityUtil;
import epf.query.cache.CacheEntry;
import epf.util.json.ext.JsonUtil;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;

@ApplicationScoped
public class EntityCache {
	
	@Inject
	transient EntityManager manager;

	@CacheResult(cacheName = Naming.Query.QUERY_ENTITY)
	public CacheEntry getEntity(
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
	
	@CacheInvalidate(cacheName = Naming.Query.QUERY_ENTITY)
	public void clearEntity(
			@CacheKey
			final String schema, 
			@CacheKey
			final String name, 
			@CacheKey
			final String id) {
	}
	
	@CacheResult(cacheName = Naming.Query.QUERY_ENTITY_COUNT)
	public Long countEntity(
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
	
	@CacheInvalidate(cacheName = Naming.Query.QUERY_ENTITY_COUNT)
	public void clearEntityCount(
			@CacheKey
			final String schema, 
			@CacheKey
			final String name
			) {
	}
}
