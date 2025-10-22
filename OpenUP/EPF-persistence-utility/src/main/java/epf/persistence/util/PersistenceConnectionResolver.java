package epf.persistence.util;

import epf.management.util.OrganizationUtil;

public abstract class PersistenceConnectionResolver<Connection> extends OrganizationConnectionResolver<Connection> {
	
	@Override
	protected String getExternalId(final String organizationId) {
		return OrganizationUtil.getDefaultPersistenceExternalId(organizationId);
	}
}
