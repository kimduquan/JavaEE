package epf.management.internal;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Base64;
import javax.sql.DataSource;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.management.schema.Organization;
import epf.management.schema.Principal;
import epf.naming.Naming;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PersistenceManagement {
	
	@Inject
	@ConfigProperty(name = Naming.Management.Internal.PERSISTENCE_TEMPLATE)
	private String databaseTemplate;
	
	@Inject
	transient DataSource managementDataSource;

	public void createPersistence(final Organization organization, final Principal principal) throws Exception {
		final String databaseName = organization.getId();
		final String userName = organization.getId();
		final String password = Base64.getEncoder().withoutPadding().encodeToString(organization.getId().getBytes(StandardCharsets.UTF_8));
		try(Connection connection = managementDataSource.getConnection()) {
			try(Statement statement = connection.createStatement()){
				statement.execute("CREATE USER '" + userName + "' WITH PASSWORD '" + password + "';");
				statement.execute("CREATE DATABASE '" + databaseName + "' OWNER '" + userName + "' TEMPLATE " + databaseTemplate + ";");
			}
		}
	}
}
