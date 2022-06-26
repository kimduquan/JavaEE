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
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.naming.Naming;
import epf.security.internal.IdentityStore;
import epf.security.internal.JPAPrincipal;
import epf.security.schema.Security;
import epf.security.util.Credential;
import epf.security.util.JdbcUtil;

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
	@Inject
	@ConfigProperty(name = Naming.Persistence.JDBC.JDBC_URL)
	transient String jdbcUrl;
	
	/**
	 * 
	 */
	@PersistenceContext(unitName = IdentityStore.SECURITY_SCHEMA_UNIT_NAME)
	transient EntityManager manager;
	
	/**
	 *
	 */
	@Inject
	transient TenantPersistence persistence;

	@Transactional
	public CompletionStage<Void> putCredential(final Credential credential) throws Exception {
		Objects.requireNonNull(credential, "Credential");
		Objects.requireNonNull(credential.getCaller(), "Credential.caller");
		Objects.requireNonNull(credential.getPassword(), "Credential.password");
		EntityManager entityManager = manager;
		if(credential.getTenant().isPresent()) {
			entityManager = persistence.createManager(credential.getTenant().get());
			if(!entityManager.isJoinedToTransaction()) {
				entityManager.joinTransaction();
			}
		}
		final Query query = entityManager.createNativeQuery(String.format(NativeQueries.CREATE_USER, credential.getCaller()));
		query.setParameter(1, new String(credential.getPassword().getValue()));
		query.executeUpdate();
		return executor.completedStage(null);
	}

	public CompletionStage<CallerPrincipal> authenticate(Credential credential) throws Exception {
		final Map<String, Object> props = new ConcurrentHashMap<>();
        props.put(Naming.Persistence.JDBC.JDBC_USER, credential.getCaller());
        props.put(Naming.Persistence.JDBC.JDBC_PASSWORD, String.valueOf(credential.getPassword().getValue()));
        credential.getTenant().ifPresent(tenant -> {
        	final String jdbcUrl = JdbcUtil.formatJdbcUrl(Naming.Security.Internal.JDBC_URL_FORMAT, Security.SCHEMA, tenant, "-");
        	props.put(Naming.Persistence.JDBC.JDBC_URL, jdbcUrl);
        });
        return executor.supplyAsync(() -> Persistence.createEntityManagerFactory(IdentityStore.SECURITY_UNIT_NAME, props))
        		.thenApply(factory -> {
        			try {
        				final EntityManager manager = factory.createEntityManager();
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
		EntityManager entityManager = manager;
		final JPAPrincipal principal = (JPAPrincipal) callerPrincipal;
		if(principal.getTenant().isPresent()) {
			entityManager = persistence.createManager(principal.getTenant().get());
			if(!entityManager.isJoinedToTransaction()) {
				entityManager.joinTransaction();
			}
		}
		final Query query = entityManager.createNativeQuery(String.format(NativeQueries.SET_ROLE, group, principal.getName()));
		query.executeUpdate();
		return executor.completedStage(null);
	}

	@Transactional
	public CompletionStage<Void> setCredential(final Credential credential) throws Exception {
		Objects.requireNonNull(credential, "Credential");
		Objects.requireNonNull(credential.getCaller(), "Credential.caller");
		Objects.requireNonNull(credential.getPassword(), "Credential.password");
		EntityManager entityManager = manager;
		if(credential.getTenant().isPresent()) {
			entityManager = persistence.createManager(credential.getTenant().get());
			if(!entityManager.isJoinedToTransaction()) {
				entityManager.joinTransaction();
			}
		}
		entityManager.createNativeQuery(String.format(NativeQueries.SET_USER__PASSWORD, credential.getCaller())).setParameter(1, new String(credential.getPassword().getValue())).executeUpdate();
		return executor.completedStage(null);
	}

	public CompletionStage<Boolean> isCaller(final Credential credential) throws Exception {
		Objects.requireNonNull(credential, "Credential");
		Objects.requireNonNull(credential.getCaller(), "Credential.caller");
		EntityManager entityManager = manager;
		if(credential.getTenant().isPresent()) {
			entityManager = persistence.createManager(credential.getTenant().get());
		}
		final Long count = (Long) entityManager.createNativeQuery(NativeQueries.CHECK_USER).setParameter(1, credential.getCaller()).getSingleResult();
		return executor.completedStage(count > 0);
	}
}
