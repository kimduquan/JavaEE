/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import openup.schema.Role;
import java.io.InputStream;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
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
@Path("persistence")
@RolesAllowed(Role.ANY_ROLE)
@RequestScoped
public class Entities implements openup.client.persistence.Entities {
    
    @Inject
    private Request cache;
    
    @Context
    private SecurityContext context;
    
    @Override
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
    public void persist(
            String unit,
            String name,
            String id,
            InputStream body
            ) throws Exception{
        Entity entity = findEntity(unit, name);
        if(entity.getType() != null){
            try(Jsonb json = JsonbBuilder.create()){
                Object obj = json.fromJson(body, entity.getType().getJavaType());
                cache.persist(unit, context.getUserPrincipal(), name, id, obj);
            }
            catch(JsonbException ex){
                throw new BadRequestException();
            }
        }
    }
    
    Object find(
            String unit,
            String name,
            String id) throws Exception{
        Entity entity = findEntityObject(unit, name, id);
        return entity.getObject();
    }
    
    @Override
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
    public void remove(
            String unit,
            String name,
            String id
            ) throws Exception{
        Entity entity = findEntityObject(unit, name, id);
        if(entity.getObject() != null){
            cache.remove(unit, context.getUserPrincipal(), name, id, entity.getObject());
        }
    }
    
    Entity findEntity(String unit, String name) throws Exception{
        Entity entity = cache.findEntity(unit, context.getUserPrincipal(), name);
        if(entity.getType() == null){
            throw new NotFoundException();
        }
        return entity;
    }
    
    Entity findEntityObject(String unit, String name, String id) throws Exception{
        Entity entity = findEntity(unit, name);
        if(entity.getType() != null){
            Object object = cache.find(unit, context.getUserPrincipal(), name, entity.getType().getJavaType(), id);
            if(object != null){
                entity.setObject(object);
            }
            else{
                throw new NotFoundException();
            }
        }
        return entity;
    }
}
