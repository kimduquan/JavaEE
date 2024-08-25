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
import epf.security.internal.JPAPrincipal;
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
		final Query query = manager.createNativeQuery(String.format(NativeQueries.CREATE_USER, credential.getCaller()));
		query.setParameter(1, new String(credential.getPassword().getValue()));
		query.executeUpdate();
		manager.flush();
		return executor.completedStage(null);
	}

	public CompletionStage<CallerPrincipal> authenticate(final Credential credential) throws Exception {
		final Map<String, Object> props = new ConcurrentHashMap<>();
        props.put(Naming.Persistence.JDBC.JDBC_USER, credential.getCaller());
        props.put(Naming.Persistence.JDBC.JDBC_PASSWORD, String.valueOf(credential.getPassword().getValue()));
        return executor.supplyAsync(() -> Persistence.createEntityManagerFactory(Naming.Security.Internal.SECURITY_MANAGEMENT_UNIT_NAME, props))
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
		final Query query = manager.createNativeQuery(String.format(NativeQueries.SET_ROLE, group, callerPrincipal.getName()));
		query.executeUpdate();
		manager.flush();
		return executor.completedStage(null);
	}

	@Transactional
	public CompletionStage<Void> setCredential(final Credential credential) throws Exception {
		Objects.requireNonNull(credential, "Credential");
		Objects.requireNonNull(credential.getCaller(), "Credential.caller");
		Objects.requireNonNull(credential.getPassword(), "Credential.password");
		manager.createNativeQuery(String.format(NativeQueries.SET_USER_PASSWORD, credential.getCaller())).setParameter(1, new String(credential.getPassword().getValue())).executeUpdate();
		manager.flush();
		return executor.completedStage(null);
	}

	public CompletionStage<Boolean> isCaller(final Credential credential) throws Exception {
		Objects.requireNonNull(credential, "Credential");
		Objects.requireNonNull(credential.getCaller(), "Credential.caller");
		final Long count = (Long) manager.createNativeQuery(NativeQueries.CHECK_USER).setParameter(1, credential.getCaller()).getSingleResult();
		return executor.completedStage(count > 0);
	}
}
