package epf.management.internal;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import javax.sql.DataSource;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.management.config.util.ConfigPath;
import epf.management.external.PersistenceClient;
import epf.management.persistence.schema.PersistenceTenant;
import epf.management.persistence.schema.PersistenceUser;
import epf.management.persistence.schema.UpdatePersistenceTenantInfo;
import epf.management.schema.Organization;
import epf.management.schema.Principal;
import epf.management.security.util.SecurityUtil;
import epf.management.util.OrganizationUtil;
import epf.naming.Naming;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PersistenceManagement {
	
	@Inject
	@ConfigProperty(name = Naming.Management.Internal.PERSISTENCE_TEMPLATE)
	String databaseTemplate;
	
	@Inject
	@ConfigProperty(name = Naming.Persistence.Internal.DEFAULT_USER)
	Optional<String> defaultUser;
	
	@Inject
	@ConfigProperty(name = Naming.Persistence.Internal.DEFAULT_PASSWORD)
	Optional<String> defaultPassword;
	
	@Inject
	@ConfigProperty(name = Naming.Management.Internal.PERSISTENCE_DATASOURCE_HOST)
	String databaseHost;
	
	@Inject
	@ConfigProperty(name = Naming.Management.Internal.PERSISTENCE_DATASOURCE_PORT)
	Integer databasePort;
	
	@Inject
	@ConfigProperty(name = Naming.Management.Internal.QUERY_DATASOURCE_HOST)
	String queryDatabaseHost;
	
	@Inject
	@ConfigProperty(name = Naming.Management.Internal.QUERY_DATASOURCE_PORT)
	Integer queryDatabasePort;
	
	@Inject
	transient DataSource managementDataSource;
	
	@RestClient
	transient PersistenceClient persistenceClient;
	
	private final ConfigPath config = new ConfigPath("/epf/config/persistence");

	public void createPersistence(final Organization organization, final Principal principal) throws Exception {
		final String databaseName = OrganizationUtil.getDefaultDatabase(organization.getId());
		
		String userName = defaultUser.orElse(null);
		if(userName == null) {
			userName = OrganizationUtil.getDefaultUserName(organization.getId());
		}
		
		String password = defaultPassword.orElse(null);
		if(password == null) {
			password = OrganizationUtil.getDefaultPassword(organization.getId());
		}
		
		try(Connection connection = managementDataSource.getConnection()) {
			try(Statement statement = connection.createStatement()){
				final String createDatabaseSql = "CREATE DATABASE \"" + databaseName + "\" OWNER \"" + userName + "\" TEMPLATE " + databaseTemplate + ";";
				statement.execute(createDatabaseSql);
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
		
		final PersistenceUser defaultPersistenceUser = new PersistenceUser();
		defaultPersistenceUser.setDb_password(password);
		defaultPersistenceUser.setDb_user(userName);
		defaultPersistenceUser.setMode_type("transaction");
		defaultPersistenceUser.setPool_size(1);
		
		persistenceTenant.getUsers().add(defaultPersistenceUser);
		
		final UpdatePersistenceTenantInfo updateInfo = new UpdatePersistenceTenantInfo();
		updateInfo.setTenant(persistenceTenant);
		
		final String persistenceSecret = config.getValue(Naming.Management.Internal.PERSISTENCE_MANAGEMENT_SECRET);
		final String securityToken = SecurityUtil.generateToken(persistenceSecret);
		final String authorization = "Bearer " + securityToken;
		final String persistenceExternalId = OrganizationUtil.getDefaultPersistenceExternalId(organization.getId());
		
		persistenceClient.createOrUpdateTenant(authorization, persistenceExternalId, updateInfo);
		
		final PersistenceTenant queryTenant = new PersistenceTenant();
		queryTenant.setDb_database(databaseName);
		queryTenant.setDb_host(queryDatabaseHost);
		queryTenant.setDb_port(queryDatabasePort);
		queryTenant.setEnforce_ssl(false);
		queryTenant.setIp_version("auto");
		queryTenant.setRequire_user(true);
		queryTenant.setUsers(new ArrayList<>());
		
		final PersistenceUser defaultQueryUser = new PersistenceUser();
		defaultQueryUser.setDb_password(password);
		defaultQueryUser.setDb_user(userName);
		defaultQueryUser.setMode_type("session");
		defaultQueryUser.setPool_size(3);
		
		queryTenant.getUsers().add(defaultQueryUser);
		
		updateInfo.setTenant(queryTenant);
		
		final String queryExternalId = OrganizationUtil.getDefaultQueryExternalId(organization.getId());
		
		persistenceClient.createOrUpdateTenant(authorization, queryExternalId, updateInfo);
	}
}
