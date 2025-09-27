package epf.persistence.internal;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.management.util.OrganizationTenantResolver;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;

@RequestScoped
@PersistenceUnitExtension
public class PersistenceTenantResolver extends OrganizationTenantResolver implements TenantResolver {
	
	@Inject
    JsonWebToken jwt;

	@Override
	protected JsonWebToken getToken() {
		return jwt;
	}
}
