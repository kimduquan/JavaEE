package epf.management.internal;

import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;
import epf.management.schema.Organization;
import epf.management.schema.Principal;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PersistenceManagement {
	
	@Inject
	transient DataSource managementDataSource;

	public void createOrganizationPersistence(final Organization organization, final Principal principal) throws Exception {
		try(Connection connection = managementDataSource.getConnection()) {
			try(Statement statement = connection.createStatement()){
				statement.execute("");
			}
		}
	}
}
