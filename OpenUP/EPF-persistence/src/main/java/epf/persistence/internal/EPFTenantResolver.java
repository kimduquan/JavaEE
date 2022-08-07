package epf.persistence.internal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import epf.schema.utility.Request;
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
		if(request.getSchema() != null) {
			if(request.getTenant() != null) {
				return request.getSchema() + "_" + request.getTenant();
			}
			return request.getSchema();
		}
		return getDefaultTenantId();
	}

}
