/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import java.io.InputStream;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
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
@Path("persistence")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.ADMIN)
public class Manager {
    
    @Inject
    private EntityManager manager;
    
    @GET
    @Path("{query}")
    @Operation(
            summary = "Named Query", 
            description = "Execute a SELECT query and return the query results."
    )
    @APIResponse(
            description = "Result",
            responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON
            )
    )
    @APIResponse(
            description = "a query has not been defined with the given name",
            responseCode = "404"
    )
    public Response query(
            @PathParam("query")
            String name,
            @MatrixParam("first")
            Integer firstResult,
            @MatrixParam("max")
            Integer maxResults,
            @Context 
            UriInfo uriInfo
            ){
        ResponseBuilder response = Response.ok();
        Query query = null;
        try{
            query = manager.createNamedQuery(name);
        }
        catch(IllegalArgumentException ex){
            response.status(Response.Status.NOT_FOUND);
        }
        if(query != null){
            buildQuery(query, firstResult, maxResults, uriInfo);
            response.entity(query.getResultStream().collect(Collectors.toList()));
        }
        return response.build();
    }
    
    @POST
    @Path("{entity}")
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
    @Transactional
    public Response persist(
            @PathParam("entity")
            String name,
            InputStream body
            ) throws Exception{
        ResponseBuilder response = Response.ok();
        Entity entity = new Entity();
        findEntity(manager, name, entity, response);
        if(entity.getType() != null){
            try(Jsonb json = JsonbBuilder.create()){
                Object obj = json.fromJson(body, entity.getType().getJavaType());
                manager.persist(obj);
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
            String id){
        ResponseBuilder response = Response.ok();
        Entity entity = new Entity();
        findEntityObject(manager, name, id, entity, response);
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
    @Transactional
    public Response remove(
            @PathParam("entity")
            String name,
            @PathParam("id")
            String id
            ){
        ResponseBuilder response = Response.ok();
        Entity entity = new Entity();
        findEntityObject(manager, name, id, entity, response);
        if(entity.getObject() != null){
            manager.remove(entity.getObject());
        }
        return response.build();
    }
    
    void findEntity(EntityManager manager, String name, Entity entity, ResponseBuilder response){
        manager.getMetamodel()
                .getEntities()
                .stream()
                .filter(
                        entityType -> {
                            return entityType.getName().equals(name);
                        }
                )
                .findFirst()
                .ifPresent(entity::setType);
        if(entity.getType() == null)
            response.status(Response.Status.NOT_FOUND);
    }
    
    void findEntityObject(EntityManager manager, String name, String id, Entity entity, ResponseBuilder response){
        findEntity(manager, name, entity, response);
        if(entity.getType() != null){
            Object object = manager.find(entity.getType().getJavaType(), id);
            if(object != null){
                entity.setObject(object);
            }
            else{
                response.status(Response.Status.NOT_FOUND);
            }
        }
    }
    
    void buildQuery(Query query, Integer firstResult, Integer maxResults, UriInfo uriInfo){
        uriInfo.getQueryParameters().forEach((param, paramValue) -> {
            String value = "";
            if(!paramValue.isEmpty()){
                value = paramValue.get(0);
            }
            query.setParameter(param, value);
        });
        if(firstResult != null){
            query.setFirstResult(firstResult);
        }
        if(maxResults != null){
            query.setMaxResults(maxResults);
        }
    }
}
