package epf.security.internal.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
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
import epf.security.internal.sql.NativeQueries;
import epf.security.schema.Principal;
import epf.security.util.Credential;
import epf.security.util.CredentialUtil;
import epf.security.util.IdentityStore;
import epf.security.util.JPAPrincipal;
import epf.security.util.PrincipalStore;
import epf.util.MapUtil;

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
	@Inject
	transient ManagedExecutor executor;

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
		final Optional<Credential> oldCredential = MapUtil.get(principalCredentials, principal.getName());
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
		principals.put(credential.getCaller(), principal);
		credentials.put(credential.getCaller(), credential);
		principalCredentials.put(principal.getName(), credential);
		return principal;
	}
	
	/**
	 * @param caller
	 * @return
	 */
	protected Optional<JPAPrincipal> getPrincipal(final String caller){
		return MapUtil.get(principals, caller);
	}
	
	/**
	 * @param caller
	 * @return
	 */
	protected Optional<Credential> getCredential(final String caller){
		return MapUtil.get(credentials, caller);
	}

	@Override
	@Transactional
	public CompletionStage<Void> setCallerClaims(final CallerPrincipal callerPrincipal, final Map<String, Object> claims) {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		final JPAPrincipal principal = (JPAPrincipal) callerPrincipal;
		return executor.supplyAsync(() -> principal.getFactory().createEntityManager())
		.thenApply(manager -> {
			final Principal p = manager.find(Principal.class, principal.getName());
			if(p != null) {
				if(p.getClaims() == null) {
					p.setClaims(new HashMap<>());
				}
				for(Entry<String, Object> claim : claims.entrySet()) {
					p.getClaims().put(claim.getKey(), String.valueOf(claim.getValue()));
				}
				manager.merge(p);
			}
			return null;
		});
	}

	@Override
	@Transactional
	public CompletionStage<Void> putCaller(final CallerPrincipal callerPrincipal, final Map<String, Object> claims) throws Exception {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		final JPAPrincipal principal = (JPAPrincipal) callerPrincipal;
		return executor.supplyAsync(() -> principal.getFactory().createEntityManager())
				.thenApply(manager -> {
					final Principal p = new Principal();
					p.setName(callerPrincipal.getName());
					p.setClaims(new HashMap<>());
					for(Entry<String, Object> claim : claims.entrySet()) {
						p.getClaims().put(claim.getKey(), String.valueOf(claim.getValue()));
					}
					manager.persist(p);
					return null;
				});
	}
}
