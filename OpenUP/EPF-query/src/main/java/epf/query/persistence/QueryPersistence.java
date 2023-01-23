package epf.query.persistence;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.EntityType;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.persistence.internal.Entity;
import epf.persistence.internal.QueryBuilder;
import epf.persistence.util.EntityTypeUtil;
import epf.schema.utility.Request;
import epf.util.json.JsonUtil;
import epf.util.logging.LogManager;

/**
 * 
 */
@ApplicationScoped
@Readiness
public class QueryPersistence implements HealthCheck {
	
	/**
	 *
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(QueryPersistence.class.getName());
	
	/**
	 *
	 */
	@PersistenceContext(unitName = epf.query.Naming.QUERY_UNIT_NAME)
	transient EntityManager entityManager;
	
	/**
	 * 
	 */
	@Inject
	Request request;
	
	/**
	 * @param paths
	 * @param firstResult
	 * @param maxResults
	 * @param context
	 * @param sort
	 * @return
	 * @throws Exception
	 */
	public List<?> executeQuery(
			final List<PathSegment> paths, 
			final Integer firstResult, 
			final Integer maxResults,
			final SecurityContext context,
			final List<String> sort) throws Exception {
		final Entity<Object> entity = new Entity<>();
		final PathSegment rootSegment = paths.get(0);
    	final String entityName = rootSegment.getPath();
    	@SuppressWarnings("unchecked")
		final EntityType<Object> entityType = (EntityType<Object>) EntityTypeUtil.findEntityType(entityManager.getMetamodel(), entityName).orElseThrow(NotFoundException::new);
    	EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
    		if(!entitySchema.equals(request.getSchema())) {
    			throw new NotFoundException();
    		}
    	});
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
	
	/**
	 * @param paths
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public Object executeCountQuery(
			final List<PathSegment> paths,
			final SecurityContext context) throws Exception {
		final Entity<Object> entity = new Entity<>();
		final PathSegment rootSegment = paths.get(0);
    	final String entityName = rootSegment.getPath();
    	@SuppressWarnings("unchecked")
		final EntityType<Object> entityType = (EntityType<Object>) EntityTypeUtil.findEntityType(entityManager.getMetamodel(), entityName).orElseThrow(NotFoundException::new);
    	EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
    		if(!entitySchema.equals(request.getSchema())) {
    			throw new NotFoundException();
    		}
    	});
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
	
	/**
	 * @param manager
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws Exception 
	 */
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

	@Override
	public HealthCheckResponse call() {
		if(!entityManager.isOpen()) {
			return HealthCheckResponse.down("EPF-query-persistence");
		}
		return HealthCheckResponse.up("EPF-query-query-persistence");
	}
}
