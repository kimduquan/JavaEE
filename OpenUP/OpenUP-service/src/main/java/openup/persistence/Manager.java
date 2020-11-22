/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.io.InputStream;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import openup.api.epf.schema.Roles;

/**
 *
 * @author FOXCONN
 */
@Path("persistence/entity")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.ANY_ROLE)
@RequestScoped
public class Manager implements openup.api.persistence.Entities {
    
    @Inject
    private Cache cache;
    
    @Context
    private SecurityContext context;
    
    @Override
    public Response persist(
            String name,
            String id,
            InputStream body
            ) throws Exception{
        ResponseBuilder response = Response.ok();
        Entity entity = findEntity(name, response);
        if(entity.getType() != null){
            try(Jsonb json = JsonbBuilder.create()){
                Object obj = json.fromJson(body, entity.getType().getJavaType());
                cache.persist(context.getUserPrincipal(), name, id, obj);
            }
            catch(JsonbException ex){
                response.status(Response.Status.BAD_REQUEST);
            }
        }
        return response.build();
    }
    
    @Override
    public Response find(
            String name,
            String id) throws Exception{
        ResponseBuilder response = Response.ok();
        Entity entity = findEntityObject(name, id, response);
        if(entity.getObject() != null){
            response.entity(entity.getObject());
        }
        return response.build();
    }
    
    @Override
    public Response remove(
            String name,
            String id
            ) throws Exception{
        ResponseBuilder response = Response.ok();
        Entity entity = findEntityObject(name, id, response);
        if(entity.getObject() != null){
            cache.remove(context.getUserPrincipal(), name, id, entity.getObject());
        }
        return response.build();
    }
    
    Entity findEntity(String name, ResponseBuilder response) throws Exception{
        Entity entity = cache.findEntity(context.getUserPrincipal(), name);
        if(entity.getType() == null)
            response.status(Response.Status.NOT_FOUND);
        return entity;
    }
    
    Entity findEntityObject(String name, String id, ResponseBuilder response) throws Exception{
        Entity entity = findEntity(name, response);
        if(entity.getType() != null){
            Object object = cache.find(context.getUserPrincipal(), name, entity.getType().getJavaType(), id);
            if(object != null){
                entity.setObject(object);
            }
            else{
                response.status(Response.Status.NOT_FOUND);
            }
        }
        return entity;
    }
}
