package epf.security.management.internal;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.security.enterprise.CallerPrincipal;
import javax.transaction.Transactional;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.naming.Naming;
import epf.schema.utility.TenantUtil;
import epf.security.internal.JPAPrincipal;
import epf.security.schema.Security;
import epf.security.util.Credential;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class ManagementIdentityStore {
	
	/**
	 * 
	 */
	@Inject
	transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@PersistenceContext(unitName = Naming.Security.Internal.SECURITY_MANAGEMENT_UNIT_NAME)
	transient EntityManager manager;

	@Transactional
	public CompletionStage<Void> putCredential(final Credential credential) throws Exception {
		Objects.requireNonNull(credential, "Credential");
		Objects.requireNonNull(credential.getCaller(), "Credential.caller");
		Objects.requireNonNull(credential.getPassword(), "Credential.password");
		final String tenant = TenantUtil.getTenantId(Security.SCHEMA, credential.getTenant().orElse(null));
		manager.setProperty(Naming.Management.TENANT, tenant);
		final Query query = manager.createNativeQuery(String.format(NativeQueries.CREATE_USER, credential.getCaller()));
		query.setParameter(1, new String(credential.getPassword().getValue()));
		query.executeUpdate();
		return executor.completedStage(null);
	}

	public CompletionStage<CallerPrincipal> authenticate(final Credential credential) throws Exception {
		final Map<String, Object> props = new ConcurrentHashMap<>();
        props.put(Naming.Persistence.JDBC.JDBC_USER, credential.getCaller());
        props.put(Naming.Persistence.JDBC.JDBC_PASSWORD, String.valueOf(credential.getPassword().getValue()));
        final String tenant = TenantUtil.getTenantId(Security.SCHEMA, credential.getTenant().orElse(null));
        props.put(Naming.Management.TENANT, tenant);
        return executor.supplyAsync(() -> Persistence.createEntityManagerFactory(Naming.Security.Internal.SECURITY_MANAGEMENT_UNIT_NAME, props))
        		.thenApply(factory -> {
        			try {
        				final Map<String, Object> newProps = new ConcurrentHashMap<>();
        				newProps.put(Naming.Management.TENANT, tenant);
        				final EntityManager manager = factory.createEntityManager(newProps);
        				final JPAPrincipal principal = new JPAPrincipal(credential.getTenant(), credential.getCaller(), factory, manager);
        				return principal;
		        	}
		        	catch(Exception ex) {
		        		factory.close();
		        		return null;
		        	}
        		});
	}

	@Transactional
	public CompletionStage<Void> setCallerGroup(final CallerPrincipal callerPrincipal, final String group) throws Exception {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		final JPAPrincipal principal = (JPAPrincipal) callerPrincipal;
		final String tenant = TenantUtil.getTenantId(Security.SCHEMA, principal.getTenant().orElse(null));
		manager.setProperty(Naming.Management.TENANT, tenant);
		final Query query = manager.createNativeQuery(String.format(NativeQueries.SET_ROLE, group, principal.getName()));
		query.executeUpdate();
		return executor.completedStage(null);
	}

	@Transactional
	public CompletionStage<Void> setCredential(final Credential credential) throws Exception {
		Objects.requireNonNull(credential, "Credential");
		Objects.requireNonNull(credential.getCaller(), "Credential.caller");
		Objects.requireNonNull(credential.getPassword(), "Credential.password");
		final String tenant = TenantUtil.getTenantId(Security.SCHEMA, credential.getTenant().orElse(null));
		manager.setProperty(Naming.Management.TENANT, tenant);
		manager.createNativeQuery(String.format(NativeQueries.SET_USER_PASSWORD, credential.getCaller())).setParameter(1, new String(credential.getPassword().getValue())).executeUpdate();
		return executor.completedStage(null);
	}

	public CompletionStage<Boolean> isCaller(final Credential credential) throws Exception {
		Objects.requireNonNull(credential, "Credential");
		Objects.requireNonNull(credential.getCaller(), "Credential.caller");
		final String tenant = TenantUtil.getTenantId(Security.SCHEMA, credential.getTenant().orElse(null));
		manager.setProperty(Naming.Management.TENANT, tenant);
		final Long count = (Long) manager.createNativeQuery(NativeQueries.CHECK_USER).setParameter(1, credential.getCaller()).getSingleResult();
		return executor.completedStage(count > 0);
	}
}
