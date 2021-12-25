package epf.persistence;

import java.io.InputStream;
import java.util.Map;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.persistence.metamodel.EntityType;
import javax.validation.Validator;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import epf.naming.Naming;
import epf.persistence.internal.Session;
import epf.util.MapUtil;
import io.opentracing.Tracer;
import io.opentracing.log.Fields;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.PERSISTENCE)
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
@RequestScoped
public class Entities implements epf.client.persistence.Entities {
    
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
     * 
     */
    @Inject
    private transient Tracer tracer;
    
    /**
     * 
     */
    @Context
    private transient SecurityContext context;
    

    
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
            final InputStream body
            ) throws Exception{
    	final Session session = request.getSession(context);
    	final EntityType<?> entityType = request.getEntityType(session, name);
    	final Object entity = toObject(entityType, body);
        validator.validate(entity);
        final Object object = request.persistEntity(session, entity);
        
        final Map<String, Object> fields = MapUtil.of(Fields.EVENT, "persistence.persist");
        fields.put("schema", schema);
        fields.put("entity", name);
        tracer.activeSpan().log(fields);
        return object;
    }
    
    @Override
	public void merge(
			final String schema,
			final String name, 
			final String entityId,
			final InputStream body
			) throws Exception {
    	final Session session = request.getSession(context);
    	final EntityType<?> entityType = request.getEntityType(session, name);
    	request.getEntity(session, entityType, entityId);
    	final Object entity = toObject(entityType, body);
    	validator.validate(entity);
    	request.mergeEntity(session, entity);
    	
    	final Map<String, Object> fields = MapUtil.of(Fields.EVENT, "persistence.persist");
        fields.put("schema", schema);
        fields.put("entity", name);
    	fields.put("entity.id", entityId);
        tracer.activeSpan().log(fields);
	}
    
    @Override
    public void remove(
    		final String schema,
    		final String name,
    		final String entityId
            ) {
    	final Session session = request.getSession(context);
    	final EntityType<?> entityType = request.getEntityType(session, name);
    	request.removeEntity(session, entityType, entityId)
    	.orElseThrow(NotFoundException::new);
    	
    	final Map<String, Object> fields = MapUtil.of(Fields.EVENT, "persistence.persist");
        fields.put("schema", schema);
        fields.put("entity", name);
    	fields.put("entity.id", entityId);
        tracer.activeSpan().log(fields);
    }
    
	@Override
	public Response find(final String schema, final String name, final String entityId) {
		final Session session = request.getSession(context);
    	final EntityType<?> entityType = request.getEntityType(session, name);
    	final Object entity = request.getEntity(session, entityType, entityId);
		return Response.ok(entity).build();
	}
}
