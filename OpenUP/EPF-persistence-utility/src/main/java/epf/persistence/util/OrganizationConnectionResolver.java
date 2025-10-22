package epf.persistence.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import epf.management.util.OrganizationUtil;
import io.agroal.api.AgroalDataSource;
import io.agroal.api.configuration.AgroalDataSourceConfiguration;
import io.agroal.api.configuration.AgroalConnectionPoolConfiguration.TransactionRequirement;
import io.agroal.api.configuration.supplier.AgroalConnectionPoolConfigurationSupplier;
import io.agroal.api.configuration.supplier.AgroalDataSourceConfigurationSupplier;
import io.agroal.api.security.NamePrincipal;
import io.agroal.api.security.SimplePassword;
import io.agroal.api.transaction.TransactionIntegration;

public abstract class OrganizationConnectionResolver<Connection> {
	
	private final Map<String, Connection> connections = new ConcurrentHashMap<>();
	
	protected abstract TransactionIntegration getTransactionIntegration();
	protected abstract AgroalDataSource newDataSource(final AgroalDataSourceConfiguration config);
	protected abstract Connection newConnection(final AgroalDataSource dataSource);
	protected abstract String getJdbcUrlFormat();
	protected abstract int getConnectionPoolSize();
	protected abstract String getExternalId(final String organizationId);
	
	public Connection resolve(final String tenantId) {
		return connections.computeIfAbsent(tenantId, organizationId -> {
			final String externalId = getExternalId(tenantId);
			final String database = OrganizationUtil.getDefaultDatabase(organizationId);
			final String userName = OrganizationUtil.getDefaultUserName(organizationId);
			final String password = OrganizationUtil.getDefaultPassword(organizationId);
			final String jdbcUrl = String.format(getJdbcUrlFormat(), database);
			final AgroalDataSourceConfigurationSupplier supplier = new AgroalDataSourceConfigurationSupplier();
			final TransactionIntegration transactionIntegration = getTransactionIntegration();
			final AgroalConnectionPoolConfigurationSupplier connectionSupplier = supplier.connectionPoolConfiguration();
			connectionSupplier.maxSize(getConnectionPoolSize())
			.connectionFactoryConfiguration()
			.credential(new NamePrincipal(userName + "." + externalId))
			.credential(new SimplePassword(password))
			.jdbcUrl(jdbcUrl);
			if(transactionIntegration != null) {
				connectionSupplier.transactionIntegration(transactionIntegration)
				.transactionRequirement(TransactionRequirement.STRICT);
			}
			final AgroalDataSourceConfiguration config = supplier.get();
			final AgroalDataSource dataSource = newDataSource(config);
			return newConnection(dataSource);
		});
	}
}
