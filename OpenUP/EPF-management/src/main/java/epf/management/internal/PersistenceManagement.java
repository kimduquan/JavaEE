package epf.management.internal;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
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
		final String databaseName = OrganizationUtil.getDefaultPersistenceDatabase(organization.getId());
		final String userName = OrganizationUtil.getDefaultPersistenceUserName(organization.getId());
		final String password = OrganizationUtil.getDefaultPersistencePassword(organization.getId());
		final String queryUserName = OrganizationUtil.getDefaultQueryUserName(organization.getId());
		final String queryPassword = OrganizationUtil.getDefaultQueryPassword(organization.getId());
		
		try(Connection connection = managementDataSource.getConnection()) {
			try(Statement statement = connection.createStatement()){
				final String createUserSql = "CREATE USER \"" + userName + "\" WITH PASSWORD '" + password + "';";
				statement.execute(createUserSql);
				final String createQueryUserSql = "CREATE USER \"" + queryUserName + "\" WITH PASSWORD '" + queryPassword + "';";
				statement.execute(createQueryUserSql);
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
		
		final PersistenceUser persistenceUser = new PersistenceUser();
		persistenceUser.setDb_password(password);
		persistenceUser.setDb_user(userName);
		persistenceUser.setMode_type("transaction");
		persistenceUser.setPool_size(1);
		
		final PersistenceUser queryUser = new PersistenceUser();
		queryUser.setDb_password(queryPassword);
		queryUser.setDb_user(queryUserName);
		queryUser.setMode_type("session");
		queryUser.setPool_size(3);
		
		persistenceTenant.getUsers().add(persistenceUser);
		persistenceTenant.getUsers().add(queryUser);
		
		final UpdatePersistenceTenantInfo updateInfo = new UpdatePersistenceTenantInfo();
		updateInfo.setTenant(persistenceTenant);
		
		final String securityToken = SecurityUtil.generateToken(persistenceClientSecert);
		final String authorization = "Bearer " + securityToken;
		
		persistenceClient.createOrUpdateTenant(authorization, organization.getId(), updateInfo);
	}
}
