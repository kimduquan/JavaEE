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
import epf.util.client.Client;
import epf.validation.persistence.Unit;

/**
 *
 * @author FOXCONN
 */
@Path("persistence")
public interface Entities {
    
    /**
     * @param unit
     * @param name
     * @param body
     * @return
     */
    @POST
    @Path("{unit}/{entity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Object persist(
            @PathParam("unit")
            @Unit
            @NotBlank
            final String unit,
            @PathParam("entity")
            @NotBlank
            final String name,
            @NotNull
            final InputStream body
            );
    
    /**
     * @param client
     * @param cls
     * @param unit
     * @param name
     * @param body
     */
    static <T> T persist(
    		final Client client,
    		final Class<T> cls,
    		final String unit,
    		final String name,
    		final T body
            ){
    	return client.request(
    			target -> target.path(unit).path(name), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.json(body), cls);
    }
    
    /**
     * @param unit
     * @param name
     * @param entityId
     * @param body
     */
    @PUT
    @Path("{unit}/{entity}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void merge(
    		@PathParam("unit")
            @Unit
            @NotBlank
            final String unit,
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
     * @param unit
     * @param name
     * @param entityId
     * @param body
     */
    static void merge(
    		final Client client,
    		final String unit,
    		final String name,
    		final String entityId,
    		final Object body
            ) {
    	client.request(
    			target -> target.path(unit).path(name).path(entityId), 
    			req -> req
    			)
    	.put(Entity.json(body));
    }
    
    /**
     * @param unit
     * @param name
     * @param entityId
     */
    @DELETE
    @Path("{unit}/{entity}/{id}")
    void remove(
            @PathParam("unit")
            @Unit
            @NotBlank
            final String unit,
            @PathParam("entity")
            @NotBlank
            final String name,
            @PathParam("id")
            @NotBlank
            final String entityId
            );
    
    /**
     * @param client
     * @param unit
     * @param name
     * @param entityId
     */
    static void remove(
    		final Client client,
    		final String unit,
    		final String name,
    		final String entityId
            ) {
    	client.request(
    			target -> target.path(unit).path(name).path(entityId), 
    			req -> req
    			)
    	.delete();
    }
}
