/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.persistence;

import java.io.InputStream;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import openup.client.persistence.validation.Unit;

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
            String unit,
            @PathParam("entity")
            @NotBlank
            String name,
            @NotNull
            InputStream body
            ) throws Exception;
    
    @PUT
    @Path("{unit}/{entity}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void merge(
    		@PathParam("unit")
            @Unit
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
    
    @DELETE
    @Path("{unit}/{entity}/{id}")
    void remove(
            @PathParam("unit")
            @Unit
            String unit,
            @PathParam("entity")
            @NotBlank
            String name,
            @PathParam("id")
            @NotBlank
            String id
            ) throws Exception;
}
