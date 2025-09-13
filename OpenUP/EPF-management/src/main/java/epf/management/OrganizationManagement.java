package epf.management;

import epf.management.schema.Organization;
import epf.management.schema.Principal;
import epf.naming.Naming;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrganizationManagement {

	@CacheResult(cacheName = Naming.Management.ORGANIZATION_MANAGEMENT)
	public Organization getPrincipalOrganization(@CacheKey final String tokenId, final Principal principal) throws Exception {
		return null;
	}
}
