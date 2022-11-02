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
import org.eclipse.microprofile.jwt.JsonWebToken;
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
	 * @param credential
	 * @return
	 */
	public CompletionStage<Set<String>> getCallerGroups(final Credential credential) {
		Objects.requireNonNull(credential, "Credential");
		return getCallerGroups(credential.getCaller(), credential.getTenant().orElse(null));
	}

	/**
	 * @param jwt
	 * @return
	 */
	public CompletionStage<Set<String>> getCallerGroups(final JsonWebToken jwt) {
		Objects.requireNonNull(jwt, "JsonWebToken");
		return getCallerGroups(jwt.getName(), jwt.getClaim(Naming.Management.TENANT));
	}
	
	private CompletionStage<Set<String>> getCallerGroups(final String name, final String tenant){
		final String tenantId = TenantUtil.getTenantId(Security.SCHEMA, tenant);
		manager.setProperty(Naming.Management.MANAGEMENT_TENANT, tenantId);
		final Query query = manager.createNativeQuery(NativeQueries.GET_CURRENT_ROLES);
		final Stream<?> stream = query.setParameter(1, name).getResultStream();
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
