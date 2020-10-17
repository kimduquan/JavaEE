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
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import openup.Roles;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
@Path("persistence/entity")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.ANY_ROLE)
public class Manager {
    
    @Inject
    private Cache cache;
    
    @Context
    private SecurityContext context;
    
    @POST
    @Path("{entity}/{id}")
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
            description = "ok",
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
    public Response persist(
            @PathParam("entity")
            String name,
            @PathParam("id")
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
    
    @GET
    @Path("{entity}/{id}")
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
    public Response find(
            @PathParam("entity")
            String name,
            @PathParam("id")
            String id) throws Exception{
        ResponseBuilder response = Response.ok();
        Entity entity = findEntityObject(name, id, response);
        if(entity.getObject() != null){
            response.entity(entity.getObject());
        }
        return response.build();
    }
    
    @DELETE
    @Path("{entity}/{id}")
    @Operation(
            summary = "remove", 
            description = "Remove the entity instance."
    )
    @APIResponse(
            description = "ok",
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
    public Response remove(
            @PathParam("entity")
            String name,
            @PathParam("id")
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
