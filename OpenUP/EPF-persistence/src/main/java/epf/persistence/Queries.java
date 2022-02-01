package epf.persistence;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.persistence.Query;
import javax.persistence.metamodel.EntityType;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.SecurityContext;
import epf.naming.Naming;
import epf.persistence.internal.Entity;
import epf.persistence.internal.QueryBuilder;
import epf.persistence.internal.Session;

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
            	final ResponseBuilder response = Response.ok();
                return collectQueryResult(query, firstResult, maxResults, response);
        	})
        	.get()
        	.build();
        }
    	throw new NotFoundException();
    }
    
    /**
     * @param query
     * @param firstResult
     * @param maxResults
     * @param response
     * @return
     */
    protected static ResponseBuilder collectQueryResult(
    		final Query query,
    		final Integer firstResult,
            final Integer maxResults,
    		final ResponseBuilder response
    		) {
    	if(firstResult != null){
            query.setFirstResult(firstResult);
        }
        if(maxResults != null){
            query.setMaxResults(maxResults);
        }
        final Stream<?> result = query.getResultStream();
        response.status(Status.OK).entity(
                    		result
                            .collect(Collectors.toList())
        );
        return response;
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
    	return queryBuilder
    			.manager(entityManager)
    			.entity(entity)
    			.paths(paths)
    			.build();
    }
}
