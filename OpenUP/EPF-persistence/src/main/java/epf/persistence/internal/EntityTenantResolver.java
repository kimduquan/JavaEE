package epf.persistence.internal;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.management.util.OrganizationTenantResolver;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;

@RequestScoped
@PersistenceUnitExtension
public class EntityTenantResolver extends OrganizationTenantResolver {
	
	@Inject
    JsonWebToken jwt;

	@Override
	protected JsonWebToken getToken() {
		return jwt;
	}
}
