package epf.management.internal;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import javax.sql.DataSource;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.management.persistence.schema.PersistenceTenant;
import epf.management.persistence.schema.PersistenceUser;
import epf.management.schema.Organization;
import epf.management.schema.Principal;
import epf.management.security.util.SecurityUtil;
import epf.naming.Naming;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PersistenceManagement {
	
	@Inject
	@ConfigProperty(name = Naming.Management.Internal.PERSISTENCE_TEMPLATE)
	String databaseTemplate;
	
	@Inject
	@ConfigProperty(name = Naming.Management.Internal.PERSISTENCE_MANAGEMENT_SECURITY_SECRET)
	String persistenceClientSecert;
	
	@Inject
	@ConfigProperty(name = Naming.Management.Internal.PERSISTENCE_DATASOURCE_HOST)
	String databaseHost;
	
	@Inject
	@ConfigProperty(name = Naming.Management.Internal.PERSISTENCE_DATASOURCE_PORT)
	Integer databasePort;
	
	@Inject
	transient DataSource managementDataSource;
	
	@RestClient
	transient PersistenceClient persistenceClient;

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
		
		final PersistenceTenant persistenceTenant = new PersistenceTenant();
		persistenceTenant.setDb_database(databaseName);
		persistenceTenant.setDb_host(databaseHost);
		persistenceTenant.setDb_port(databasePort);
		persistenceTenant.setEnforce_ssl(false);
		persistenceTenant.setIp_version("auto");
		persistenceTenant.setRequire_user(true);
		persistenceTenant.setUsers(new ArrayList<>());
		
		final PersistenceUser persistenceUser = new PersistenceUser();
		persistenceUser.setDb_password(password);
		persistenceUser.setDb_user(userName);
		persistenceUser.setMode_type("transaction");
		persistenceUser.setPool_size(1);
		
		persistenceTenant.getUsers().add(persistenceUser);
		
		final String securityToken = SecurityUtil.generateToken(persistenceClientSecert);
		final String authorization = "Bearer " + securityToken;
		
		persistenceClient.createOrUpdateTenant(authorization, organization.getId(), persistenceTenant);
	}
}
