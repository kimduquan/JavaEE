package epf.persistence.internal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.schema.utility.TenantUtil;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;

@ApplicationScoped
public class EntityTenantResolver implements TenantResolver {
	
	@Inject
    JsonWebToken jwt;

	@Override
	public String getDefaultTenantId() {
		return "";
	}

	@Override
	public String resolveTenantId() {
		final String tenantId = TenantUtil.getTenantId(request.getSchema(), request.getTenant());
		if(tenantId != null) {
			return tenantId;
		}
		return getDefaultTenantId();
	}

}
