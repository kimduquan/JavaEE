package epf.persistence.util;

import epf.management.util.OrganizationUtil;
import io.agroal.api.transaction.TransactionIntegration;

public abstract class QueryConnectionResolver<Connection> extends OrganizationConnectionResolver<Connection> {
	
	@Override
	protected String getExternalId(final String organizationId) {
		return OrganizationUtil.getDefaultQueryExternalId(organizationId);
	}

	@Override
	protected TransactionIntegration getTransactionIntegration() {
		return null;
	}
}
