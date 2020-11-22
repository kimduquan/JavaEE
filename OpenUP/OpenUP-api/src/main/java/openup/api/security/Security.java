/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.api.security;

import java.net.URL;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import openup.api.epf.schema.Roles;

/**
 *
 * @author FOXCONN
 */
@Path("security")
@RolesAllowed(Roles.ANY_ROLE)
public interface Security {
    
    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    Response login(
            @FormParam("username")
            String username,
            @FormParam("password")
            String password, 
            @QueryParam("url")
            URL url) throws Exception;
    
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Response runAs(
            @FormParam("runAs") 
            String role,
            @Context
            SecurityContext context, 
            @Context 
            UriInfo uriInfo
            ) throws Exception;
    
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    Response logOut(
            @Context
            SecurityContext context
            ) throws Exception;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Response authenticate(@Context SecurityContext context);
}
