package epf.security.client.otp;

import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletionStage;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.MediaType;
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
    CompletionStage<String> loginOneTime(
            @FormParam("username")
            @NotBlank
            final String username,
            @FormParam("password")
            @NotBlank
            final String password,
            @MatrixParam(Naming.Management.TENANT)
            final String tenant
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
    		final String password, 
    		final URL url) {
    	final Form form = new Form();
    	form.param("username", username);
    	form.param("password", password);
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
            @FormParam("password")
            @NotBlank
            final String oneTimePassword,
            @FormParam("url")
            @NotBlank
            final URL url,
            @HeaderParam(Naming.Gateway.Headers.X_FORWARDED_HOST)
            final List<String> forwardedHost,
            @MatrixParam(Naming.Management.TENANT)
            final String tenant
    );
    
    /**
     * @param client
     * @param oneTimePassword
     * @return
     */
    static String authenticateOneTime(
    		final Client client,
    		final String oneTimePassword,
    		final URL url
    		) {
    	final Form form = new Form();
    	form.param("password", oneTimePassword);
    	form.param("url", url.toString());
    	return client.request(
    			target -> target.path("otp"),
    			req -> req.accept(MediaType.TEXT_PLAIN))
    			.put(Entity.form(form), String.class);
    }
}
