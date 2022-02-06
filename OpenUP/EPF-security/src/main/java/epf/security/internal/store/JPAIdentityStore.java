package epf.security.internal.store;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.security.internal.sql.NativeQueries;
import epf.security.util.IdentityStore;
import epf.security.util.JPAPrincipal;
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

	@Override
	public CompletionStage<CredentialValidationResult> validate(final UsernamePasswordCredential credential) {
		Objects.requireNonNull(credential, "UsernamePasswordCredential");
		Objects.requireNonNull(credential.getCaller(), "UsernamePasswordCredential.caller");
		Objects.requireNonNull(credential.getPassword(), "UsernamePasswordCredential.password");
		final Map<String, Object> props = new ConcurrentHashMap<>();
        props.put(JDBC_USER, credential.getCaller());
        props.put(JDBC_PASSWORD, credential.getPasswordAsString());
        return executor.supplyAsync(() -> Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, props))
        		.thenApply(factory -> {
        			try {
        				final EntityManager manager = factory.createEntityManager();
        				return new JPAPrincipal(credential.getCaller(), factory, manager);
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
		return executor.supplyAsync(() -> (JPAPrincipal) callerPrincipal)
				.thenApply(principal -> principal.getDefaultManager().createNativeQuery(NativeQueries.GET_CURRENT_ROLES, String.class))
				.thenApply(query -> query.getResultStream().map(role -> {
					return StringUtil.toPascalSnakeCase(role.split("_"));
				}).collect(Collectors.toSet()));
	}
}
