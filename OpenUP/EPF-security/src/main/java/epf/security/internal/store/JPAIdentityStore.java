package epf.security.internal.store;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
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
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.naming.Naming;
import epf.security.internal.JPAPrincipal;
import epf.security.internal.TenantPersistence;
import epf.security.internal.sql.NativeQueries;
import epf.security.schema.Security;
import epf.security.util.Credential;
import epf.security.util.CredentialComparator;
import epf.security.util.JdbcUtil;
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
	transient ManagedExecutor executor;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Persistence.JDBC.JDBC_URL)
	transient String jdbcUrl;
	
	/**
	 * 
	 */
	@PersistenceContext(unitName = Naming.Security.Internal.SECURITY_UNIT_NAME)
	transient EntityManager manager;
	
	/**
	 * 
	 */
	@Inject
	transient JPAPrincipalStore principalStore;
	
	/**
	 *
	 */
	@Inject
	transient TenantPersistence persistence;
	
	/**
	 * @param credential
	 * @param oldCredential
	 * @return
	 */
	protected CompletionStage<CredentialValidationResult> validate(final Credential credential, final Credential oldCredential){
		CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;
		final CredentialComparator comparator = new CredentialComparator();
		if(comparator.compare(credential, oldCredential) == 0) {
			final Optional<JPAPrincipal> principal = principalStore.getPrincipal(credential.getCaller(), credential.getTenant());
			if(principal.isPresent()) {
				result = new CredentialValidationResult(principal.get());
			}
		}
		return CompletableFuture.completedStage(result);
	}

	public CompletionStage<CredentialValidationResult> validate(final Credential credential) throws Exception {
		Objects.requireNonNull(credential, "Credential");
		Objects.requireNonNull(credential.getCaller(), "Credential.caller");
		Objects.requireNonNull(credential.getPassword(), "Credential.password");
		final Optional<Credential> oldCredential = principalStore.getCredential(credential.getCaller(), credential.getTenant());
		if (oldCredential.isPresent()) {
			return validate(credential, oldCredential.get());
		}
		return authenticate(credential).thenApply(principal -> {
			return principal != null ? new CredentialValidationResult(principal) : CredentialValidationResult.INVALID_RESULT;
		});
	}

	public CompletionStage<Set<String>> getCallerGroups(final CallerPrincipal callerPrincipal) {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		EntityManager entityManager = manager;
		final JPAPrincipal principal = (JPAPrincipal) callerPrincipal;
		if(principal.getTenant().isPresent()) {
			entityManager = persistence.createManager(Naming.Security.Internal.SECURITY_UNIT_NAME, principal.getTenant().get());
		}
		final Query query = entityManager.createNativeQuery(NativeQueries.GET_CURRENT_ROLES);
		final Stream<?> stream = query.setParameter(1, callerPrincipal.getName()).getResultStream();
		return executor.supplyAsync(() -> {
			final Set<String> groups = stream.map(role -> {
				return StringUtil.toPascalSnakeCase(role.toString().split("_"));
				}).collect(Collectors.toSet());
			return groups;
		});
	}

	public CompletionStage<CallerPrincipal> authenticate(final Credential credential) throws Exception {
		final Map<String, Object> props = new ConcurrentHashMap<>();
        props.put(Naming.Persistence.JDBC.JDBC_USER, credential.getCaller());
        props.put(Naming.Persistence.JDBC.JDBC_PASSWORD, String.valueOf(credential.getPassword().getValue()));
        credential.getTenant().ifPresent(tenant -> {
        	final String jdbcUrl = JdbcUtil.formatJdbcUrl(Naming.Security.Internal.JDBC_URL_FORMAT, Security.SCHEMA, tenant, "-");
        	props.put(Naming.Persistence.JDBC.JDBC_URL, jdbcUrl);
        });
        return executor.supplyAsync(() -> Persistence.createEntityManagerFactory(Naming.Security.Internal.SECURITY_UNIT_NAME, props))
        		.thenApply(factory -> {
        			try {
        				final EntityManager manager = factory.createEntityManager();
        				return principalStore.putPrincipaḷ̣̣̣̣(credential, factory, manager);
		        	}
		        	catch(Exception ex) {
		        		factory.close();
		        		return null;
		        	}
        		});
	}
}
