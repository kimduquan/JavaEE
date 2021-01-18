/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.security;

import java.net.URL;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import openup.client.persistence.validation.Unit;

/**
 *
 * @author FOXCONN
 */
@Path("security")
public interface Security {
    
    String AUDIENCE_URL_FORMAT = "%s://%s:%s/";
    String TOKEN_ID_FORMAT = "%s-%s-%s";
    String REQUEST_HEADER_FORMAT = "Bearer %s";
    
    @POST
    @Path("{unit}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String login(
            @PathParam("unit")
            @Unit
            String unit,
            @FormParam("username")
            @NotBlank
            String username,
            @FormParam("password_hash")
            @NotBlank
            String password_hash, 
            @QueryParam("url")
            @NotNull
            URL url
    ) throws Exception;
    
    @DELETE
    @Path("{unit}")
    @Produces(MediaType.TEXT_PLAIN)
    String logOut(
            @PathParam("unit")
            @Unit
            String unit
            ) throws Exception;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Token authenticate() throws Exception;
}
