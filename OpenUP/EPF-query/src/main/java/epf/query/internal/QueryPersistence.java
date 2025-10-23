package epf.query.internal;

import java.util.List;
import java.util.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.metamodel.EntityType;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.PathSegment;
import epf.persistence.internal.Entity;
import epf.persistence.internal.QueryBuilder;
import epf.persistence.util.EntityTypeUtil;
import epf.util.json.ext.JsonUtil;
import epf.util.logging.LogManager;

@ApplicationScoped
public class QueryPersistence {
	
	private transient static final Logger LOGGER = LogManager.getLogger(QueryPersistence.class.getName());
	
	@Inject
	transient EntityManager entityManager;
	
	public List<?> executeQuery(
			final String schema,
			final List<PathSegment> paths, 
			final Integer firstResult, 
			final Integer maxResults,
			final List<String> sort) throws Exception {
		final Entity<Object> entity = new Entity<>();
		final PathSegment rootSegment = paths.get(0);
    	final String entityName = rootSegment.getPath();
    	@SuppressWarnings("unchecked")
		final EntityType<Object> entityType = (EntityType<Object>) EntityTypeUtil.findEntityType(entityManager.getMetamodel(), schema, entityName).orElseThrow(NotFoundException::new);
    	entity.setType(entityType);
    	final QueryBuilder queryBuilder = new QueryBuilder();
    	final CriteriaQuery<Object> criteria = queryBuilder
    			.metamodel(entityManager.getMetamodel())
    			.criteria(entityManager.getCriteriaBuilder())
    			.entity(entity)
    			.paths(paths)
    			.sort(sort)
    			.build();
    	return executeQuery(entityManager, criteria, firstResult, maxResults);
	}
	
	public Object executeCountQuery(
			final String schema,
			final List<PathSegment> paths) throws Exception {
		final Entity<Object> entity = new Entity<>();
		final PathSegment rootSegment = paths.get(0);
    	final String entityName = rootSegment.getPath();
    	@SuppressWarnings("unchecked")
		final EntityType<Object> entityType = (EntityType<Object>) EntityTypeUtil.findEntityType(entityManager.getMetamodel(), schema, entityName).orElseThrow(NotFoundException::new);
    	entity.setType(entityType);
    	final QueryBuilder queryBuilder = new QueryBuilder();
    	final CriteriaQuery<Object> criteria = queryBuilder
    			.metamodel(entityManager.getMetamodel())
    			.criteria(entityManager.getCriteriaBuilder())
    			.entity(entity)
    			.paths(paths)
    			.countOnly()
    			.build();
    	final TypedQuery<?> query = entityManager.createQuery(criteria);
    	return query.getSingleResult();
	}
	
	private List<?> executeQuery(
    		final EntityManager manager, 
    		final CriteriaQuery<Object> criteria,
    		final Integer firstResult,
            final Integer maxResults) throws Exception{
		final TypedQuery<Object> query = manager.createQuery(criteria);
		if(firstResult != null){
            query.setFirstResult(firstResult);
        }
        if(maxResults != null){
            query.setMaxResults(maxResults);
        }
        final List<?> resultList = query.getResultList();
        resultList.forEach(object -> {
        	try {
				JsonUtil.toString(object);
			} 
        	catch (Exception e) {
				LOGGER.warning("[PersistenceQuery][executeQuery]" + e.getMessage());
			}
        });
        return resultList;
    }
}
