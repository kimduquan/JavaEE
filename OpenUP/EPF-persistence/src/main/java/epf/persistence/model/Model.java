/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.persistence.model;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;
import epf.persistence.Request;
import epf.persistence.impl.Entity;
import epf.schema.roles.Role;

/**
 *
 * @author FOXCONN
 */
@Path("model")
@RolesAllowed(Role.DEFAULT_ROLE)
@RequestScoped
public class Model implements epf.client.model.Model {
    
    /**
     * 
     */
    @Inject
    private transient Request cache;
    
    /**
     * 
     */
    @Context
    private transient SecurityContext context;
    
    /**
     * @param <T>
     * @param unit
     * @param name
     * @return
     */
    protected <T> Entity<T> findEntity(final String unit, final String name) {
    	final Entity<T> entity = cache.findEntity(unit, context.getUserPrincipal(), name);
        if(entity.getType() == null){
            throw new NotFoundException();
        }
        return entity;
    }
    
    /**
     * @param <T>
     * @param unit
     * @return
     */
    protected <T> List<Entity<T>> findEntities(final String unit){
    	final List<Entity<T>> entities = cache.findEntities(unit, context.getUserPrincipal());
    	if(entities.isEmpty()){
            throw new NotFoundException();
        }
        return entities;
    }

	@Override
	public Response getEntityType(final String unit, final String name) {
		final EntityBuilder builder = new EntityBuilder();
		final ResponseBuilder response = Response.ok(builder.build(findEntity(unit, name)));
		return response.build();
	}

	@Override
	public Response getEntityTypes(final String unit) {
		final EntityBuilder builder = new EntityBuilder();
		final List<epf.client.model.Entity> entityTypes = findEntities(unit)
				.stream()
				.map(builder::build)
				.collect(Collectors.toList());
		return Response.ok(entityTypes).build();
	}
}
