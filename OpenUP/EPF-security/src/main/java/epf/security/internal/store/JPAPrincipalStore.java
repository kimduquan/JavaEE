package epf.security.internal.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
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
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.naming.Naming;
import epf.security.internal.IdentityStore;
import epf.security.internal.JPAPrincipal;
import epf.security.internal.PrincipalStore;
import epf.security.internal.sql.NativeQueries;
import epf.security.schema.Principal;
import epf.security.util.Credential;
import epf.security.util.CredentialUtil;
import epf.util.MapUtil;
import epf.util.StringUtil;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class JPAPrincipalStore implements PrincipalStore {
	
	/**
	 * 
	 */
	private transient final Map<String, Credential> credentials = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Map<String, JPAPrincipal> principals = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private transient final Map<String, Credential> principalCredentials = new ConcurrentHashMap<>();
	
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

	@Override
	@Transactional
	public CompletionStage<Void> setCallerPassword(final CallerPrincipal callerPrincipal, final Password password) throws Exception{
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		final JPAPrincipal principal = (JPAPrincipal)callerPrincipal;
		final EntityManager manager = principal.getFactory().createEntityManager();
		manager.joinTransaction();
		final Query query = manager.createNativeQuery(NativeQueries.SET_PASSWORD);
		query.setParameter(1, new String(password.getValue()));
		query.executeUpdate();
		final Optional<Credential> oldCredential = getPrincipalCredential(principal.getName(), principal.getTenant());
		final String passwordHash = CredentialUtil.encryptPassword(oldCredential.get().getCaller(), new String(password.getValue()));
		return executor.supplyAsync(() -> {
			final Map<String, Object> props = new HashMap<>(principal.getFactory().getProperties());
			principal.close();
			props.put(Naming.Persistence.JDBC.JDBC_PASSWORD, passwordHash);
			final EntityManagerFactory factory = Persistence.createEntityManagerFactory(IdentityStore.SECURITY_UNIT_NAME, props);
			final EntityManager newManager = factory.createEntityManager();
			principal.reset(factory, newManager);
			oldCredential.get().setPassword(new Password(passwordHash));
			return null;
		});
	}

	@Override
	public CompletionStage<Map<String, Object>> getCallerClaims(final CallerPrincipal callerPrincipal) {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		final JPAPrincipal principal = (JPAPrincipal) callerPrincipal;
		return executor.supplyAsync(() -> principal.getFactory().createEntityManager())
		.thenApply(manager -> manager.find(Principal.class, principal.getName()))
		.thenApply(p -> {
			final Map<String, Object> claims = new HashMap<>();
			if(p.getClaims() != null) {
				claims.putAll(p.getClaims());
			}
			return claims;
		});
	}
	
	/**
	 * @param credential
	 * @param factory
	 * @param manager
	 * @return
	 */
	protected JPAPrincipal putPrincipaḷ̣̣̣̣(final Credential credential, final EntityManagerFactory factory, final EntityManager manager) {
		final JPAPrincipal principal = new JPAPrincipal(credential.getTenant(), credential.getCaller(), factory, manager);
		final String key = getKey(credential.getCaller(), credential.getTenant());
		principals.put(key, principal);
		credentials.put(key, credential);
		final String principalKey = getKey(principal.getName(), principal.getTenant());
		principalCredentials.put(principalKey, credential);
		return principal;
	}
	
	/**
	 * @param caller
	 * @param tenant
	 * @return
	 */
	protected Optional<JPAPrincipal> getPrincipal(final String caller, final Optional<String> tenant){
		return MapUtil.get(principals, getKey(caller, tenant));
	}
	
	/**
	 * @param caller
	 * @param tenant
	 * @return
	 */
	protected Optional<Credential> getCredential(final String caller, final Optional<String> tenant){
		return MapUtil.get(credentials, getKey(caller, tenant));
	}
	
	/**
	 * @param caller
	 * @param tenant
	 * @return
	 */
	protected Optional<Credential> getPrincipalCredential(final String name, final Optional<String> tenant){
		return MapUtil.get(principalCredentials, getKey(name, tenant));
	}
	
	private String getKey(final String caller, final Optional<String> tenant) {
		return StringUtil.join(tenant.orElse(""), caller);
	}

	@Override
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

	@Override
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
