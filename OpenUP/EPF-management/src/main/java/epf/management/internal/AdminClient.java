package epf.management.internal;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.management.admin.schema.Organization;
import epf.management.auth.schema.TokenInfo;
import epf.naming.Naming;
import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = Naming.Management.Internal.MANAGEMENT_ADMIN)
public interface AdminClient {

	@POST
	@Path("organizations")
	Response createOrganization(@HeaderParam(HttpHeaders.AUTHORIZATION) final String authorization, final Organization organization) throws Exception;
	
	default String createOrganization(final TokenInfo token, final Organization organization) throws Exception {
		final Response response = createOrganization(token.getToken_type() + " " + token.getAccess_token(), organization);
		response.bufferEntity();
		if(Status.CREATED.getStatusCode() == response.getStatus()) {
			return response.getHeaderString(HttpHeaders.LOCATION);
		}
		throw new ClientErrorException(response.getStatus());
	}
	
	@POST
	@Path("organizations/{org-id}/members")
	void addMember(@HeaderParam(HttpHeaders.AUTHORIZATION) final String authorization, @PathParam("org-id") final String organizationId, final String userId) throws Exception;
	
	default void addMember(final TokenInfo token, final String organizationId, final String userId) throws Exception {
		addMember(token.getToken_type() + " " + token.getAccess_token(), organizationId, userId);
	}
}
