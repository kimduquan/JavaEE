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
    
    @POST
    @Path("{unit}/{entity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Object persist(
            @PathParam("unit")
            @Unit
            @NotBlank
            String unit,
            @PathParam("entity")
            @NotBlank
            String name,
            @NotNull
            InputStream body
            ) throws Exception;
    
    static <T> T persist(
    		Client client,
            Class<T> cls,
            String unit,
            String name,
            T body
            ) throws Exception{
    	return client.request(
    			target -> target.path(unit).path(name), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.json(body), cls);
    }
    
    @PUT
    @Path("{unit}/{entity}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void merge(
    		@PathParam("unit")
            @Unit
            @NotBlank
            String unit,
            @PathParam("entity")
            @NotBlank
            String name,
            @PathParam("id")
            @NotBlank
            String id,
            @NotNull
            InputStream body
            ) throws Exception;
    
    static void merge(
    		Client client,
    		String unit,
            String name,
            String id,
            Object body
            ) throws Exception{
    	client.request(
    			target -> target.path(unit).path(name).path(id), 
    			req -> req
    			)
    	.put(Entity.json(body));
    }
    
    @DELETE
    @Path("{unit}/{entity}/{id}")
    void remove(
            @PathParam("unit")
            @Unit
            @NotBlank
            String unit,
            @PathParam("entity")
            @NotBlank
            String name,
            @PathParam("id")
            @NotBlank
            String id
            ) throws Exception;
    
    static void remove(
    		Client client,
            String unit,
            String name,
            String id
            ) throws Exception{
    	client.request(
    			target -> target.path(unit).path(name).path(id), 
    			req -> req
    			)
    	.delete();
    }
}
