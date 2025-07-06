package epf.shell.security;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;
import epf.security.schema.Token;

@Path(Naming.SECURITY)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface SecurityClient {

	@POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String login(
    		@FormParam("username")
            final String username,
            @FormParam("password")
            final String password, 
            @QueryParam("url")
            final String url
    ) throws Exception;
	
	@DELETE
    @Produces(MediaType.TEXT_PLAIN)
    String logOut(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token) throws Exception;
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    Token authenticate(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token) throws Exception;
	
	@PATCH
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    void update(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		@FormParam("password")
    		final String password
    		) throws Exception;
	
	@PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    String revoke(
    		@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
    		final MultivaluedMap<String, String> body) throws Exception;
}
