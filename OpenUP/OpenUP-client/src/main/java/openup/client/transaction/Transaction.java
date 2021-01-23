/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.transaction;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author FOXCONN
 */
@Path("transaction")
public interface Transaction {
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    String begin() throws Exception;
    
    @PUT
    @Path("{id}")
    void commit(
            @PathParam("id") 
            String id
    ) throws Exception;
    
    @DELETE
    @Path("{id}")
    void rollback(
            @PathParam("id") 
            String id
    ) throws Exception;
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Status getStatus(
            @PathParam("id") 
            String id
    ) throws Exception;
}
