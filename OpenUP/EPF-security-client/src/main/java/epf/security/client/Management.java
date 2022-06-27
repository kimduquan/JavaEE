package epf.security.client;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.SECURITY)
public interface Management {
	
    /**
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    @Path(Naming.Security.CREDENTIAL)
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Response createCredential(
    		@MatrixParam(Naming.Management.TENANT)
            final String tenant,
    		@FormParam(Naming.Security.Claims.EMAIL)
    		@NotBlank
    		@Pattern(regexp = Naming.Security.Claims.EMAIL_REGEXP)
    		final String email,
    		@FormParam(Naming.Security.Credential.PASSWORD)
    		@NotBlank
    		final String password,
    		@FormParam(Naming.Security.Claims.FIRST_NAME)
    		@NotBlank
    		final String firstName,
    		@FormParam(Naming.Security.Claims.LAST_NAME)
    		@NotBlank
    		final String lastName,
    		@HeaderParam(Naming.Gateway.Headers.X_FORWARDED_HOST)
            final List<String> forwardedHost,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_PORT)
            final List<String> forwardedPort,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_PROTO)
            final List<String> forwardedProto
    		) throws Exception;
    
    /**
     * @param client
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    static Response createCredential(final Client client, final String email, final String password, final String firstName, final String lastName) throws Exception {
    	return client.request(
    			target -> target.path(Naming.Security.CREDENTIAL), 
    			req -> req.accept(MediaType.TEXT_PLAIN)
    			)
    			.post(Entity.form(new Form()
    					.param(Naming.Security.Claims.EMAIL, email)
    					.param(Naming.Security.Credential.PASSWORD, password)
    					.param(Naming.Security.Claims.FIRST_NAME, firstName)
    					.param(Naming.Security.Claims.LAST_NAME, lastName)
    					)
    					);
    }
    
    /**
     * @param context
     * @return
     * @throws Exception
     */
    @Path(Naming.Security.CREDENTIAL)
    @PUT
    Response activeCredential(
    		@Context 
    		final SecurityContext context) throws Exception;
    
    /**
     * @param client
     * @return
     * @throws Exception
     */
    static Response activeCredential(final Client client) throws Exception {
    	return client.request(
    			target -> target.path(Naming.Security.CREDENTIAL), 
    			req -> req
    			)
    			.put(null);
    }

    /**
     * @param email
     * @return
     */
    @Path(Naming.Security.Credential.PASSWORD)
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Response resetPassword(
    		@MatrixParam(Naming.Management.TENANT)
            final String tenant,
    		@FormParam(Naming.Security.Claims.EMAIL) 
    		@Pattern(regexp = Naming.Security.Claims.EMAIL_REGEXP)
    		final String email,
    		@HeaderParam(Naming.Gateway.Headers.X_FORWARDED_HOST)
            final List<String> forwardedHost,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_PORT)
            final List<String> forwardedPort,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_PROTO)
            final List<String> forwardedProto) throws Exception;
    
    /**
     * @param client
     * @param email
     * @return
     */
    static Response resetPassword(final Client client, final String email) {
    	return client
    			.request(
    					target -> target.path(Naming.Security.Credential.PASSWORD), 
    					req -> req.accept(MediaType.TEXT_PLAIN)
    					)
    			.post(Entity.form(new Form().param(Naming.Security.Claims.EMAIL, email)));
    }
    
    /**
     * @param password
     * @param context
     * @return
     * @throws Exception
     */
    @Path(Naming.Security.Credential.PASSWORD)
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Response setPassword(
    		@FormParam(Naming.Security.Credential.PASSWORD)
    		final String password, 
    		@Context 
    		final SecurityContext context) throws Exception;
    
    /**
     * @param client
     * @param password
     * @return
     */
    static Response setPassword(final Client client, final String password) {
    	return client
    			.request(
    					target -> target.path(Naming.Security.Credential.PASSWORD), 
    					req -> req
    					)
    			.put(Entity.form(new Form().param(Naming.Security.Credential.PASSWORD, password)));
    }
}
