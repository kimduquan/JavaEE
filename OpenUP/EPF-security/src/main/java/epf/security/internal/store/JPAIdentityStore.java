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
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.naming.Naming;
import epf.security.internal.sql.NativeQueries;
import epf.security.util.CredentialUtil;
import epf.security.util.IdentityStore;
import epf.security.util.JPAPrincipal;
import epf.security.util.JdbcUtil;
import epf.util.StringUtil;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class JPAIdentityStore implements IdentityStore {
	
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
	@PersistenceContext(unitName = "EPF-Schema")
	transient EntityManager manager;

	@Override
	public CompletionStage<CredentialValidationResult> validate(final UsernamePasswordCredential credential) {
		Objects.requireNonNull(credential, "UsernamePasswordCredential");
		Objects.requireNonNull(credential.getCaller(), "UsernamePasswordCredential.caller");
		Objects.requireNonNull(credential.getPassword(), "UsernamePasswordCredential.password");
		final StringBuilder ternant = new StringBuilder();
		final String user = CredentialUtil.getUsername(credential, ternant);
		final Map<String, Object> props = new ConcurrentHashMap<>();
        props.put(Naming.Persistence.JDBC.JDBC_USER, user);
        props.put(Naming.Persistence.JDBC.JDBC_PASSWORD, credential.getPasswordAsString());
        if(!"".equals(ternant.toString())) {
        	final String ternantUrl = JdbcUtil.formatTernantUrl(jdbcUrl, ternant.toString());
        	props.put(Naming.Persistence.JDBC.JDBC_URL, ternantUrl);
        }
        return executor.supplyAsync(() -> Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, props))
        		.thenApply(factory -> {
        			try {
        				final EntityManager manager = factory.createEntityManager();
        				return new JPAPrincipal(user, ternant.toString(), factory, manager);
		        	}
		        	catch(Exception ex) {
		        		factory.close();
		        		throw ex;
		        	}
        		})
        		.thenApply(CredentialValidationResult::new);
	}

	@Override
	public CompletionStage<Set<String>> getCallerGroups(final CallerPrincipal callerPrincipal) {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		final Query query = manager.createNativeQuery(NativeQueries.GET_CURRENT_ROLES);
		final Stream<?> stream = query.setParameter(1, callerPrincipal.getName()).getResultStream();
		return executor.supplyAsync(() -> {
			final Set<String> groups = stream.map(role -> {
				return StringUtil.toPascalSnakeCase(role.toString().split("_"));
				}).collect(Collectors.toSet());
			return groups;
		});
	}
}
