package epf.query.util;

import javax.enterprise.context.RequestScoped;
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
@RequestScoped
public class EPFTenantResolver implements TenantResolver {
	
	/**
	 * 
	 */
	@Inject
    Request request;

	@Override
	public String getDefaultTenantId() {
		return "PUBLIC";
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
