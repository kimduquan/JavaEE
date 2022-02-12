package epf.persistence;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.SecurityContext;
import epf.naming.Naming;
import epf.persistence.ext.EntityManagerFactory;
import epf.persistence.internal.util.PrincipalUtil;
import epf.persistence.internal.util.EntityTypeUtil;
import epf.persistence.internal.util.EntityUtil;
import epf.util.concurrent.Stage;
import io.smallrye.common.annotation.NonBlocking;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.PERSISTENCE)
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
@ApplicationScoped
@NonBlocking
public class Entities implements epf.persistence.client.Entities {
    
    /**
     * 
     */
    @Inject
    transient Validator validator;
    
    /**
     * 
     */
    @Inject
    transient EntityManagerFactory factory;
    
    @Override
    @Transactional
    public CompletionStage<Object> persist(
    		final String schema,
            final String name,
            final SecurityContext context,
            final InputStream body
            ) throws Exception{
    	final EntityType<?> entityType = EntityTypeUtil.findEntityType(factory.getMetamodel(), name).orElseThrow(NotFoundException::new);
    	final Object entity = EntityUtil.toObject(entityType, body);
        validator.validate(entity);
        final Map<String, Object> claims = PrincipalUtil.getClaims(context.getUserPrincipal());
        return Stage.stage(factory.createEntityManager(claims))
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
    	final EntityType<?> entityType = EntityTypeUtil.findEntityType(factory.getMetamodel(), name).orElseThrow(NotFoundException::new);
    	final Object entityId = EntityUtil.getEntityId(entityType, id);
    	final Map<String, Object> claims = PrincipalUtil.getClaims(context.getUserPrincipal());
    	return Stage.stage(factory.createEntityManager(claims))
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
    	final EntityType<?> entityType = EntityTypeUtil.findEntityType(factory.getMetamodel(), name).orElseThrow(NotFoundException::new);
    	final Object entityId = EntityUtil.getEntityId(entityType, id);
    	final Map<String, Object> claims = PrincipalUtil.getClaims(context.getUserPrincipal());
    	return Stage.stage(factory.createEntityManager(claims))
    	.compose(manager -> manager.find(entityType.getJavaType(), entityId))
    	.apply(entity -> Optional.ofNullable(entity).orElseThrow(NotFoundException::new))
    	.stage((manager, entity) -> manager.remove(entity))
    	.accept()
    	.complete();
    }
    
	@Override
	public CompletionStage<Object> find(final String schema, final String name, final String id, final SecurityContext context) {
		final EntityType<?> entityType = EntityTypeUtil.findEntityType(factory.getMetamodel(), name).orElseThrow(NotFoundException::new);
    	final Object entityId = EntityUtil.getEntityId(entityType, id);
    	final Map<String, Object> claims = PrincipalUtil.getClaims(context.getUserPrincipal());
    	return Stage.stage(factory.createEntityManager(claims))
    			.compose(manager -> manager.find(entityType.getJavaType(), entityId))
    			.apply(entity -> (Object)entity)
    			.complete();
	}
}
