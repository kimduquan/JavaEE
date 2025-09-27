package epf.persistence.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import epf.management.util.OrganizationUtil;
import io.agroal.api.AgroalDataSource;
import io.agroal.api.configuration.AgroalDataSourceConfiguration;
import io.agroal.api.configuration.AgroalConnectionPoolConfiguration.TransactionRequirement;
import io.agroal.api.configuration.supplier.AgroalDataSourceConfigurationSupplier;
import io.agroal.api.security.NamePrincipal;
import io.agroal.api.security.SimplePassword;
import io.agroal.api.transaction.TransactionIntegration;

public abstract class OrganizationConnectionResolver<Connection> {
	
	private final Map<String, Connection> connections = new ConcurrentHashMap<>();
	
	protected abstract TransactionIntegration newTransactionIntegration();
	protected abstract AgroalDataSource newDataSource(final AgroalDataSourceConfiguration config);
	protected abstract Connection newConnection(final AgroalDataSource dataSource);
	protected abstract String getJdbcUrlFormat();
	protected abstract int getConnectionPoolSize();
	
	public Connection resolve(final String tenantId) {
		return connections.computeIfAbsent(tenantId, organizationId -> {
			final String database = OrganizationUtil.getDefaultPersistenceDatabase(organizationId);
			final String userName = OrganizationUtil.getDefaultPersistenceUserName(organizationId);
			final String password = OrganizationUtil.getDefaultPersistencePassword(organizationId);
			final String jdbcUrl = String.format(getJdbcUrlFormat(), database);
			final AgroalDataSourceConfigurationSupplier supplier = new AgroalDataSourceConfigurationSupplier();
			supplier.connectionPoolConfiguration()
			.maxSize(getConnectionPoolSize())
			.transactionIntegration(newTransactionIntegration())
			.transactionRequirement(TransactionRequirement.STRICT)
			.connectionFactoryConfiguration()
			.credential(new NamePrincipal(userName + "." + organizationId))
			.credential(new SimplePassword(password))
			.jdbcUrl(jdbcUrl);
			final AgroalDataSourceConfiguration config = supplier.get();
			final AgroalDataSource dataSource = newDataSource(config);
			return newConnection(dataSource);
		});
	}
}
