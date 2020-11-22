/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.io.InputStream;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
public class Manager implements openup.api.persistence.Entities {
    
    @Inject
    private Cache cache;
    
    @Context
    private SecurityContext context;
    
    
    @Operation(
            summary = "persist", 
            description = "Make an instance managed and persistent."
    )
    @RequestBody(
            description = "entity instance",
            content = @Content(mediaType = MediaType.APPLICATION_JSON),
            required = true
    )
    @APIResponse(
            description = "OK",
            responseCode = "200"
    )
    @APIResponse(
            description = "entity name is not present",
            responseCode = "404"
    )
    @APIResponse(
            description = "any unexpected error(s) occur(s) during deserialization",
            responseCode = "400"
    )
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
    
    
    @Operation(
            summary = "Find by primary key.", 
            description = "Search for an entity of the specified class and primary key."
    )
    @APIResponse(
            responseCode = "200",
            description = "the found entity instance",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON
            )
    )
    @APIResponse(
            description = "entity name is not present",
            responseCode = "404"
    )
    @APIResponse(
            description = "the entity does not exist",
            responseCode = "404"
    )
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
    
    
    @Operation(
            summary = "remove", 
            description = "Remove the entity instance."
    )
    @APIResponse(
            description = "OK",
            responseCode = "200"
    )
    @APIResponse(
            description = "entity name is not present",
            responseCode = "404"
    )
    @APIResponse(
            description = "the entity does not exist",
            responseCode = "404"
    )
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
