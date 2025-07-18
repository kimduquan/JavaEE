package epf.security.internal.store;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.naming.Naming;
import epf.security.internal.JPAPrincipal;
import epf.security.internal.NativeQueries;
import epf.security.schema.Security;
import epf.security.util.Credential;
import epf.security.util.TenantUtil;
import epf.util.StringUtil;

@ApplicationScoped
@Readiness
public class JPAIdentityStore implements HealthCheck {
	
	@Inject
	private transient ManagedExecutor executor;
	
	public CompletionStage<Set<String>> getCallerGroups(final EntityManager manager, final Credential credential) {
		Objects.requireNonNull(credential, "Credential");
		return getCallerGroups(manager, credential.getTenant().orElse(null));
	}

	public CompletionStage<Set<String>> getCallerGroups(final JsonWebToken jwt) {
		Objects.requireNonNull(jwt, "JsonWebToken");
		return executor.completedStage(jwt.getGroups());
	}
	
	private CompletionStage<Set<String>> getCallerGroups(final EntityManager manager, final String tenant){
		final String tenantId = TenantUtil.getTenantId(Security.SCHEMA, tenant);
		manager.setProperty(Naming.Management.MANAGEMENT_TENANT, tenantId);
		final Query query = manager.createNativeQuery(NativeQueries.GET_CURRENT_ROLES);
		final Stream<?> stream = query.getResultStream();
		final Set<String> groups = stream.map(role -> StringUtil.toPascalSnakeCase(role.toString().split("_"))).collect(Collectors.toSet());
		return executor.completedStage(groups);
	}

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

	@Override
	public HealthCheckResponse call() {
		if(executor.isShutdown() || executor.isTerminated()) {
			return HealthCheckResponse.down("epf-security-identity-store");
		}
		return HealthCheckResponse.up("epf-security-identity-store");
	}
}
