package epf.query.util;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.schema.utility.Request;
import epf.schema.utility.TenantUtil;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;

/**
 * @author PC
 *
 */
@PersistenceUnitExtension(epf.query.Naming.QUERY_UNIT_NAME)
@ApplicationScoped
public class EPFTenantResolver implements TenantResolver {
	
	/**
	 * 
	 */
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
