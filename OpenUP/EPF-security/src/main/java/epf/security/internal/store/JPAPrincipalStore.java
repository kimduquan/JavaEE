package epf.security.internal.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Password;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.naming.Naming;
import epf.security.internal.JPAPrincipal;
import epf.security.internal.NativeQueries;
import epf.security.internal.TenantPersistence;
import epf.security.schema.Principal;
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
	transient EntityManagerFactory factory;
	
	/**
	 * 
	 */
	@Inject
	transient ManagedExecutor executor;
	
	/**
	 *
	 */
	@Inject
	transient TenantPersistence persistence;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		factory = Persistence.createEntityManagerFactory(Naming.Security.Internal.SECURITY_UNIT_NAME);
	}

	/**
	 * @param credential
	 * @param password
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public CompletionStage<Void> setCallerPassword(final Credential credential, final Password password) throws Exception{
		Objects.requireNonNull(credential, "Credential");
		final EntityManager manager = factory.createEntityManager();
		if(!manager.isJoinedToTransaction()) {
			manager.joinTransaction();
		}
		final Query query = manager.createNativeQuery(String.format(NativeQueries.SET_PASSWORD, credential.getCaller()));
		query.setParameter(1, new String(password.getValue()));
		query.executeUpdate();
		manager.close();
		return executor.completedStage(null);
	}

	/**
	 * @param credential
	 * @return
	 */
	public CompletionStage<Map<String, Object>> getCallerClaims(final Credential credential) {
		Objects.requireNonNull(credential, "Credential");
		final EntityManager manager = factory.createEntityManager();
		final Principal principal = manager.find(Principal.class, credential.getCaller());
		final Map<String, Object> claims = new HashMap<>();
		if(principal.getClaims() != null) {
			claims.putAll(principal.getClaims());
		}
		manager.close();
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
		final EntityManager manager = principal.getFactory().createEntityManager();
		if(!manager.isJoinedToTransaction()) {
			manager.joinTransaction();
		}
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
		manager.close();
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
		EntityManager entityManager = factory.createEntityManager();
		final JPAPrincipal principal = (JPAPrincipal) callerPrincipal;
		if(principal.getTenant().isPresent()) {
			entityManager = persistence.createManager(Naming.Security.Internal.SECURITY_UNIT_NAME, principal.getTenant().get());
		}
		if(!entityManager.isJoinedToTransaction()) {
			entityManager.joinTransaction();
		}
		if(entityManager.find(Principal.class, callerPrincipal.getName()) != null) {
			entityManager.close();
			return executor.failedStage(new BadRequestException());
		}
		final Principal p = new Principal();
		p.setName(callerPrincipal.getName());
		p.setClaims(new HashMap<>());
		entityManager.persist(p);
		entityManager.flush();
		entityManager.close();
		return executor.completedStage(null);
	}
}
