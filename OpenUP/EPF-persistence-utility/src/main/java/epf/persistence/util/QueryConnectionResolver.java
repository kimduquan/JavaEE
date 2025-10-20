package epf.persistence.util;

import epf.management.util.OrganizationUtil;
import io.agroal.api.transaction.TransactionIntegration;

public abstract class QueryConnectionResolver<Connection> extends OrganizationConnectionResolver<Connection> {
	
	@Override
	protected String getExternalId(final String organizationId) {
		return OrganizationUtil.getDefaultQueryExternalId(organizationId);
	}
	
	@Override
	protected String getDatabase(final String organizationId) {
		return OrganizationUtil.getDefaultQueryDatabase(organizationId);
	}

	@Override
	protected String getUserName(final String organizationId) {
		return OrganizationUtil.getDefaultQueryUserName(organizationId);
	}

	@Override
	protected String getPassword(final String organizationId) {
		return OrganizationUtil.getDefaultQueryPassword(organizationId);
	}

	@Override
	protected TransactionIntegration getTransactionIntegration() {
		return null;
	}
}
