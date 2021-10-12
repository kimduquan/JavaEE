/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.persistence;

import java.io.InputStream;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import epf.client.util.Client;
import epf.naming.Naming;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.PERSISTENCE)
public interface Entities {
    
    /**
     * @param schema
     * @param entity
     * @param body
     * @return
     */
    @POST
    @Path("{schema}/{entity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Object persist(
    		@PathParam("schema")
            @NotBlank
            final String schema,
            @PathParam("entity")
            @NotBlank
            final String entity,
            @NotNull
            final InputStream body
            );
    
    /**
     * @param client
     * @param cls
     * @param schema
     * @param entity
     * @param body
     */
    static <T> T persist(
    		final Client client,
    		final Class<T> cls,
    		final String schema,
    		final String entity,
    		final T body
            ){
    	return client.request(
    			target -> target.path(schema).path(entity), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.json(body), cls);
    }
    
    /**
     * @param client
     * @param schema
     * @param entity
     * @param body
     */
    static Response persist(
    		final Client client,
    		final String schema,
    		final String entity,
    		final String body
            ){
    	return client.request(
    			target -> target.path(schema).path(entity), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.entity(body, MediaType.APPLICATION_JSON));
    }
    
    /**
     * @param schema
     * @param entity
     * @param entityId
     * @param body
     */
    @PUT
    @Path("{schema}/{entity}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void merge(
    		@PathParam("schema")
            @NotBlank
            final String schema,
            @PathParam("entity")
            @NotBlank
            final String entity,
            @PathParam("id")
            @NotBlank
            final String entityId,
            @NotNull
            final InputStream body
            );
    
    /**
     * @param client
     * @param schema
     * @param entity
     * @param entityId
     * @param body
     */
    static void merge(
    		final Client client,
    		final String schema,
    		final String entity,
    		final String entityId,
    		final Object body
            ) {
    	client.request(
    			target -> target.path(schema).path(entity).path(entityId), 
    			req -> req
    			)
    	.put(Entity.json(body));
    }
    
    /**
     * @param client
     * @param schema
     * @param entity
     * @param entityId
     * @param body
     */
    static void merge(
    		final Client client,
    		final String schema,
    		final String entity,
    		final String entityId,
    		final String body
            ) {
    	client.request(
    			target -> target.path(schema).path(entity).path(entityId), 
    			req -> req
    			)
    	.put(Entity.entity(body, MediaType.APPLICATION_JSON_TYPE));
    }
    
    /**
     * @param schema
     * @param entity
     * @param entityId
     */
    @DELETE
    @Path("{schema}/{entity}/{id}")
    void remove(
    		@PathParam("schema")
            @NotBlank
            final String schema,
            @PathParam("entity")
            @NotBlank
            final String entity,
            @PathParam("id")
            @NotBlank
            final String entityId
            );
    
    /**
     * @param client
     * @param schema
     * @param entity
     * @param entityId
     */
    static void remove(
    		final Client client,
    		final String schema,
    		final String entity,
    		final String entityId
            ) {
    	client.request(
    			target -> target.path(schema).path(entity).path(entityId), 
    			req -> req
    			)
    	.delete();
    }
    
    /**
     * @param schema
     * @param entity
     * @param entityId
     * @return
     */
    @POST
    @Path("{schema}/{entity}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response find(
    		@PathParam("schema")
            @NotBlank
            final String schema,
            @PathParam("entity")
            @NotBlank
            final String entity,
            @PathParam("id")
            @NotBlank
            final String entityId
            );
    
    /**
     * @param client
     * @param schema
     * @param entity
     * @param entityId
     * @return
     */
    static Response find(
    		final Client client,
    		final String schema,
            final String entity,
            final String entityId
            ) {
    	return client.request(
    			target -> target.path(schema).path(entity).path(entityId), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.text(""));
    }
    
    /**
     * @param client
     * @param cls
     * @param schema
     * @param entity
     * @param entityId
     */
    static <T extends Object> T find(
    		final Client client,
    		final Class<T> cls,
    		final String schema,
            final String entity,
            final String entityId
            ) {
    	return client.request(
    			target -> target.path(schema).path(entity).path(entityId), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.text(""))
    			.readEntity(cls);
    }
}
