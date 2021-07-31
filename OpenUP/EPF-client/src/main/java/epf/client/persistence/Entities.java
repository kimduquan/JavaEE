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

import epf.util.client.Client;

/**
 *
 * @author FOXCONN
 */
@Path("persistence")
public interface Entities {
    
    /**
     * @param name
     * @param body
     * @return
     */
    @POST
    @Path("{entity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Object persist(
            @PathParam("entity")
            @NotBlank
            final String name,
            @NotNull
            final InputStream body
            );
    
    /**
     * @param <T>
     * @param client
     * @param cls
     * @param name
     * @param body
     * @return
     */
    static <T> T persist(
    		final Client client,
    		final Class<T> cls,
    		final String name,
    		final T body
            ){
    	return client.request(
    			target -> target.path(name), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.json(body), cls);
    }
    
    /**
     * @param client
     * @param name
     * @param body
     * @return
     */
    static Response persist(
    		final Client client,
    		final String name,
    		final String body
            ){
    	return client.request(
    			target -> target.path(name), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.entity(body, MediaType.APPLICATION_JSON));
    }
    
    /**
     * @param name
     * @param entityId
     * @param body
     */
    @PUT
    @Path("{entity}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void merge(
            @PathParam("entity")
            @NotBlank
            final String name,
            @PathParam("id")
            @NotBlank
            final String entityId,
            @NotNull
            final InputStream body
            );
    
    /**
     * @param client
     * @param name
     * @param entityId
     * @param body
     */
    static void merge(
    		final Client client,
    		final String name,
    		final String entityId,
    		final Object body
            ) {
    	client.request(
    			target -> target.path(name).path(entityId), 
    			req -> req
    			)
    	.put(Entity.json(body));
    }
    
    /**
     * @param client
     * @param name
     * @param entityId
     * @param body
     */
    static void merge(
    		final Client client,
    		final String name,
    		final String entityId,
    		final String body
            ) {
    	client.request(
    			target -> target.path(name).path(entityId), 
    			req -> req
    			)
    	.put(Entity.entity(body, MediaType.APPLICATION_JSON_TYPE));
    }
    
    /**
     * @param name
     * @param entityId
     */
    @DELETE
    @Path("{entity}/{id}")
    void remove(
            @PathParam("entity")
            @NotBlank
            final String name,
            @PathParam("id")
            @NotBlank
            final String entityId
            );
    
    /**
     * @param client
     * @param name
     * @param entityId
     */
    static void remove(
    		final Client client,
    		final String name,
    		final String entityId
            ) {
    	client.request(
    			target -> target.path(name).path(entityId), 
    			req -> req
    			)
    	.delete();
    }
    
    /**
     * @param name
     * @param entityId
     * @return
     */
    @POST
    @Path("{entity}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response find(
            @PathParam("entity")
            @NotBlank
            final String name,
            @PathParam("id")
            @NotBlank
            final String entityId
            );
    
    /**
     * @param client
     * @param name
     * @param entityId
     * @return
     */
    static Response find(
    		final Client client,
            final String name,
            final String entityId
            ) {
    	return client.request(
    			target -> target.path(name).path(entityId), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.text(null));
    }
}
