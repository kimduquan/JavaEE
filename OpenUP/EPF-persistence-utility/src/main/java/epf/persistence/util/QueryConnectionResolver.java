package epf.persistence.util;

import epf.management.util.OrganizationUtil;

public abstract class QueryConnectionResolver<Connection> extends OrganizationConnectionResolver<Connection> {

	@Override
	protected String getUserName(final String organizationId) {
		return OrganizationUtil.getDefaultQueryUserName(organizationId);
	}

	@Override
	protected String getPassword(final String organizationId) {
		return OrganizationUtil.getDefaultQueryPassword(organizationId);
	}

}
