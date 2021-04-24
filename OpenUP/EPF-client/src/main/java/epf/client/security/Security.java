/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.security;

import java.net.URL;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.schema.EPF;
import epf.util.client.Client;
import epf.validation.persistence.Unit;

/**
 *
 * @author FOXCONN
 */
@Path("security")
public interface Security {
    
    /**
     * 
     */
    String AUDIENCE_URL_FORMAT = "%s://%s:%s/";
    /**
     * 
     */
    String TOKEN_ID_FORMAT = "%s-%s-%s";
    /**
     * 
     */
    String REQUEST_HEADER_FORMAT = "Bearer %s";
    
    /**
     * 
     */
    String UNIT = "unit";
    
    /**
     * @param unit
     * @param username
     * @param password_hash
     * @param url
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String login(
            @QueryParam(UNIT)
            @Unit
            @NotBlank
            @DefaultValue(EPF.SCHEMA)
            final String unit,
            @FormParam("username")
            @NotBlank
            final String username,
            @FormParam("password_hash")
            @NotBlank
            final String passwordHash, 
            @QueryParam("url")
            @NotNull
            final URL url
    );
    
    /**
     * @param client
     * @param unit
     * @param username
     * @param password_hash
     * @param url
     * @return
     */
    static String login(
    		final Client client, 
    		final String unit, 
    		final String username, 
    		final String passwordHash, 
    		final URL url) {
    	final Form form = new Form();
    	form.param("username", username);
    	form.param("password_hash", passwordHash);
    	return client.request(
    			target -> target.queryParam(UNIT, unit).queryParam("url", url),
    			req -> req.accept(MediaType.TEXT_PLAIN))
    			.post(Entity.form(form), String.class);
    }
    
    /**
     * @param unit
     * @return
     */
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    String logOut(
            @QueryParam(UNIT)
            @Unit
            @NotBlank
            @DefaultValue(EPF.SCHEMA)
            final String unit
            );
    
    /**
     * @param client
     * @param unit
     * @return
     */
    static String logOut(final Client client, final String unit) {
    	return client.request(
    			target -> target.queryParam(UNIT, unit), 
    			req -> req.accept(MediaType.TEXT_PLAIN)
    			)
    			.delete(String.class);
    }
    
    /**
     * @param unit
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Token authenticate(
    		@QueryParam(UNIT)
            @Unit
            @DefaultValue(EPF.SCHEMA)
    		@NotBlank
    		final String unit
    		);
    
    /**
     * @param client
     * @param unit
     * @return
     */
    static Token authenticate(final Client client, final String unit) {
    	return client.request(
    			target -> target.queryParam(UNIT, unit), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(Token.class);
    }
    
    /**
     * @param unit
     */
    @PATCH
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    void update(
    		@QueryParam(UNIT)
            @Unit
            @DefaultValue(EPF.SCHEMA)
    		@NotBlank
    		final String unit,
    		@BeanParam
    		@Valid
    		final Info info
    		);
    
    /**
     * @param client
     * @param unit
     * @param fields
     */
    static Response update(final Client client, final String unit, final Map<String, String> fields) {
    	final Form form = new Form();
    	fields.forEach((name, value) -> form.param(name, value));
    	return client.request(
    			target -> target.queryParam(UNIT, unit), 
    			req -> req
    			)
    	.build(HttpMethod.PATCH, Entity.form(form))
    	.invoke();
    }
}
