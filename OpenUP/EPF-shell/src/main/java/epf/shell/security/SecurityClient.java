package epf.shell.security;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;
import epf.security.schema.Token;

/**
 * @author PC
 *
 */
@Path(Naming.SECURITY)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface SecurityClient {

	/**
	 * @param username
	 * @param passwordHash
	 * @param url
	 * @return
	 * @throws Exception
	 */
	@POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String login(
    		@FormParam("username")
            final String username,
            @FormParam("password_hash")
            final String passwordHash, 
            @QueryParam("url")
            final String url
    ) throws Exception;
	
	/**
	 * @return
	 * @throws Exception
	 */
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
    String logOut(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token) throws Exception;
	
	/**
	 * @return
	 * @throws Exception
	 */
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    Token authenticate(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token) throws Exception;
	
	/**
	 * @param password
	 * @throws Exception
	 */
	@PATCH
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    void update(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		@FormParam("password")
    		final String password
    		) throws Exception;
	
	/**
	 * @return
	 * @throws Exception
	 */
	@PUT
    @Produces(MediaType.TEXT_PLAIN)
    String revoke(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		final MultivaluedMap<String, String> body) throws Exception;
}
