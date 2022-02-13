package epf.security.internal.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Password;
import javax.transaction.Transactional;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.naming.Naming;
import epf.security.internal.sql.NativeQueries;
import epf.security.schema.Principal;
import epf.security.util.Credential;
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
	public CompletionStage<Void> setCallerPassword(final CallerPrincipal callerPrincipal, final Password password) {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		final JPAPrincipal principal = (JPAPrincipal)callerPrincipal;
		return executor.supplyAsync(() -> principal.getFactory().createEntityManager())
				.thenApply(manager -> manager.createQuery(NativeQueries.SET_PASSWORD))
				.thenApply(query -> query.setParameter(1, new String(password.getValue())))
				.thenApply(query -> query.executeUpdate())
				.thenAccept(i -> {
					principal.close();
					final Map<String, Object> props = principal.getFactory().getProperties();
					props.put(Naming.Persistence.JDBC.JDBC_PASSWORD, String.valueOf(password.getValue()));
					final EntityManagerFactory factory = Persistence.createEntityManagerFactory(IdentityStore.PERSISTENCE_UNIT, props);
					final EntityManager manager = factory.createEntityManager();
					principal.reset(factory, manager);
					MapUtil.get(principalCredentials, principal.getName()).ifPresent(credential -> {
						credential.setPassword(password);
					});
				});
	}

	@Override
	public CompletionStage<Map<String, Object>> getCallerClaims(final CallerPrincipal callerPrincipal) {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		final JPAPrincipal principal = (JPAPrincipal) callerPrincipal;
		return executor.supplyAsync(() -> principal.getFactory().createEntityManager())
		.thenApply(manager -> manager.find(Principal.class, principal.getName()))
		.thenApply(p -> p.getClaims() != null ? new HashMap<>(p.getClaims()) : new HashMap<>());
	}
	
	/**
	 * @param credential
	 * @param factory
	 * @param manager
	 * @return
	 */
	protected JPAPrincipal putPrincipaḷ̣̣̣̣(final Credential credential, final EntityManagerFactory factory, final EntityManager manager) {
		final JPAPrincipal principal = new JPAPrincipal(credential.getTernant(), credential.getCaller(), factory, manager);
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
}
