/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence;

import java.io.InputStream;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.validation.Validator;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import epf.client.EPFException;
import epf.client.security.Security;
import epf.persistence.impl.Entity;

/**
 *
 * @author FOXCONN
 */
@Path("persistence")
@RolesAllowed(Security.DEFAULT_ROLE)
@RequestScoped
public class Entities implements epf.client.persistence.Entities {
	
	/**
	 * 
	 */
	@Inject
	private transient Logger logger;
    
    /**
     * 
     */
    @Inject
    private transient Request cache;
    
    /**
     * 
     */
    @Inject
    private transient Validator validator;
    
    /**
     * 
     */
    @Context
    private transient SecurityContext context;
    
    @Override
    public Object persist(
    		final String schema,
            final String name,
            final InputStream body
            ){
    	final Entity<Object> entity = findEntity(schema, name);
    	Object object = null;
        if(entity.getType() != null){
            try(Jsonb json = JsonbBuilder.create()){
            	final Object obj = json.fromJson(body, entity.getType().getJavaType());
                validator.validate(obj);
                object = cache.persist(context.getUserPrincipal(), schema, name, obj);
            }
            catch(JsonbException ex){
            	throw new BadRequestException(ex);
            }
            catch(Exception ex) {
            	throw new EPFException(ex);
            }
        }
        return object;
    }
    
    @Override
	public void merge(
			final String schema,
			final String name, 
			final String entityId,
			final InputStream body
			) {
    	final Entity<Object> entity = findEntityObject(schema, name, entityId);
        if(entity.getObject() != null){
            try(Jsonb json = JsonbBuilder.create()){
            	final Object obj = json.fromJson(body, entity.getType().getJavaType());
                validator.validate(obj);
                cache.merge(context.getUserPrincipal(), schema, name, entityId, obj);
            }
            catch(JsonbException ex){
            	logger.throwing(getClass().getName(), "merge", ex);
                throw new BadRequestException(ex);
            }
            catch(Exception ex) {
            	throw new EPFException(ex);
            }
        }
	}
    
    @Override
    public void remove(
    		final String schema,
    		final String name,
    		final String entityId
            ) {
    	final Entity<Object> entity = findEntityObject(schema, name, entityId);
        if(entity.getObject() != null){
            cache.remove(context.getUserPrincipal(), schema, name, entityId, entity.getObject());
        }
    }
    
    /**
     * @param <T>
     * @param schema
     * @param name
     * @return
     */
    protected <T> Entity<T> findEntity(final String schema, final String name) {
    	final Entity<T> entity = cache.findEntity(context.getUserPrincipal(), schema, name);
        if(entity.getType() == null){
            throw new NotFoundException();
        }
        return entity;
    }
    
    protected <T> Entity<T> findEntityObject(final String schema, final String name, final String entityId) {
    	final Entity<T> entity = findEntity(schema, name);
        if(entity.getType() != null){
        	final T object = cache.find(context.getUserPrincipal(), schema, name, entity.getType().getJavaType(), entityId);
            if(object == null){
            	throw new NotFoundException();
            }
            entity.setObject(object);
        }
        return entity;
    }

	@Override
	public Response find(final String schema, final String name, final String entityId) {
		final Entity<Object> entity = findEntityObject(schema, name, entityId);
		return Response.ok(entity.getObject()).build();
	}
}
