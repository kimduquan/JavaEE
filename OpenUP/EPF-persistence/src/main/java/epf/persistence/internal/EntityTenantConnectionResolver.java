package epf.persistence.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

import epf.management.util.OrganizationUtil;
import io.agroal.api.AgroalDataSource;
import io.agroal.api.configuration.AgroalConnectionPoolConfiguration.TransactionRequirement;
import io.agroal.api.configuration.AgroalDataSourceConfiguration;
import io.agroal.api.configuration.supplier.AgroalDataSourceConfigurationSupplier;
import io.agroal.api.security.NamePrincipal;
import io.agroal.api.security.SimplePassword;
import io.agroal.narayana.NarayanaTransactionIntegration;
import io.agroal.pool.DataSource;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.customized.QuarkusConnectionProvider;
import io.quarkus.hibernate.orm.runtime.tenant.TenantConnectionResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.TransactionSynchronizationRegistry;

@ApplicationScoped
@PersistenceUnitExtension
public class EntityTenantConnectionResolver implements TenantConnectionResolver {
	
	private final Map<String, QuarkusConnectionProvider> connectionProviders = new ConcurrentHashMap<>();
	
	@Inject
    @ConfigProperty(name = "epf.datasource.jdbc.url.format")
	String format;
	
	@Inject
    @ConfigProperty(name = "epf.datasource.connection.pool.size")
	int connectionPoolSize;
	
	@Inject
	transient TransactionManager transactionManager;
	
	@Inject
    transient TransactionSynchronizationRegistry transactionSynchronizationRegistry;

	@Override
	public ConnectionProvider resolve(final String tenantId) {
		return connectionProviders.computeIfAbsent(tenantId, orgnanizationId -> {
			final String database = OrganizationUtil.getDefaultPersistenceDatabase(orgnanizationId);
			final String userName = OrganizationUtil.getDefaultPersistenceUserName(orgnanizationId);
			final String password = OrganizationUtil.getDefaultPersistencePassword(orgnanizationId);
			final String jdbcUrl = String.format(format, database);
			final AgroalDataSourceConfigurationSupplier supplier = new AgroalDataSourceConfigurationSupplier();
			supplier.connectionPoolConfiguration()
			.maxSize(connectionPoolSize)
			.transactionIntegration(new NarayanaTransactionIntegration(transactionManager, transactionSynchronizationRegistry))
			.transactionRequirement(TransactionRequirement.STRICT)
			.connectionFactoryConfiguration()
			.credential(new NamePrincipal(userName + "." + orgnanizationId))
			.credential(new SimplePassword(password))
			.jdbcUrl(jdbcUrl);
			final AgroalDataSourceConfiguration config = supplier.get();
			final AgroalDataSource dataSource = new DataSource(config);
			return new QuarkusConnectionProvider(dataSource);
		});
	}
}
