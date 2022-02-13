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
import epf.persistence.ext.EntityManager;
import epf.persistence.ext.EntityManagerFactory;
import epf.persistence.internal.util.PrincipalUtil;
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
    CompletionStage<Object> persist(final EntityManager manager, final Object entity){
    	return manager.persist(entity).thenApply(v -> entity);
    }
    
    /**
     * @param manager
     * @param entityType
     * @param entityId
     * @param body
     * @return
     */
    CompletionStage<Void> merge(final EntityManager manager, final EntityType<?> entityType, final Object entityId, final InputStream body){
    	return manager.find(entityType.getJavaType(), entityId)
    			.thenApply(entity -> Optional.ofNullable(entity).orElseThrow(NotFoundException::new))
    			.thenApply(entity -> EntityUtil.toObject(entityType, body))
    			.thenApply(entity -> {
    				validator.validate(entity);
    				return entity;
    			})
    			.thenCompose(entity -> manager.merge(entity))
    			.thenAccept(v -> {});
    }
    
    /**
     * @param manager
     * @param entityType
     * @param entityId
     * @return
     */
    CompletionStage<Void> remove(final EntityManager manager, final EntityType<?> entityType, final Object entityId){
    	return manager.find(entityType.getJavaType(), entityId)
    			.thenApply(entity -> Optional.ofNullable(entity).orElseThrow(NotFoundException::new))
    			.thenCompose(entity -> manager.remove(entity));
    }
    
    /**
     * @param manager
     * @param entityType
     * @param entityId
     * @return
     */
    CompletionStage<Object> find(final EntityManager manager, final EntityType<?> entityType, final Object entityId){
    	return manager.find(entityType.getJavaType(), entityId)
    			.thenApply(entity -> Optional.ofNullable(entity).orElseThrow(NotFoundException::new));
    }
    
    @Override
    @Transactional
    public CompletionStage<Object> persist(
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
        validator.validate(entity);
        final Map<String, Object> props = PrincipalUtil.getClaims(context.getUserPrincipal());
    	props.put(Naming.Persistence.Internal.SCHEMA, schema);
    	return factory.createEntityManager(props).thenCompose(
    			manager -> persist(manager, entity).thenCombine(manager.close(), (res, v) -> res)
    			);
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
    	EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
    		if(!entitySchema.equals(schema)) {
    			throw new NotFoundException();
    		}
    	});
    	final Object entityId = EntityUtil.getEntityId(entityType, id);
    	final Map<String, Object> props = PrincipalUtil.getClaims(context.getUserPrincipal());
    	props.put(Naming.Persistence.Internal.SCHEMA, schema);
    	return factory.createEntityManager(props).thenCompose(
    			manager -> merge(manager, entityType, entityId, body).thenCombine(manager.close(), (res, v) -> v)
    			);
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
    	EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
    		if(!entitySchema.equals(schema)) {
    			throw new NotFoundException();
    		}
    	});
    	final Object entityId = EntityUtil.getEntityId(entityType, id);
    	final Map<String, Object> props = PrincipalUtil.getClaims(context.getUserPrincipal());
    	props.put(Naming.Persistence.Internal.SCHEMA, schema);
    	return factory.createEntityManager(props).thenCompose(
    			manager -> remove(manager, entityType, entityId).thenCombine(manager.close(), (res, v) -> res)
    			);
    }
    
	@Override
	public CompletionStage<Object> find(final String schema, final String name, final String id, final SecurityContext context) {
		final EntityType<?> entityType = EntityTypeUtil.findEntityType(factory.getMetamodel(), name).orElseThrow(NotFoundException::new);
		EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
    		if(!entitySchema.equals(schema)) {
    			throw new NotFoundException();
    		}
    	});
		final Object entityId = EntityUtil.getEntityId(entityType, id);
    	final Map<String, Object> props = PrincipalUtil.getClaims(context.getUserPrincipal());
    	props.put(Naming.Persistence.Internal.SCHEMA, schema);
    	return factory.createEntityManager(props).thenCompose(
    			manager -> find(manager, entityType, entityId).thenCombine(manager.close(), (res, v) -> res)
    			);
	}
}
