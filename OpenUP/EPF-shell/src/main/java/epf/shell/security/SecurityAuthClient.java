package epf.shell.security;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;
import epf.shell.security.schema.TokenInfo;

@Path("/")
@RegisterRestClient(configKey = Naming.Security.Auth.CLIENT)
public interface SecurityAuthClient {

	@POST
	@Path("token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    TokenInfo login(
    		@FormParam("grant_type")
    		final String grantType,
    		@FormParam("client_id")
    		final String clientId,
    		@FormParam("client_secret")
    		final String clientSecret,
    		@FormParam("username")
            final String username,
            @FormParam("password")
            final String password
    ) throws Exception;
}
