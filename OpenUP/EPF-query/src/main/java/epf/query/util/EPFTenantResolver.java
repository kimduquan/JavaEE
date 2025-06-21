package epf.query.util;

import jakarta.enterprise.context.ApplicationScoped;
import epf.management.util.TenantUtil;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;

@ApplicationScoped
public class EPFTenantResolver implements TenantResolver {

	@Override
	public String getDefaultTenantId() {
		return TenantUtil.getDefaultTenantId();
	}

	@Override
	public String resolveTenantId() {
		String tenantId = TenantUtil.getTenantId();
		if(tenantId == null) {
			tenantId = getDefaultTenantId();
		}
		return tenantId.toUpperCase();
	}

}
