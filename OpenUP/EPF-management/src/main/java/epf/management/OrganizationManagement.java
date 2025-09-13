package epf.management;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.management.internal.KeycloakAdminClient;
import epf.management.keycloak.schema.Domain;
import epf.management.schema.Organization;
import epf.management.schema.Principal;
import epf.naming.Naming;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OrganizationManagement {
	
	@Inject
	@ConfigProperty(name = Naming.Management.ORGANIZATION_DOMAIN)
	String organizationDomain;
	
	@RestClient
	transient KeycloakAdminClient keycloakAdmin;

	@CacheResult(cacheName = Naming.Management.ORGANIZATION_MANAGEMENT)
	public Organization createPrincipalOrganization(@CacheKey final String tokenId, final Principal principal) throws Exception {
		epf.management.keycloak.schema.Organization keycloakOrg = new epf.management.keycloak.schema.Organization();
		
		final String name = Base64.getEncoder().withoutPadding().encodeToString(principal.getEmail().getBytes(StandardCharsets.UTF_8));
		keycloakOrg.setName(name);
		
		final String domainName = name + "." + organizationDomain;
		final Domain domain = new Domain();
		domain.setName(domainName);
		keycloakOrg.setDomains(new ArrayList<>());
		keycloakOrg.getDomains().add(domain);
		
		keycloakOrg = keycloakAdmin.createOrganization(keycloakOrg);
		
		final String userId = principal.getSubject();
		keycloakAdmin.addMember(keycloakOrg.getId(), userId);
		
		final Organization organization = new Organization();
		organization.setId(keycloakOrg.getId());
		organization.setName(keycloakOrg.getName());
		return organization;
	}
}
