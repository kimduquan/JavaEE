package epf.cache;

import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.EntityType;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.health.Readiness;
import epf.cache.persistence.Persistence;
import epf.cache.security.Security;
import epf.security.schema.Token;
import epf.util.json.JsonUtil;
import epf.naming.Naming;
import epf.persistence.internal.Entity;
import epf.persistence.internal.QueryBuilder;
import epf.persistence.internal.util.EntityTypeUtil;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.CACHE)
public class Cache implements epf.client.cache.Cache {
	
	/**
	 * 
	 */
	@PersistenceContext(unitName = "EPF_Cache")
	private transient EntityManager manager;
	
	/**
	 * 
	 */
	@Inject @Readiness
	private transient Security security;

	/**
	 * 
	 */
	@Inject @Readiness
	private transient Persistence persistence;
	
	@Override
    public Response getEntity(
    		final String schema,
            final String name,
            final String entityId
            ) {
		final Optional<Object> entity = persistence.getEntity(schema, name, entityId);
		return Response.ok(entity.orElseThrow(NotFoundException::new)).build();
	}

	@Override
	public Token getToken(final String tokenId) {
		return security.getToken(tokenId);
	}

	@Override
	public Response executeQuery(
			final String schema, 
			final List<PathSegment> paths, 
			final Integer firstResult, 
			final Integer maxResults,
			final SecurityContext context) {
		final Entity<Object> entity = new Entity<>();
    	if(!paths.isEmpty()){
        	final PathSegment rootSegment = paths.get(0);
        	final String entityName = rootSegment.getPath();
        	@SuppressWarnings("unchecked")
			final EntityType<Object> entityType = (EntityType<Object>) EntityTypeUtil.findEntityType(manager.getEntityManagerFactory().getMetamodel(), entityName).orElseThrow(NotFoundException::new);
        	EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
        		if(!entitySchema.equals(schema)) {
        			throw new NotFoundException();
        		}
        	});
        	entity.setType(entityType);
        	final QueryBuilder queryBuilder = new QueryBuilder();
        	final CriteriaQuery<Object> criteria = queryBuilder
        			.metamodel(manager.getEntityManagerFactory().getMetamodel())
        			.criteria(manager.getEntityManagerFactory().getCriteriaBuilder())
        			.entity(entity)
        			.paths(paths)
        			.build();
        	final JsonArray json = executeQuery(manager, criteria, firstResult, maxResults);
        	return Response.ok(json).build();
        }
    	throw new NotFoundException();
	}
	
	/**
	 * @param manager
	 * @param criteria
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	private JsonArray executeQuery(
    		final EntityManager manager, 
    		final CriteriaQuery<Object> criteria,
    		final Integer firstResult,
            final Integer maxResults){
		final TypedQuery<Object> query = manager.createQuery(criteria);
		if(firstResult != null){
            query.setFirstResult(firstResult);
        }
        if(maxResults != null){
            query.setMaxResults(maxResults);
        }
        return JsonUtil.toJsonArray(query.getResultList());
    }
}
