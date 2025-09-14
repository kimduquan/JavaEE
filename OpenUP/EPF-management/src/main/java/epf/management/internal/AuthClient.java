package epf.management.internal;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.management.auth.schema.ClientCredential;
import epf.management.auth.schema.TokenInfo;
import epf.naming.Naming;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@RegisterRestClient(configKey = Naming.Management.Internal.MANAGEMENT_AUTH)
public interface AuthClient {

	@POST
	@Path("token")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	TokenInfo getToken(final ClientCredential credential) throws Exception;
}
