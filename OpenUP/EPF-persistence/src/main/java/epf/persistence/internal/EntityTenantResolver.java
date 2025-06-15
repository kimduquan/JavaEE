package epf.persistence.internal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.management.util.TenantUtil;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;

@ApplicationScoped
public class EntityTenantResolver implements TenantResolver {
	
	@Inject
    JsonWebToken jwt;

	@Override
	public String getDefaultTenantId() {
		return TenantUtil.getDefaultTenantId();
	}

	@Override
	public String resolveTenantId() {
		final String tenantId = TenantUtil.getTenantId(jwt);
		if(tenantId != null) {
			return tenantId;
		}
		return getDefaultTenantId();
	}

}
