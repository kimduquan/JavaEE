package epf.security.internal.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Password;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.naming.Naming;
import epf.schema.utility.TenantUtil;
import epf.security.internal.JPAPrincipal;
import epf.security.internal.NativeQueries;
import epf.security.schema.Principal;
import epf.security.schema.Security;
import epf.security.util.Credential;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class JPAPrincipalStore {
	
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
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public CompletionStage<Void> setCallerPassword(final Credential credential, final Password password) throws Exception{
		Objects.requireNonNull(credential, "Credential");
		final String tenant = TenantUtil.getTenantId(Security.SCHEMA, credential.getTenant().orElse(null));
		manager.setProperty(Naming.Management.MANAGEMENT_TENANT, tenant);
		final Query query = manager.createNativeQuery(String.format(NativeQueries.SET_PASSWORD, credential.getCaller()));
		query.setParameter(1, new String(password.getValue()));
		query.executeUpdate();
		manager.flush();
		return executor.completedStage(null);
	}

	/**
	 * @param credential
	 * @return
	 */
	public CompletionStage<Map<String, Object>> getCallerClaims(final Credential credential) {
		Objects.requireNonNull(credential, "Credential");
		final String tenant = TenantUtil.getTenantId(Security.SCHEMA, credential.getTenant().orElse(null));
		manager.setProperty(Naming.Management.MANAGEMENT_TENANT, tenant);
		final Principal principal = manager.find(Principal.class, credential.getCaller());
		final Map<String, Object> claims = new HashMap<>();
		if(principal != null && principal.getClaims() != null) {
			claims.putAll(principal.getClaims());
		}
		return executor.completedStage(claims);
	}

	/**
	 * @param callerPrincipal
	 * @param claims
	 * @return
	 */
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

	/**
	 * @param callerPrincipal
	 * @return
	 * @throws Exception
	 */
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
}
