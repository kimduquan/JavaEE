package epf.cache.persistence;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;
import epf.persistence.internal.Entity;
import epf.persistence.internal.QueryBuilder;
import epf.persistence.internal.util.EntityTypeUtil;
import epf.schema.utility.EntityEvent;
import epf.schema.utility.PostPersist;
import epf.schema.utility.PostRemove;
import epf.schema.utility.PostUpdate;
import epf.util.json.JsonUtil;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Readiness
public class PersistenceCache implements HealthCheck {
	
	/**
	 *
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(PersistenceCache.class.getName());
	
	/**
	 *
	 */
	@PersistenceContext(unitName = "EPF_Cache")
	private transient EntityManager entityManager;
	
	/**
	 * 
	 */
	@Inject
	private transient SchemaCache schemaCache;
	
	/**
	 * @param event
	 */
	@Transactional
	public void accept(final EntityEvent event) {
		try {
			if(event instanceof PostUpdate) {
				entityManager.merge(event.getEntity());
			}
			else if(event instanceof PostPersist) {
				entityManager.persist(event.getEntity());
			}
			else if(event instanceof PostRemove) {
				final Optional<Object> entityId = schemaCache.getEntityId(event.getEntity());
				if(entityId.isPresent()) {
					entityManager.remove(entityManager.getReference(event.getEntity().getClass(), entityId.get()));
				}
			}
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[PersistenceCache.accept]", ex);
		}
	}

	@Override
	public HealthCheckResponse call() {
		return HealthCheckResponse.up("EPF-persistence-cache");
	}
	
	/**
	 * @param schema
	 * @param paths
	 * @param firstResult
	 * @param maxResults
	 * @param context
	 * @param sort
	 * @return
	 * @throws Exception 
	 */
	public Response executeQuery(
			final String schema, 
			final List<PathSegment> paths, 
			final Integer firstResult, 
			final Integer maxResults,
			final SecurityContext context,
			final List<String> sort) throws Exception {
		final Entity<Object> entity = new Entity<>();
    	if(!paths.isEmpty()){
        	final PathSegment rootSegment = paths.get(0);
        	final String entityName = rootSegment.getPath();
        	@SuppressWarnings("unchecked")
			final EntityType<Object> entityType = (EntityType<Object>) EntityTypeUtil.findEntityType(entityManager.getMetamodel(), entityName).orElseThrow(NotFoundException::new);
        	EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
        		if(!entitySchema.equals(schema)) {
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
    	throw new NotFoundException();
	}
	
	/**
	 * @param schema
	 * @param paths
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public Response executeCountQuery(
			final String schema, 
			final List<PathSegment> paths,
			final SecurityContext context) throws Exception {
		final Entity<Object> entity = new Entity<>();
    	if(!paths.isEmpty()){
        	final PathSegment rootSegment = paths.get(0);
        	final String entityName = rootSegment.getPath();
        	@SuppressWarnings("unchecked")
			final EntityType<Object> entityType = (EntityType<Object>) EntityTypeUtil.findEntityType(entityManager.getMetamodel(), entityName).orElseThrow(NotFoundException::new);
        	EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
        		if(!entitySchema.equals(schema)) {
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
        	return Response.ok().header(Naming.Query.ENTITY_COUNT, query.getSingleResult()).build();
        }
    	throw new NotFoundException();
	}
	
	/**
	 * @param manager
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws Exception 
	 */
	private Response executeQuery(
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
        return Response.ok(JsonUtil.toJsonArray(resultList)).header(Naming.Query.ENTITY_COUNT, resultList.size()).build();
    }
}
