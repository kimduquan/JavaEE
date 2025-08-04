package epf.persistence.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import io.agroal.api.AgroalDataSource;
import io.agroal.api.configuration.AgroalDataSourceConfiguration;
import io.agroal.api.configuration.supplier.AgroalDataSourceConfigurationSupplier;
import io.agroal.api.security.NamePrincipal;
import io.agroal.api.security.SimplePassword;
import io.agroal.pool.DataSource;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.customized.QuarkusConnectionProvider;
import io.quarkus.hibernate.orm.runtime.tenant.TenantConnectionResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@PersistenceUnitExtension
public class EntityTenantConnectionResolver implements TenantConnectionResolver {
	
	private final Map<String, QuarkusConnectionProvider> connectionProviders = new ConcurrentHashMap<>();
	
	@Inject
    @ConfigProperty(name = "datasource.username")
	String userName;
	
	@Inject
    @ConfigProperty(name = "datasource.password")
	String password;
	
	@Inject
    @ConfigProperty(name = "datasource.jdbc.url.format")
	String format;

	@Override
	public ConnectionProvider resolve(final String tenantId) {
		return connectionProviders.computeIfAbsent(tenantId, tenant -> {
			final String jdbcUrl = String.format(format, tenant);
			final AgroalDataSourceConfigurationSupplier supplier = new AgroalDataSourceConfigurationSupplier();
			supplier.connectionPoolConfiguration().connectionFactoryConfiguration().credential(new NamePrincipal(userName + "." + tenant)).credential(new SimplePassword(password)).jdbcUrl(jdbcUrl);
			final AgroalDataSourceConfiguration config = supplier.get();
			final AgroalDataSource dataSource = new DataSource(config);
			return new QuarkusConnectionProvider(dataSource);
		});
	}
}
