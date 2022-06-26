package epf.security.management.internal;

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
import javax.security.enterprise.CallerPrincipal;
import javax.transaction.Transactional;
import org.eclipse.microprofile.context.ManagedExecutor;

import epf.security.internal.IdentityStore;
import epf.security.internal.JPAPrincipal;
import epf.security.schema.Principal;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class ManagementPrincipalStore {
	
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
		factory = Persistence.createEntityManagerFactory(IdentityStore.SECURITY_UNIT_NAME);
	}

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

	@Transactional
	public CompletionStage<Void> putCaller(final CallerPrincipal callerPrincipal) throws Exception {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		EntityManager entityManager = factory.createEntityManager();
		final JPAPrincipal principal = (JPAPrincipal) callerPrincipal;
		if(principal.getTenant().isPresent()) {
			entityManager = persistence.createManager(principal.getTenant().get());
		}
		if(!entityManager.isJoinedToTransaction()) {
			entityManager.joinTransaction();
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
