package epf.management.internal;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.management.keycloak.auth.schema.TokenInfo;
import epf.management.keycloak.schema.Organization;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "epf-management-admin")
public interface AdminClient {

	@POST
	@Path("organizations")
	Organization createOrganization(@HeaderParam(HttpHeaders.AUTHORIZATION) final String authorization, final Organization organization) throws Exception;
	
	default Organization createOrganization(final TokenInfo token, final Organization organization) throws Exception {
		return createOrganization(token.getToken_type() + " " + token.getAccess_token(), organization);
	}
	
	@POST
	@Path("organizations/{org-id}/members")
	void addMember(@HeaderParam(HttpHeaders.AUTHORIZATION) final String authorization, @PathParam("org-id") final String organizationId, final String userId) throws Exception;
	
	default void addMember(final TokenInfo token, final String organizationId, final String userId) throws Exception {
		addMember(token.getToken_type() + " " + token.getAccess_token(), organizationId, userId);
	}
}
