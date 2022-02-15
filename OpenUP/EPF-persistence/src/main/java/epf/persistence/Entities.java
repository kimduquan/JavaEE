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
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import epf.naming.Naming;
import epf.persistence.ext.EntityManager;
import epf.persistence.ext.EntityManagerFactory;
import epf.persistence.internal.util.PrincipalUtil;
import epf.util.concurrent.StageUtil;
import epf.persistence.internal.util.EntityTypeUtil;
import epf.persistence.internal.util.EntityUtil;
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
    
    /**
     * @param manager
     * @param entity
     * @return
     */
    CompletionStage<Response> persist(final EntityManager manager, final Object entity){
    	return manager.persist(entity).thenApply(v -> {
    		manager.detach(entity);
    		return Response.ok(entity).build();
    	});
    }
    
    /**
     * @param manager
     * @param entityType
     * @param entityId
     * @param entity
     * @return
     */
    CompletionStage<Response> merge(final EntityManager manager, final EntityType<?> entityType, final Object entityId, final Object entity){
    	return manager.find(entityType.getJavaType(), entityId)
    			.thenApply(oldEntity -> Optional.ofNullable(oldEntity).orElseThrow(NotFoundException::new))
    			.thenCompose(oldEntity -> manager.merge(entity))
    			.thenApply(e -> Response.ok().build());
    }
    
    /**
     * @param manager
     * @param entityType
     * @param entityId
     * @return
     */
    CompletionStage<Response> remove(final EntityManager manager, final EntityType<?> entityType, final Object entityId){
    	return manager.find(entityType.getJavaType(), entityId)
    			.thenApply(entity -> Optional.ofNullable(entity).orElseThrow(NotFoundException::new))
    			.thenCompose(entity -> manager.remove(entity))
    			.thenApply(v -> Response.ok().build());
    }
    
    /**
     * @param manager
     * @param entityType
     * @param entityId
     * @return
     */
    CompletionStage<Response> find(final EntityManager manager, final EntityType<?> entityType, final Object entityId){
    	return manager.find(entityType.getJavaType(), entityId)
    			.thenApply(entity -> {
    				Optional.ofNullable(entity).orElseThrow(NotFoundException::new);
    				manager.detach(entity);
    				return Response.ok(entity).build();
    			});
    }
    
    @Override
    @Transactional
    public CompletionStage<Response> persist(
    		final String schema,
            final String name,
            final SecurityContext context,
            final InputStream body
            ) throws Exception{
    	final EntityType<?> entityType = EntityTypeUtil.findEntityType(factory.getMetamodel(), name).orElseThrow(NotFoundException::new);
    	EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
    		if(!entitySchema.equals(schema)) {
    			throw new NotFoundException();
    		}
    	});
    	final Object entity = EntityUtil.toObject(entityType, body);
    	if(!validator.validate(entity).isEmpty()) {
        	throw new BadRequestException();
        }
        final Map<String, Object> props = PrincipalUtil.getClaims(context.getUserPrincipal());
    	props.put(Naming.Persistence.Internal.SCHEMA, schema);
    	return StageUtil.stage(factory.createEntityManager(props), manager -> persist(manager, entity));
    }
    
    @Override
    @Transactional
	public CompletionStage<Response> merge(
			final String schema,
			final String name, 
			final String id,
			final SecurityContext context,
			final InputStream body
			) throws Exception {
    	final EntityType<?> entityType = EntityTypeUtil.findEntityType(factory.getMetamodel(), name).orElseThrow(NotFoundException::new);
    	EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
    		if(!entitySchema.equals(schema)) {
    			throw new NotFoundException();
    		}
    	});
    	final Object entity = EntityUtil.toObject(entityType, body);
    	if(!validator.validate(entity).isEmpty()) {
        	throw new BadRequestException();
        }
    	final Object entityId = EntityUtil.getEntityId(entityType, id);
    	final Map<String, Object> props = PrincipalUtil.getClaims(context.getUserPrincipal());
    	props.put(Naming.Persistence.Internal.SCHEMA, schema);
    	return StageUtil.stage(factory.createEntityManager(props), manager -> merge(manager, entityType, entityId, entity));
	}
    
    @Override
    @Transactional
    public CompletionStage<Response> remove(
    		final String schema,
    		final String name,
    		final String id,
    		final SecurityContext context
            ) {
    	final EntityType<?> entityType = EntityTypeUtil.findEntityType(factory.getMetamodel(), name).orElseThrow(NotFoundException::new);
    	EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
    		if(!entitySchema.equals(schema)) {
    			throw new NotFoundException();
    		}
    	});
    	final Object entityId = EntityUtil.getEntityId(entityType, id);
    	final Map<String, Object> props = PrincipalUtil.getClaims(context.getUserPrincipal());
    	props.put(Naming.Persistence.Internal.SCHEMA, schema);
    	return StageUtil.stage(factory.createEntityManager(props), manager -> remove(manager, entityType, entityId));
    }
    
	@Override
	@Transactional
	public CompletionStage<Response> find(final String schema, final String name, final String id, final SecurityContext context) {
		final EntityType<?> entityType = EntityTypeUtil.findEntityType(factory.getMetamodel(), name).orElseThrow(NotFoundException::new);
		EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
    		if(!entitySchema.equals(schema)) {
    			throw new NotFoundException();
    		}
    	});
		final Object entityId = EntityUtil.getEntityId(entityType, id);
    	final Map<String, Object> props = PrincipalUtil.getClaims(context.getUserPrincipal());
    	props.put(Naming.Persistence.Internal.SCHEMA, schema);
    	return StageUtil.stage(factory.createEntityManager(props), manager -> find(manager, entityType, entityId));
	}
}
