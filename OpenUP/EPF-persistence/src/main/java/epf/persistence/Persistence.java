package epf.persistence;

import java.io.InputStream;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import epf.naming.Naming;
import epf.persistence.util.EntityTypeUtil;
import epf.persistence.util.EntityUtil;
import epf.util.json.JsonUtil;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.PERSISTENCE)
@ApplicationScoped
public class Persistence implements epf.persistence.client.Entities {
    
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
    public Response persist(
    		final String schema,
            final String name,
            final InputStream body
            ) throws Exception{
    	final Optional<EntityType<?>> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), name);
    	if(entityType.isEmpty()) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Optional<String> entitySchema = EntityTypeUtil.getSchema(entityType.get());
    	if(entitySchema.isPresent() && !entitySchema.get().equals(schema)) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Object entity = EntityUtil.toObject(entityType.get(), body);
    	if(!validator.validate(entity).isEmpty()) {
    		return Response.status(Response.Status.BAD_REQUEST).build();
        }
        manager.persist(entity);
    	return Response.ok(JsonUtil.toJson(entity)).build();
    }
    
    @Override
    @Transactional
	public Response merge(
			final String schema,
			final String name, 
			final String id,
			final InputStream body
			) throws Exception {
    	final Optional<EntityType<?>> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), name);
    	if(entityType.isEmpty()) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Optional<String> entitySchema = EntityTypeUtil.getSchema(entityType.get());
    	if(entitySchema.isPresent() && !entitySchema.get().equals(schema)) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Object entityId = EntityUtil.getEntityId(entityType.get(), id);
    	final Object entityObject = manager.find(entityType.get().getJavaType(), entityId);
    	if(entityObject == null) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Object entity = EntityUtil.toObject(entityType.get(), body);
    	if(!validator.validate(entity).isEmpty()) {
    		return Response.status(Response.Status.BAD_REQUEST).build();
        }
    	return Response.ok(manager.merge(entity)).build();
	}
    
    @Override
    @Transactional
    public Response remove(
    		final String schema,
    		final String name,
    		final String id
            ) throws Exception {
    	final Optional<EntityType<?>> entityType = EntityTypeUtil.findEntityType(manager.getMetamodel(), name);
    	if(entityType.isEmpty()) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Optional<String> entitySchema = EntityTypeUtil.getSchema(entityType.get());
    	if(entitySchema.isPresent() && !entitySchema.get().equals(schema)) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	final Object entityId = EntityUtil.getEntityId(entityType.get(), id);
    	final Object entityObject = manager.find(entityType.get().getJavaType(), entityId);
    	if(entityObject == null) {
    		return Response.status(Response.Status.NOT_FOUND).build();
    	}
    	JsonUtil.toString(entityObject);
    	manager.remove(entityObject);
    	return Response.ok().build();
    }
}
