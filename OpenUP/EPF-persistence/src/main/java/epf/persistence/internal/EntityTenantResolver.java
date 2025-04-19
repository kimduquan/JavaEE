package epf.persistence.internal;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import epf.schema.utility.Request;
import epf.schema.utility.TenantUtil;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;

@PersistenceUnitExtension
@RequestScoped
public class EntityTenantResolver implements TenantResolver {
	
	@Inject
    Request request;

	@Override
	public String getDefaultTenantId() {
		return "public";
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
