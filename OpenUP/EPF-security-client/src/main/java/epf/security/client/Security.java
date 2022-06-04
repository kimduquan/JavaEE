package epf.security.client;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletionStage;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
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
    CompletionStage<String> login(
            @FormParam("username")
            @NotBlank
            final String username,
            @FormParam("password")
            @NotBlank
            final String password, 
            @QueryParam(URL)
            @NotNull
            final URL url,
            @MatrixParam(Naming.Management.TENANT)
            final String tenant,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_HOST)
            final List<String> forwardedHost,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_PORT)
            final List<String> forwardedPort,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_PROTO)
            final List<String> forwardedProto
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
    	form.add("username", username);
    	form.add("password", password);
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
     * @return
     * @throws Exception 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    Token authenticate(
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
     * @param info
     * @throws Exception 
     */
    @PATCH
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    CompletionStage<Response> update(
    		@FormParam("password")
    		@NotNull
    		@NotBlank
    		final String password,
            @Context
            final SecurityContext context
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
    CompletionStage<String> revoke(
            @Context
            final SecurityContext context,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_HOST)
            final List<String> forwardedHost,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_PORT)
            final List<String> forwardedPort,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_PROTO)
            final List<String> forwardedProto,
            @FormParam("duration")
            final String duration) throws Exception;
    
    /**
     * @param client
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
     * @param token
     * @return
     * @throws Exception
     */
    @Path("auth")
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
            @MatrixParam(Naming.Management.TENANT)
            final String tenant,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_HOST)
            final List<String> forwardedHost,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_PORT)
            final List<String> forwardedPort,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_PROTO)
            final List<String> forwardedProto) throws Exception;
    
    /**
     * @param client
     * @param provider
     * @param session
     * @param token
     * @return
     */
    static Token authenticateIDToken(
    		final Client client, 
    		final String provider, 
    		final String session, 
    		final String token) {
    	return client.request(
    			target -> target.path("auth"), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.post(Entity.form(new Form().param("provider", provider).param("session", session).param("token", token)))
    			.readEntity(Token.class);
    }
}
