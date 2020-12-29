/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.persistence;

import java.io.InputStream;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author FOXCONN
 */
@Path("persistence")
public interface Entities {
    
    @POST
    @Path("{unit}/{entity}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void persist(
            @PathParam("unit")
            String unit,
            @PathParam("entity")
            String name,
            @PathParam("id")
            String id,
            InputStream body
            ) throws Exception;
    
    @DELETE
    @Path("{unit}/{entity}/{id}")
    void remove(
            @PathParam("unit")
            String unit,
            @PathParam("entity")
            String name,
            @PathParam("id")
            String id
            ) throws Exception;
}
