package epf.query.util;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import epf.query.persistence.Message;
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
    Message message;

	@Override
	public String getDefaultTenantId() {
		return "Public".toUpperCase();
	}

	@Override
	public String resolveTenantId() {
		final String tenantId = message.getEvent() != null ? TenantUtil.getTenantId(message.getEvent().getSchema(), message.getEvent().getTenant()) : null;
		if(tenantId != null) {
			return tenantId.toUpperCase();
		}
		return getDefaultTenantId();
	}

}
