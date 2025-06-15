package epf.query.util;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import epf.management.util.TenantUtil;
import epf.schema.utility.Request;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;

@PersistenceUnitExtension(epf.query.Naming.QUERY_UNIT_NAME)
@ApplicationScoped
public class EPFTenantResolver implements TenantResolver {
	
	@Inject
    Request request;

	@Override
	public String getDefaultTenantId() {
		return "Public".toUpperCase();
	}

	@Override
	public String resolveTenantId() {
		String tenantId = TenantUtil.getTenantId(request.getSchema(), request.getTenant());
		if(tenantId == null) {
			tenantId = getDefaultTenantId();
		}
		return tenantId.toUpperCase();
	}

}
