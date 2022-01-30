/**
 * 
 */
package epf.security.client.otp;

import java.net.URL;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
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
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.SECURITY)
public interface OTPSecurity {

    /**
     * @param username
     * @param passwordHash
     * @param url
     * @return
     * @throws Exception 
     */
    @POST
    @Path("otp")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String loginOneTime(
            @FormParam("username")
            @NotBlank
            final String username,
            @FormParam("password_hash")
            @NotBlank
            final String passwordHash, 
            @QueryParam("url")
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
    static String loginOneTime(
    		final Client client,
    		final String username, 
    		final String passwordHash, 
    		final URL url) {
    	final Form form = new Form();
    	form.param("username", username);
    	form.param("password_hash", passwordHash);
    	return client.request(
    			target -> target.path("otp").queryParam("url", url),
    			req -> req.accept(MediaType.TEXT_PLAIN))
    			.post(Entity.form(form), String.class);
    }
    
    /**
     * @param oneTimePassword
     * @return
     */
    @PUT
    @Path("otp")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String authenticateOneTime(
            @FormParam("otp")
            @NotBlank
            final String oneTimePassword
    );
    
    /**
     * @param client
     * @param oneTimePassword
     * @return
     */
    static String authenticateOneTime(
    		final Client client,
    		final String oneTimePassword
    		) {
    	final Form form = new Form();
    	form.param("otp", oneTimePassword);
    	return client.request(
    			target -> target.path("otp"),
    			req -> req.accept(MediaType.TEXT_PLAIN))
    			.put(Entity.form(form), String.class);
    }
}
