package epf.query.util;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.management.util.OrganizationTenantResolver;

@RequestScoped
public class EPFTenantResolver extends OrganizationTenantResolver {
	
	@Inject
    JsonWebToken jwt;

	@Override
	protected JsonWebToken getToken() {
		return jwt;
	}
	
}
