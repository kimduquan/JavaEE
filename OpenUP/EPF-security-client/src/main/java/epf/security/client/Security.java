package epf.security.client;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletionStage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
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
     * @param password
     * @param url
     * @param tenant
     * @param forwardedHost
     * @return
     * @throws Exception
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    CompletionStage<String> login(
            @FormParam(Naming.Security.Credential.USERNAME)
            @NotBlank
            final String username,
            @FormParam(Naming.Security.Credential.PASSWORD)
            @NotNull
            @NotBlank
            final String password, 
            @QueryParam(URL)
            @NotNull
            final URL url,
            @MatrixParam(Naming.Management.TENANT)
            final String tenant,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_HOST)
            final List<String> forwardedHost
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
    		final String password, 
    		final URL url) {
    	final MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
    	form.add(Naming.Security.Credential.USERNAME, username);
    	form.add(Naming.Security.Credential.PASSWORD, password);
    	return client.request(
    			target -> target.queryParam(URL, url),
    			req -> req.accept(MediaType.TEXT_PLAIN))
    			.post(Entity.form(form), String.class);
    }
    
    /**
     * @param context
     * @return
     * @throws Exception
     */
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    String logOut(
    		@Context
            final SecurityContext context) throws Exception;
    
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
     * @param tenant
     * @param context
     * @return
     * @throws Exception
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Token authenticate(
    		@MatrixParam(Naming.Management.TENANT)
    		final String tenant,
            @Context
            final SecurityContext context) throws Exception;
    
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
     * @param password
     * @param context
     * @return
     * @throws Exception
     */
    @PATCH
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    CompletionStage<Response> update(
    		@FormParam(Naming.Security.Credential.PASSWORD)
    		@NotNull
    		@NotBlank
    		final String password,
            @Context
            final SecurityContext context
    		) throws Exception;
    
    /**
     * @param client
     * @param password
     * @return
     */
    static Response update(final Client client, final String password) {
    	final Form form = new Form();
    	return client.request(
    			target -> target, 
    			req -> req
    			)
    	.build(HttpMethod.PATCH, Entity.form(password == null ? form : form.param(Naming.Security.Credential.PASSWORD, password)))
    	.invoke();
    }
    
    /**
     * @param context
     * @param forwardedHost
     * @param duration
     * @return
     * @throws Exception
     */
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    CompletionStage<String> revoke(
            @Context
            final SecurityContext context,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_HOST)
            final List<String> forwardedHost,
            @FormParam("duration")
            final String duration) throws Exception;
    
    /**
     * @param client
     * @param duration
     * @return
     */
    static String revoke(final Client client, final Duration duration) {
    	final Form form = new Form();
    	if(duration != null) {
    		form.param("duration", duration.toString());
    	}
    	return client.request(
    			target -> target,
    			req -> req.accept(MediaType.TEXT_PLAIN))
    			.put(Entity.form(form), String.class);
    }

    /**
     * @param provider
     * @param session
     * @param token
     * @param url
     * @param tenant
     * @param forwardedHost
     * @throws Exception
     */
    @Path(Naming.Security.AUTH)
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Response authenticateIDToken(
            @FormParam("provider")
            final String provider,
            @FormParam("session")
            final String session,
            @FormParam("token")
            final String token,
            @FormParam(URL)
            final URL url,
            @MatrixParam(Naming.Management.TENANT)
            final String tenant,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_HOST)
            final List<String> forwardedHost) throws Exception;
    
    /**
     * @param client
     * @param provider
     * @param session
     * @param token
     * @param url
     * @return
     */
    static Token authenticateIDToken(
    		final Client client, 
    		final String provider, 
    		final String session, 
    		final String token,
    		final URL url) {
    	return client.request(
    			target -> target.path(Naming.Security.AUTH), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.form(new Form().param("provider", provider).param("session", session).param("token", token).param(URL, url.toString())))
    			.readEntity(Token.class);
    }
    
    /**
     * @param context
     * @return
     * @throws Exception
     */
    @Path(Naming.Security.PRINCIPAL)
    @POST
    Response createPrincipal(
    		@Context 
    		final SecurityContext context) throws Exception;
    
    /**
     * @param client
     * @return
     * @throws Exception
     */
    static Response createPrincipal(final Client client) throws Exception {
    	return client.request(
    			target -> target.path(Naming.Security.PRINCIPAL), 
    			req -> req
    			)
    			.post(null);
    }
}
