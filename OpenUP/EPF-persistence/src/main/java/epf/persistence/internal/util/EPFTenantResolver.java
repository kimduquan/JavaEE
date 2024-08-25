package epf.persistence.internal.util;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import epf.schema.utility.Request;
import epf.schema.utility.TenantUtil;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;

/**
 * @author PC
 *
 */
@PersistenceUnitExtension
@RequestScoped
public class EPFTenantResolver implements TenantResolver {
	
	/**
	 * 
	 */
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
