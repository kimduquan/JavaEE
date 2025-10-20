package epf.persistence.util;

import epf.management.util.OrganizationUtil;

public abstract class PersistenceConnectionResolver<Connection> extends OrganizationConnectionResolver<Connection> {
	
	@Override
	protected String getExternalId(final String organizationId) {
		return OrganizationUtil.getDefaultPersistenceExternalId(organizationId);
	}
	
	@Override
	protected String getDatabase(final String organizationId) {
		return OrganizationUtil.getDefaultPersistenceDatabase(organizationId);
	}

	@Override
	protected String getUserName(final String organizationId) {
		return OrganizationUtil.getDefaultPersistenceUserName(organizationId);
	}

	@Override
	protected String getPassword(final String organizationId) {
		return OrganizationUtil.getDefaultPersistencePassword(organizationId);
	}

}
