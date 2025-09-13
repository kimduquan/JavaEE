package epf.management.internal;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.management.keycloak.schema.Organization;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "keycloak.admin")
public interface KeycloakAdminClient {

	@POST
	@Path("organizations")
	Organization createOrganization(final Organization organization) throws Exception;
	
	@POST
	@Path("organizations/{org-id}/members")
	void addMember(@PathParam("org-id") final String organizationId, final String userId) throws Exception;
}
