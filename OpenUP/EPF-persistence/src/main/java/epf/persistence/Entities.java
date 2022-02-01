package epf.persistence;

import java.io.InputStream;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.persistence.metamodel.EntityType;
import javax.validation.Validator;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import epf.naming.Naming;
import epf.persistence.internal.Session;

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
    private transient Request request;
    

    
    /**
     * @param entityType
     * @param body
     * @return
     * @throws Exception
     */
    protected Object toObject(final EntityType<?> entityType, final InputStream body) throws Exception {
    	try(Jsonb json = JsonbBuilder.create()){
        	return json.fromJson(body, entityType.getJavaType());
        }
    	catch(JsonbException ex){
        	throw new BadRequestException(ex);
        }
    }
    
    @Override
    public Object persist(
    		final String schema,
            final String name,
            final SecurityContext context,
            final InputStream body
            ) throws Exception{
    	final Session session = request.getSession(context);
    	final EntityType<?> entityType = request.getEntityType(session, name);
    	final Object entity = toObject(entityType, body);
        validator.validate(entity);
        final Object object = request.persistEntity(session, entity);
        return object;
    }
    
    @Override
	public void merge(
			final String schema,
			final String name, 
			final String id,
			final SecurityContext context,
			final InputStream body
			) throws Exception {
    	final Session session = request.getSession(context);
    	final EntityType<?> entityType = request.getEntityType(session, name);
    	final Object entityId = request.getEntityId(entityType, id);
    	request.getEntity(session, entityType, entityId);
    	final Object entity = toObject(entityType, body);
    	validator.validate(entity);
    	request.mergeEntity(session, entity);
	}
    
    @Override
    public void remove(
    		final String schema,
    		final String name,
    		final String id,
    		final SecurityContext context
            ) {
    	final Session session = request.getSession(context);
    	final EntityType<?> entityType = request.getEntityType(session, name);
    	final Object entityId = request.getEntityId(entityType, id);
    	request.removeEntity(session, entityType, entityId).orElseThrow(NotFoundException::new);
    }
    
	@Override
	public Response find(final String schema, final String name, final String id, final SecurityContext context) {
		final Session session = request.getSession(context);
    	final EntityType<?> entityType = request.getEntityType(session, name);
    	final Object entityId = request.getEntityId(entityType, id);
    	final Object entity = request.getEntity(session, entityType, entityId);
		return Response.ok(entity).build();
	}
}
