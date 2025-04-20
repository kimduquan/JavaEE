package epf.security.internal.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.security.enterprise.CallerPrincipal;
import jakarta.security.enterprise.credential.Password;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.naming.Naming;
import epf.security.internal.JPAPrincipal;
import epf.security.internal.NativeQueries;
import epf.security.schema.Principal;
import epf.security.schema.Security;
import epf.security.util.Credential;
import epf.security.util.TenantUtil;

@ApplicationScoped
@Readiness
public class JPAPrincipalStore implements HealthCheck {
	
	@Inject
	private transient ManagedExecutor executor;
	
	@PersistenceContext(unitName = Naming.Security.Internal.SECURITY_PRINCIPAL_UNIT_NAME)
	private transient EntityManager manager;

	@Transactional
	public CompletionStage<Void> setCallerPassword(final JsonWebToken jwt, final Password password) throws Exception{
		Objects.requireNonNull(jwt, "JsonWebToken");
		return setCallerPassword(jwt.getName(), jwt.getClaim(Naming.Management.TENANT), password);
	}
	
	private CompletionStage<Void> setCallerPassword(final String name, final String tenant, final Password password) throws Exception {
		final String tenantId = TenantUtil.getTenantId(Security.SCHEMA, tenant);
		manager.setProperty(Naming.Management.MANAGEMENT_TENANT, tenantId);
		final Query query = manager.createNativeQuery(String.format(NativeQueries.SET_PASSWORD, name));
		query.setParameter(1, new String(password.getValue()));
		query.executeUpdate();
		manager.flush();
		return executor.completedStage(null);
	}

	public CompletionStage<Map<String, Object>> getCallerClaims(final JsonWebToken jwt) {
		Objects.requireNonNull(jwt, "JsonWebToken");
		return getCallerClaims(jwt.getName(), jwt.getClaim(Naming.Management.TENANT));
	}
	
	public CompletionStage<Map<String, Object>> getCallerClaims(final Credential credential) {
		Objects.requireNonNull(credential, "Credential");
		return getCallerClaims(credential.getCaller(), credential.getTenant().orElse(null));
	}
	
	private CompletionStage<Map<String, Object>> getCallerClaims(final String name, final String tenant){
		final String tenantId = TenantUtil.getTenantId(Security.SCHEMA, tenant);
		manager.setProperty(Naming.Management.MANAGEMENT_TENANT, tenantId);
		final Principal principal = manager.find(Principal.class, name);
		final Map<String, Object> claims = new HashMap<>();
		if(principal != null && principal.getClaims() != null) {
			claims.putAll(principal.getClaims());
		}
		return executor.completedStage(claims);
	}

	@Transactional
	public CompletionStage<Void> setCallerClaims(final CallerPrincipal callerPrincipal, final Map<String, Object> claims) {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		final JPAPrincipal principal = (JPAPrincipal) callerPrincipal;
		final String tenant = TenantUtil.getTenantId(Security.SCHEMA, principal.getTenant().orElse(null));
		manager.setProperty(Naming.Management.MANAGEMENT_TENANT, tenant);
		final Principal p = manager.find(Principal.class, principal.getName());
		if(p != null) {
			if(p.getClaims() == null) {
				p.setClaims(new HashMap<>());
			}
			for(Entry<String, Object> claim : claims.entrySet()) {
				p.getClaims().put(claim.getKey(), String.valueOf(claim.getValue()));
			}
			manager.merge(p);
			manager.flush();
		}
		return executor.completedStage(null);
	}

	@Transactional
	public CompletionStage<Void> putCaller(final CallerPrincipal callerPrincipal) throws Exception {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		Objects.requireNonNull(callerPrincipal.getName(), "CallerPrincipal.name");
		final JPAPrincipal principal = (JPAPrincipal) callerPrincipal;
		final String tenant = TenantUtil.getTenantId(Security.SCHEMA, principal.getTenant().orElse(null));
		manager.setProperty(Naming.Management.MANAGEMENT_TENANT, tenant);
		if(manager.find(Principal.class, callerPrincipal.getName()) != null) {
			return executor.failedStage(new BadRequestException());
		}
		final Principal newPrincipal = new Principal();
		newPrincipal.setName(callerPrincipal.getName());
		newPrincipal.setClaims(new HashMap<>());
		manager.persist(newPrincipal);
		manager.flush();
		return executor.completedStage(null);
	}

	@Override
	public HealthCheckResponse call() {
		if(executor.isShutdown() || executor.isTerminated() || !manager.isOpen()) {
			return HealthCheckResponse.down("epf-security-principal-store");
		}
		return HealthCheckResponse.up("epf-security-principal-store");
	}
}
