package epf.persistence;

import java.util.List;
import java.util.concurrent.CompletionStage;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.EntityType;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.SecurityContext;
import epf.naming.Naming;
import epf.persistence.internal.SessionStore;
import epf.persistence.internal.Session;
import epf.persistence.util.Entity;
import epf.persistence.util.EntityTypeUtil;
import epf.persistence.util.QueryBuilder;
import epf.util.concurrent.Stage;

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
    private transient SessionStore sessionStore;
    
    @Override
    public CompletionStage<List<Object>> executeQuery(
    		final String schema,
            final List<PathSegment> paths,
            final Integer firstResult,
            final Integer maxResults,
            final SecurityContext context
            ) {
    	final Session session = sessionStore.getSession(context).orElseThrow(ForbiddenException::new);
    	final Entity<Object> entity = new Entity<>();
    	if(!paths.isEmpty()){
        	final PathSegment rootSegment = paths.get(0);
        	final String entityName = rootSegment.getPath();
        	@SuppressWarnings("unchecked")
			final EntityType<Object> entityType = (EntityType<Object>) EntityTypeUtil.findEntityType(session.getPrincipal().getFactory().getMetamodel(), entityName).orElseThrow(NotFoundException::new);
        	entity.setType(entityType);
        	final QueryBuilder queryBuilder = new QueryBuilder();
        	final CriteriaQuery<Object> criteria = queryBuilder
        			.metamodel(session.getPrincipal().getFactory().getMetamodel())
        			.criteria(session.getPrincipal().getFactory().getCriteriaBuilder())
        			.entity(entity)
        			.paths(paths)
        			.build();
        	return Stage.stage(session.getPrincipal().getFactory().createEntityManager())
        	.apply(entityManager -> entityManager.createQuery(criteria))
        	.compose(query -> {
        		if(firstResult != null){
                    query.setFirstResult(firstResult);
                }
                if(maxResults != null){
                    query.setMaxResults(maxResults);
                }
                return query.getResultList();
                }
        	)
        	.complete();
        }
    	throw new NotFoundException();
    }
}
