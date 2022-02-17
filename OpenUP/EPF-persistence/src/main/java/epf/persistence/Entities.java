package epf.persistence;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.SecurityContext;
import epf.naming.Naming;
import epf.persistence.ext.EntityManager;
import epf.persistence.ext.EntityManagerFactory;
import epf.persistence.internal.util.PrincipalUtil;
import epf.util.json.JsonUtil;
import epf.util.logging.LogManager;
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
	private transient static final Logger LOGGER = LogManager.getLogger(Entities.class.getName());
    
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
    CompletionStage<JsonObject> persist(final EntityManager manager, final Object entity){
    	manager.joinTransaction();
    	return manager.persist(entity).thenApply(v -> {
    		try {
				return JsonUtil.toJson(entity);
			} 
    		catch (Exception e) {
				LOGGER.log(Level.SEVERE, "persist", e);
				return JsonUtil.empty();
			}
    	});
    }
    
    /**
     * @param manager
     * @param entityType
     * @param entityId
     * @param entity
     * @return
     */
    CompletionStage<Void> merge(final EntityManager manager, final EntityType<?> entityType, final Object entityId, final Object entity){
    	manager.joinTransaction();
    	return manager.find(
    			entityType.getJavaType(), 
    			entityId, 
    			foundEntity -> Optional.ofNullable(foundEntity).orElseThrow(NotFoundException::new)
    			)
    			.thenCompose(
    					foundEntity -> manager.merge(entity, e -> e)
    					)
    			.thenAccept(obj -> {});
    }
    
    /**
     * @param manager
     * @param entityType
     * @param entityId
     * @return
     */
    CompletionStage<Void> remove(final EntityManager manager, final EntityType<?> entityType, final Object entityId){
    	manager.joinTransaction();
    	final CompletionStage<Object> oldEntity = manager.find(entityType.getJavaType(), entityId, entity -> Optional.ofNullable(entity).orElseThrow(NotFoundException::new));
    	return oldEntity.thenCompose(entity -> manager.remove(entity));
    }
    
    /**
     * @param manager
     * @param entityType
     * @param entityId
     * @return
     */
    CompletionStage<JsonObject> find(final EntityManager manager, final EntityType<?> entityType, final Object entityId){
    	return manager.find(
    			entityType.getJavaType(), 
    			entityId, 
    			entity -> {
    				Optional.ofNullable(entity).orElseThrow(NotFoundException::new);
		    		try {
		        		return JsonUtil.toJson(entity);
		        		}
		    		catch (Exception e) {
		    			LOGGER.log(Level.SEVERE, "find", e);
						return JsonUtil.empty();
						}
		    		}
    			);
    }
    
    @Override
    @Transactional
    public CompletionStage<JsonObject> persist(
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
    	final EntityManager manager = factory.createEntityManager(props);
    	return persist(manager, entity);
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
    	final Object entity = EntityUtil.toObject(entityType, body);
    	if(!validator.validate(entity).isEmpty()) {
        	throw new BadRequestException();
        }
    	final Object entityId = EntityUtil.getEntityId(entityType, id);
    	final Map<String, Object> props = PrincipalUtil.getClaims(context.getUserPrincipal());
    	props.put(Naming.Persistence.Internal.SCHEMA, schema);
    	final EntityManager manager = factory.createEntityManager(props);
    	return merge(manager, entityType, entityId, entity);
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
    	final EntityManager manager = factory.createEntityManager(props);
    	return remove(manager, entityType, entityId);
    }
    
	@Override
	@Transactional
	public CompletionStage<JsonObject> find(final String schema, final String name, final String id, final SecurityContext context) {
		final EntityType<?> entityType = EntityTypeUtil.findEntityType(factory.getMetamodel(), name).orElseThrow(NotFoundException::new);
		EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
    		if(!entitySchema.equals(schema)) {
    			throw new NotFoundException();
    		}
    	});
		final Object entityId = EntityUtil.getEntityId(entityType, id);
    	final Map<String, Object> props = PrincipalUtil.getClaims(context.getUserPrincipal());
    	props.put(Naming.Persistence.Internal.SCHEMA, schema);
    	final EntityManager manager = factory.createEntityManager(props);
    	return find(manager, entityType, entityId);
	}
}
