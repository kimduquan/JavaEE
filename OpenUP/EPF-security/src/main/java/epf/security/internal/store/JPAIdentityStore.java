package epf.security.internal.store;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.naming.Naming;
import epf.schema.utility.TenantUtil;
import epf.security.internal.JPAPrincipal;
import epf.security.internal.NativeQueries;
import epf.security.schema.Security;
import epf.security.util.Credential;
import epf.util.StringUtil;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class JPAIdentityStore {
	
	/**
	 * 
	 */
	@Inject
	private transient ManagedExecutor executor;
	
	/**
	 *
	 */
	@PersistenceContext(unitName = Naming.Security.Internal.SECURITY_UNIT_NAME)
	private transient EntityManager manager;

	/**
	 * @param callerPrincipal
	 * @return
	 */
	public CompletionStage<Set<String>> getCallerGroups(final Credential credential) {
		Objects.requireNonNull(credential, "Credential");
		final String tenant = TenantUtil.getTenantId(Security.SCHEMA, credential.getTenant().orElse(null));
		manager.setProperty(Naming.Management.MANAGEMENT_TENANT, tenant);
		final Query query = manager.createNativeQuery(NativeQueries.GET_CURRENT_ROLES);
		final Stream<?> stream = query.setParameter(1, credential.getCaller()).getResultStream();
		final Set<String> groups = stream.map(role -> StringUtil.toPascalSnakeCase(role.toString().split("_"))).collect(Collectors.toSet());
		return executor.completedStage(groups);
	}

	/**
	 * @param credential
	 * @return
	 * @throws Exception
	 */
	public CompletionStage<JPAPrincipal> authenticate(final Credential credential) throws Exception {
		final Map<String, Object> props = new ConcurrentHashMap<>();
        props.put(Naming.Persistence.JDBC.JDBC_USER, credential.getCaller());
        props.put(Naming.Persistence.JDBC.JDBC_PASSWORD, String.valueOf(credential.getPassword().getValue()));
        final String tenant = TenantUtil.getTenantId(Security.SCHEMA, credential.getTenant().orElse(null));
        props.put(Naming.Management.MANAGEMENT_TENANT, tenant);
        return executor.supplyAsync(() -> Persistence.createEntityManagerFactory(Naming.Security.Internal.SECURITY_UNIT_NAME, props))
        		.thenApply(factory -> {
        			try {
        				final Map<String, Object> newProps = new ConcurrentHashMap<>();
        				newProps.put(Naming.Management.MANAGEMENT_TENANT, tenant);
        				final EntityManager manager = factory.createEntityManager(newProps);
        				return new JPAPrincipal(credential.getTenant(), credential.getCaller(), factory, manager);
		        	}
		        	catch(Exception ex) {
		        		factory.close();
		        		return null;
		        	}
        		});
	}
}
