package epf.persistence;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.EntityType;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.SecurityContext;
import epf.naming.Naming;
import epf.persistence.internal.Session;
import epf.persistence.util.Entity;
import epf.persistence.util.QueryBuilder;
import epf.persistence.util.QueryUtil;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.PERSISTENCE)
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
@ApplicationScoped
public class Queries implements epf.persistence.client.Queries {
    
    /**
     * 
     */
    @Inject
    private transient Request request;
    
    @Override
    public Response executeQuery(
    		final String schema,
            final List<PathSegment> paths,
            final Integer firstResult,
            final Integer maxResults,
            final SecurityContext context
            ) {
    	final Session session = request.getSession(context);
    	final Entity<Object> entity = new Entity<>();
    	if(!paths.isEmpty()){
        	final PathSegment rootSegment = paths.get(0);
        	final String entityName = rootSegment.getPath();
        	@SuppressWarnings("unchecked")
			final EntityType<Object> entityType = (EntityType<Object>) request.getEntityType(session, entityName);
        	entity.setType(entityType);
        	return session.peekManager(entityManager -> {
        		final Query query = buildQuery(entityManager, entity, paths);
            	final ResponseBuilder response = QueryUtil.collectResult(query, firstResult, maxResults, Response.ok());
                return response;
        	})
        	.get()
        	.build();
        }
    	throw new NotFoundException();
    }
    
    /**
     * @param entityManager
     * @param entity
     * @param paths
     * @return
     */
    protected static Query buildQuery(
    		final EntityManager entityManager, 
    		final Entity<Object> entity, 
    		final List<PathSegment> paths) {
    	final QueryBuilder queryBuilder = new QueryBuilder();
    	final CriteriaQuery<Object> criteria = queryBuilder
    			.metamodel(entityManager.getMetamodel())
    			.criteria(entityManager.getCriteriaBuilder())
    			.entity(entity)
    			.paths(paths)
    			.build();
    	return entityManager.createQuery(criteria);
    }
}
