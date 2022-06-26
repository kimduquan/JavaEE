package epf.security.management.internal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import epf.naming.Naming;
import epf.security.internal.IdentityStore;
import epf.security.schema.Security;
import epf.security.util.JdbcUtil;

/**
 * 
 */
@ApplicationScoped
public class TenantPersistence {

	/**
	 *
	 */
	transient final Map<String, EntityManagerFactory> managerFactories = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		managerFactories.forEach((tenant, factory) -> {
			factory.close();
		});
	}
	
	/**
	 * @param unitName
	 * @param schema
	 * @param tenant
	 * @param props
	 * @return
	 */
	private EntityManagerFactory createFactory(final String unitName, final String schema, final String tenant, final Map<String, Object> props) {
		final String jdbcUrl = JdbcUtil.formatJdbcUrl(Naming.Security.Internal.JDBC_URL_FORMAT, schema, tenant, "-");
    	props.put(Naming.Persistence.JDBC.JDBC_URL, jdbcUrl);
    	return Persistence.createEntityManagerFactory(unitName, props);
	}
	
	/**
	 * @param tenant
	 * @return
	 */
	public EntityManager createManager(final String tenant) {
		return managerFactories.computeIfAbsent(tenant, t -> createFactory(IdentityStore.SECURITY_MANAGEMENT_UNIT_NAME, Security.SCHEMA, tenant, new ConcurrentHashMap<>())).createEntityManager();
	}
}
