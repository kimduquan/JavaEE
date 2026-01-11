package epf.query.internal;

import java.util.Optional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.naming.Naming;
import epf.persistence.util.QueryConnectionResolver;
import io.agroal.api.AgroalDataSource;
import io.agroal.api.configuration.AgroalDataSourceConfiguration;
import io.agroal.pool.DataSource;
import io.quarkus.hibernate.orm.PersistenceUnitExtension;
import io.quarkus.hibernate.orm.runtime.customized.QuarkusConnectionProvider;
import io.quarkus.hibernate.orm.runtime.tenant.TenantConnectionResolver;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@PersistenceUnitExtension
public class ConnectionResolver extends QueryConnectionResolver<QuarkusConnectionProvider> implements TenantConnectionResolver {
	
	@Inject
    @ConfigProperty(name = Naming.Persistence.Internal.JDBC_URL_FORMAT)
	String jdbcUrlFormat;
	
	@Inject
    @ConfigProperty(name = Naming.Persistence.Internal.CONNECTION_POOL_SIZE)
	int connectionPoolSize;
	
	@Inject
    @ConfigProperty(name = Naming.Persistence.Internal.DEFAULT_USER)
	Optional<String> user;
	
	@Inject
    @ConfigProperty(name = Naming.Persistence.Internal.DEFAULT_PASSWORD)
	Optional<String> password;

	@Override
	protected AgroalDataSource newDataSource(final AgroalDataSourceConfiguration config) {
		return new DataSource(config);
	}

	@Override
	protected QuarkusConnectionProvider newConnection(final AgroalDataSource dataSource) {
		return new QuarkusConnectionProvider(dataSource);
	}

	@Override
	protected String getJdbcUrlFormat() {
		return jdbcUrlFormat;
	}

	@Override
	protected int getConnectionPoolSize() {
		return connectionPoolSize;
	}

	@Override
	protected Optional<String> getDefaultPassword() {
		return password;
	}

	@Override
	protected Optional<String> getDefaultUser() {
		return user;
	}
}
