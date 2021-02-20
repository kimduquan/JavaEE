/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.security;

import java.net.URL;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import epf.schema.EPF;
import epf.util.client.Client;
import epf.validation.persistence.Unit;

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
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String login(
            @QueryParam("unit")
            @Unit
            @NotBlank
            @DefaultValue(EPF.Schema)
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
    
    static String login(
    		Client client, 
    		String unit, 
    		String username, 
    		String password_hash, 
    		URL url) {
    	Form form = new Form();
    	form.param("username", username);
    	form.param("password_hash", password_hash);
    	return client.request(
    			target -> target.queryParam("unit", unit).queryParam("url", url),
    			req -> req.accept(MediaType.TEXT_PLAIN))
    			.post(Entity.form(form), String.class);
    }
    
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    String logOut(
            @QueryParam("unit")
            @Unit
            @NotBlank
            @DefaultValue(EPF.Schema)
            String unit
            ) throws Exception;
    
    static String logOut(Client client, String unit) {
    	return client.request(
    			target -> target.queryParam("unit", unit), 
    			req -> req.accept(MediaType.TEXT_PLAIN)
    			)
    			.delete(String.class);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Token authenticate(
    		@QueryParam("unit")
            @Unit
            @DefaultValue(EPF.Schema)
    		@NotBlank
            String unit
    		) throws Exception;
    
    static Token authenticate(Client client, String unit) throws Exception{
    	return client.request(
    			target -> target.queryParam("unit", unit), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(Token.class);
    }
}
