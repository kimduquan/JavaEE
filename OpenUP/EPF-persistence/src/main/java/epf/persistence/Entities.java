package epf.persistence;

import java.io.InputStream;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.SecurityContext;
import epf.naming.Naming;
import epf.util.json.JsonUtil;
import epf.persistence.internal.util.EntityTypeUtil;
import epf.persistence.internal.util.EntityUtil;

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
    transient Validator validator;
    
    /**
     * 
     */
    @Inject
    transient EntityManager manager;
    
    @Override
    @Transactional
    public JsonObject persist(
    		final String schema,
            final String name,
            final SecurityContext context,
            final InputStream body
            ) throws Exception{
    	final EntityType<?> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), name).orElseThrow(NotFoundException::new);
    	EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
    		if(!entitySchema.equals(schema)) {
    			throw new NotFoundException();
    		}
    	});
    	final Object entity = EntityUtil.toObject(entityType, body);
    	if(!validator.validate(entity).isEmpty()) {
        	throw new BadRequestException();
        }
        manager.persist(entity);
    	return JsonUtil.toJson(entity);
    }
    
    @Override
    @Transactional
	public void merge(
			final String schema,
			final String name, 
			final String id,
			final SecurityContext context,
			final InputStream body
			) throws Exception {
    	final EntityType<?> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), name).orElseThrow(NotFoundException::new);
    	EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
    		if(!entitySchema.equals(schema)) {
    			throw new NotFoundException();
    		}
    	});
    	final Object entityId = EntityUtil.getEntityId(entityType, id);
    	final Optional<Object> entityObject = Optional.ofNullable(manager.find(entityType.getJavaType(), entityId));
    	entityObject.orElseThrow(NotFoundException::new);
    	final Object entity = EntityUtil.toObject(entityType, body);
    	if(!validator.validate(entity).isEmpty()) {
        	throw new BadRequestException();
        }
    	manager.merge(entity);
	}
    
    @Override
    @Transactional
    public void remove(
    		final String schema,
    		final String name,
    		final String id,
    		final SecurityContext context
            ) throws Exception {
    	final EntityType<?> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), name).orElseThrow(NotFoundException::new);
    	EntityTypeUtil.getSchema(entityType).ifPresent(entitySchema -> {
    		if(!entitySchema.equals(schema)) {
    			throw new NotFoundException();
    		}
    	});
    	final Object entityId = EntityUtil.getEntityId(entityType, id);
    	final Optional<Object> entityObject = Optional.ofNullable(manager.getReference(entityType.getJavaType(), entityId));
    	JsonUtil.toJson(entityObject.orElseThrow(NotFoundException::new));
    	manager.remove(entityObject.orElseThrow(NotFoundException::new));
    }
}
