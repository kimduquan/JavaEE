package epf.query.internal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.ws.rs.NotFoundException;
import java.util.Optional;
import epf.naming.Naming;
import epf.persistence.util.EntityTypeUtil;
import epf.persistence.util.EntityUtil;
import epf.query.cache.CacheEntry;
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
			final String id) throws Exception {
		final Optional<EntityType<?>> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), schema, name);
		final Object entityId = EntityUtil.convertEntityId(entityType.get(), id);
		final Class<?> entityClass = entityType.get().getJavaType();
		final Object entity = manager.find(entityClass, entityId);
		if(entity != null) {
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
	public Integer countEntity(
			@CacheKey
			final String schema, 
			@CacheKey
			final String name) throws Exception {
		return 0;
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
