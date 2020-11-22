/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.api.persistence;

import java.io.InputStream;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import openup.api.epf.schema.Roles;

/**
 *
 * @author FOXCONN
 */
@Path("persistence/entity")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(Roles.ANY_ROLE)
public interface Entities {
    
    @POST
    @Path("{entity}/{id}")
    Response persist(
            @PathParam("entity")
            String name,
            @PathParam("id")
            String id,
            InputStream body
            ) throws Exception;
    
    @GET
    @Path("{entity}/{id}")
    Response find(
            @PathParam("entity")
            String name,
            @PathParam("id")
            String id) throws Exception;
    
    @DELETE
    @Path("{entity}/{id}")
    Response remove(
            @PathParam("entity")
            String name,
            @PathParam("id")
            String id
            ) throws Exception;
}
