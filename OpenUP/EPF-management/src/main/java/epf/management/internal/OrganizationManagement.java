package epf.management.internal;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;
import java.util.Map.Entry;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.management.admin.schema.Domain;
import epf.management.schema.Organization;
import epf.management.auth.schema.ClientCredential;
import epf.management.auth.schema.TokenInfo;
import epf.management.schema.Principal;
import epf.naming.Naming;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;

@ApplicationScoped
public class OrganizationManagement {
	
	@Inject
	@ConfigProperty(name = Naming.Management.ORGANIZATION_DOMAIN)
	String organizationDomain;
	
	@Inject
	@ConfigProperty(name = "epf.management.auth.client.id")
	String clientId;
	
	@Inject
	@ConfigProperty(name = "epf.management.auth.client.secret")
	String clientSecret;
	
	@RestClient
	transient AuthClient authClient;
	
	@RestClient
	transient AdminClient adminClient;

	@CacheResult(cacheName = Naming.Management.ORGANIZATION_MANAGEMENT)
	public Organization createPrincipalOrganization(@CacheKey final String tokenId, final Principal principal) throws Exception {
		
		final ClientCredential credential = new ClientCredential();
		credential.setClient_id(clientId);
		credential.setClient_secret(clientSecret);
		final TokenInfo token = authClient.getToken(credential);
		
		epf.management.admin.schema.Organization keycloakOrg = new epf.management.admin.schema.Organization();
		
		final String name = Base64.getEncoder().withoutPadding().encodeToString(principal.getEmail().getBytes(StandardCharsets.UTF_8));
		keycloakOrg.setName(name);
		
		final String domainName = name + "." + organizationDomain;
		final Domain domain = new Domain();
		domain.setName(domainName);
		keycloakOrg.setDomains(new ArrayList<>());
		keycloakOrg.getDomains().add(domain);
		
		final String location = adminClient.createOrganization(token, keycloakOrg);
		final String id = location.substring(location.lastIndexOf('/') + 1);
		
		final String userId = principal.getSubject();
		adminClient.addMember(token, id, userId);
		
		final Organization organization = new Organization();
		organization.setId(id);
		organization.setName(name);
		return organization;
	}
	
	public Optional<Organization> getOrganization(final JsonWebToken jwt) throws Exception {
		final Optional<?> organizationClaim = jwt.claim(Naming.Management.ORGANIZATION);
		if(organizationClaim.isPresent()) {
			final Organization organization = new Organization();
			final JsonObject organizationValue = (JsonObject) organizationClaim.get();
			for(Entry<String, JsonValue> organizationEntry : organizationValue.entrySet()) {
				organization.setName(organizationEntry.getKey());
				organization.setId(organizationEntry.getValue().asJsonObject().getString("id"));
			}
			return Optional.of(organization);
		}
		return Optional.empty();
	}
}
