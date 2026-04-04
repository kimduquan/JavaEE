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
import epf.management.config.util.ConfigPath;
import epf.management.external.AdminClient;
import epf.management.external.AuthClient;
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
	@ConfigProperty(name = Naming.Management.Internal.AUTH_CLIENT_ID)
	String clientId;
	
	@RestClient
	transient AuthClient authClient;
	
	@RestClient
	transient AdminClient adminClient;
	
	@Inject
	transient PersistenceManagement persistenceManagement;
	
	private final ConfigPath config = new ConfigPath("/epf/config/management");

	@CacheResult(cacheName = Naming.Management.ORGANIZATION_MANAGEMENT)
	public Organization createOrganization(@CacheKey final String subject, final Principal principal) throws Exception {
		
		final String clientSecret = config.getValue(Naming.Management.Internal.AUTH_CLIENT_SECRET);
		final ClientCredential credential = new ClientCredential();
		credential.setClient_id(clientId);
		credential.setClient_secret(clientSecret);
		final TokenInfo token = authClient.getToken(credential);
		
		epf.management.admin.schema.Organization organizationInfo = new epf.management.admin.schema.Organization();
		
		final String name = Base64.getEncoder().withoutPadding().encodeToString(principal.getEmail().getBytes(StandardCharsets.UTF_8));
		organizationInfo.setName(name);
		
		final String domainName = String.format(organizationDomain, name);
		final Domain domain = new Domain();
		domain.setName(domainName);
		organizationInfo.setDomains(new ArrayList<>());
		organizationInfo.getDomains().add(domain);
		
		final String location = adminClient.createOrganization(token, organizationInfo);
		final String id = location.substring(location.lastIndexOf('/') + 1);
		
		final String userId = principal.getSubject();
		adminClient.addMember(token, id, userId);
		
		final Organization organization = new Organization();
		organization.setId(id);
		organization.setName(name);
		organization.setDomain(String.format(organizationDomain, id));
		
		persistenceManagement.createPersistence(organization, principal);
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
				organization.setDomain(String.format(organizationDomain, organization.getId()));
			}
			return Optional.of(organization);
		}
		return Optional.empty();
	}
}
