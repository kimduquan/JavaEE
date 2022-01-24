/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.security.client;

import java.net.URL;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.security.schema.Token;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.SECURITY)
public interface Security {
    
    /**
     * 
     */
    String AUDIENCE_FORMAT = "%s://%s:%s/";
    /**
     * 
     */
    String TOKEN_ID_FORMAT = "%s-%s-%s";
    /**
     * 
     */
    String HEADER_FORMAT = "Bearer %s";
    
    /**
     * 
     */
    String URL = "url";
    
    /**
     * @param username
     * @param passwordHash
     * @param url
     * @return
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String login(
            @FormParam("username")
            @NotBlank
            final String username,
            @FormParam("password_hash")
            @NotBlank
            final String passwordHash, 
            @QueryParam(URL)
            @NotNull
            final URL url,
            @Context
            final HttpHeaders headers
    ) throws Exception;
    
    /**
     * @param client
     * @param username
     * @param passwordHash
     * @param url
     * @return
     */
    static String login(
    		final Client client,
    		final String username, 
    		final String passwordHash, 
    		final URL url) {
    	final MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
    	form.add("username", username);
    	form.add("password_hash", passwordHash);
    	return client.request(
    			target -> target.queryParam(URL, url),
    			req -> req.accept(MediaType.TEXT_PLAIN))
    			.post(Entity.form(form), String.class);
    }
    
    /**
     * @return
     * @throws Exception 
     */
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    String logOut() throws Exception;
    
    /**
     * @param client
     * @return
     */
    static String logOut(final Client client) {
    	return client.request(
    			target -> target, 
    			req -> req.accept(MediaType.TEXT_PLAIN)
    			)
    			.delete(String.class);
    }
    
    /**
     * @return
     * @throws Exception 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Token authenticate() throws Exception;
    
    /**
     * @param client
     * @return
     */
    static Token authenticate(final Client client) {
    	return client.request(
    			target -> target, 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(Token.class);
    }
    
    /**
     * @param info
     * @throws Exception 
     */
    @PATCH
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    void update(
    		@FormParam("password")
    		@NotNull
    		@NotBlank
    		final String password
    		) throws Exception;
    
    /**
     * @param client
     * @param fields
     * @return
     */
    static Response update(final Client client, final String password) {
    	final Form form = new Form().param("password", password);
    	return client.request(
    			target -> target, 
    			req -> req
    			)
    	.build(HttpMethod.PATCH, Entity.form(form))
    	.invoke();
    }
    
    /**
     * @return
     * @throws Exception 
     */
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String revoke() throws Exception;
    
    /**
     * @param client
     * @return
     */
    static String revoke(final Client client) {
    	return client.request(
    			target -> target,
    			req -> req.accept(MediaType.TEXT_PLAIN))
    			.put(Entity.form(new Form()), String.class);
    }
}
