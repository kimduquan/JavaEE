package epf.persistence;

import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.SecurityContext;
import epf.naming.Naming;
import epf.persistence.internal.SessionStore;
import epf.persistence.internal.Session;
import epf.persistence.util.EntityTypeUtil;
import epf.persistence.util.EntityUtil;
import epf.util.concurrent.Stage;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.PERSISTENCE)
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
@ApplicationScoped
public class Entities implements epf.persistence.client.Entities {
    
    /**
     * 
     */
    @Inject
    private transient Validator validator;
    
    /**
     * 
     */
    @Inject
    private transient SessionStore sessionStore;
    
    @Override
    @Transactional
    public CompletionStage<Object> persist(
    		final String schema,
            final String name,
            final SecurityContext context,
            final InputStream body
            ) throws Exception{
    	final Session session = sessionStore.getSession(context).orElseThrow(ForbiddenException::new);
    	final EntityType<?> entityType = EntityTypeUtil.findEntityType(session.getPrincipal().getFactory().getMetamodel(), name).orElseThrow(NotFoundException::new);
    	final Object entity = EntityUtil.toObject(entityType, body);
        validator.validate(entity);
        return Stage.stage(session.getPrincipal().getFactory().createEntityManager())
        		.compose(manager -> manager.persist(entity))
        		.apply(v -> entity)
        		.complete();
    }
    
    @Override
    @Transactional
	public CompletionStage<Void> merge(
			final String schema,
			final String name, 
			final String id,
			final SecurityContext context,
			final InputStream body
			) throws Exception {
    	final Session session = sessionStore.getSession(context).orElseThrow(ForbiddenException::new);
    	final EntityType<?> entityType = EntityTypeUtil.findEntityType(session.getPrincipal().getFactory().getMetamodel(), name).orElseThrow(NotFoundException::new);
    	final Object entityId = EntityUtil.getEntityId(entityType, id);
    	return Stage.stage(session.getPrincipal().getFactory().createEntityManager())
    	.compose(manager -> manager.find(entityType.getJavaType(), entityId))
    	.apply(entity -> Optional.ofNullable(entity).orElseThrow(NotFoundException::new))
		.apply(entity -> EntityUtil.toObject(entityType, body))
		.apply(entity -> {
			validator.validate(entity); 
			return entity;
			})
		.stage((manager, entity) -> manager.merge(entity))
		.accept()
		.complete();
	}
    
    @Override
    @Transactional
    public CompletionStage<Void> remove(
    		final String schema,
    		final String name,
    		final String id,
    		final SecurityContext context
            ) {
    	final Session session = sessionStore.getSession(context).orElseThrow(ForbiddenException::new);
    	final EntityType<?> entityType = EntityTypeUtil.findEntityType(session.getPrincipal().getFactory().getMetamodel(), name).orElseThrow(NotFoundException::new);
    	final Object entityId = EntityUtil.getEntityId(entityType, id);
    	return Stage.stage(session.getPrincipal().getFactory().createEntityManager())
    	.compose(manager -> manager.find(entityType.getJavaType(), entityId))
    	.apply(entity -> Optional.ofNullable(entity).orElseThrow(NotFoundException::new))
    	.stage((manager, entity) -> manager.remove(entity))
    	.accept()
    	.complete();
    }
    
	@Override
	public CompletionStage<Object> find(final String schema, final String name, final String id, final SecurityContext context) {
		final Session session = sessionStore.getSession(context).orElseThrow(ForbiddenException::new);
    	final EntityType<?> entityType = EntityTypeUtil.findEntityType(session.getPrincipal().getFactory().getMetamodel(), name).orElseThrow(NotFoundException::new);
    	final Object entityId = EntityUtil.getEntityId(entityType, id);
    	return Stage.stage(session.getPrincipal().getFactory().createEntityManager())
    			.compose(manager -> manager.find(entityType.getJavaType(), entityId))
    			.apply(entity -> (Object)entity)
    			.complete();
	}
}
