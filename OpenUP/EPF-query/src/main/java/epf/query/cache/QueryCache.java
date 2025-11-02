package epf.query.cache;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.EntityType;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.PathSegment;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import epf.naming.Naming;
import epf.persistence.util.EntityTypeUtil;
import epf.persistence.util.EntityUtil;
import epf.query.internal.QueryBuilder;
import epf.query.schema.NativeQuery;
import epf.query.schema.ResultList;
import epf.query.schema.SingleResult;
import epf.schema.internal.Entity;
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
	
	@CacheResult(cacheName = Naming.Query.QUERY_CACHE, keyGenerator = QueryCacheKeyGenerator.class)
	public List<?> executeQuery(
			@CacheKey
			final String organizationId,
			@CacheKey
			final String schema,
			@CacheKey
			final PathSegment[] paths,
			@CacheKey
			final Integer firstResult,
			@CacheKey
			final Integer maxResults,
			@CacheKey
			final String[] sort) throws Exception {
		final Entity<Object> entity = new Entity<>();
		final PathSegment rootSegment = paths[0];
    	final String entityName = rootSegment.getPath();
    	@SuppressWarnings("unchecked")
		final EntityType<Object> entityType = (EntityType<Object>) EntityTypeUtil.findEntityType(manager.getMetamodel(), schema, entityName).orElseThrow(NotFoundException::new);
    	entity.setType(entityType);
    	final QueryBuilder queryBuilder = new QueryBuilder();
    	final CriteriaQuery<Object> criteria = queryBuilder
    			.metamodel(manager.getMetamodel())
    			.criteria(manager.getCriteriaBuilder())
    			.entity(entity)
    			.paths(Arrays.asList(paths))
    			.sort(Arrays.asList(sort))
    			.build();
    	return executeQuery(manager, criteria, firstResult, maxResults);
	}
	
	@CacheResult(cacheName = Naming.Query.QUERY_COUNT_CACHE, keyGenerator = QueryCacheKeyGenerator.class)
	public Long executeCountQuery(
			@CacheKey
			final String organizationId,
			@CacheKey
			final String schema,
			@CacheKey
			final PathSegment[] paths) throws Exception {
		final Entity<Object> entity = new Entity<>();
		final PathSegment rootSegment = paths[0];
    	final String entityName = rootSegment.getPath();
    	@SuppressWarnings("unchecked")
		final EntityType<Object> entityType = (EntityType<Object>) EntityTypeUtil.findEntityType(manager.getMetamodel(), schema, entityName).orElseThrow(NotFoundException::new);
    	entity.setType(entityType);
    	final QueryBuilder queryBuilder = new QueryBuilder();
    	final CriteriaQuery<Object> criteria = queryBuilder
    			.metamodel(manager.getMetamodel())
    			.criteria(manager.getCriteriaBuilder())
    			.entity(entity)
    			.paths(Arrays.asList(paths))
    			.countOnly()
    			.build();
    	final TypedQuery<?> query = manager.createQuery(criteria);
    	return (Long)query.getSingleResult();
	}
	
	private List<?> executeQuery(
    		final EntityManager manager, 
    		final CriteriaQuery<Object> criteria,
    		final Integer firstResult,
            final Integer maxResults) throws Exception {
		final TypedQuery<Object> query = manager.createQuery(criteria);
		if(firstResult != null){
            query.setFirstResult(firstResult);
        }
        if(maxResults != null){
            query.setMaxResults(maxResults);
        }
        final List<?> resultList = query.getResultList();
        for(Object object : resultList) {
        	manager.detach(object);
        }
        return resultList;
    }
	
	public SingleResult executeSingleResultQuery(final NativeQuery nativeQuery) {
		final Query query = manager.createQuery(nativeQuery.getQuery());
		if(nativeQuery.getParameters() != null) {
			for(Map.Entry<String, Object> parameter : nativeQuery.getParameters().entrySet()) {
				query.setParameter(parameter.getKey(), parameter.getValue());
			}
		}
		final Object result = query.getSingleResultOrNull();
		final SingleResult queryResult = new SingleResult();
		queryResult.setResult(result);
		return queryResult;
	}
	
	public ResultList executeResultListQuery(final NativeQuery nativeQuery, final Integer firstResult, final Integer maxResults) {
		final Query query = manager.createQuery(nativeQuery.getQuery());
		if(nativeQuery.getParameters() != null) {
			for(Map.Entry<String, Object> parameter : nativeQuery.getParameters().entrySet()) {
				query.setParameter(parameter.getKey(), parameter.getValue());
			}
		}
		if(firstResult != null) {
			query.setFirstResult(firstResult);
		}
		if(maxResults != null) {
			query.setMaxResults(maxResults);
		}
		final List<?> results = query.getResultList();
		final ResultList queryResult = new ResultList();
		queryResult.setResult(results);
		return queryResult;
	}
}
