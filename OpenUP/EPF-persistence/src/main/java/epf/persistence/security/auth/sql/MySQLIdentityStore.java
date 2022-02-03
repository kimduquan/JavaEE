package epf.persistence.security.auth.sql;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import org.eclipse.microprofile.opentracing.Traced;
import epf.persistence.internal.rx.RxPersistence;
import epf.persistence.security.auth.EPFPrincipal;
import epf.persistence.security.auth.IdentityStore;
import epf.persistence.util.EntityManagerFactory;
import epf.persistence.util.Query;
import epf.util.concurrent.Stage;
import epf.util.logging.LogManager;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Traced
public class MySQLIdentityStore implements IdentityStore {
	
	/**
	 * 
	 */
	private static transient final Logger LOGGER = LogManager.getLogger(MySQLIdentityStore.class.getName());
	
	/**
	 * 
	 */
	private transient final Map<String, EPFPrincipal> principals = new ConcurrentHashMap<>();

	@Override
	public CompletionStage<CredentialValidationResult> validate(final UsernamePasswordCredential credential) {
		Objects.requireNonNull(credential, "UsernamePasswordCredential");
		final Map<String, Object> props = new ConcurrentHashMap<>();
        props.put(JDBC_USER, credential.getCaller());
        props.put(JDBC_PASSWORD, credential.getPasswordAsString());
        final EntityManagerFactory factory = RxPersistence.createEntityManagerFactory(PERSISTENCE_UNIT, props);
        try {
        	return Stage.stage(factory.createEntityManager())
        	.apply(manager -> new EPFPrincipal(credential.getCaller(), factory, manager))
        	.apply(principal -> {
        		principals.put(principal.getName(), principal);
        		return principal;
        		}
        	)
        	.apply(CredentialValidationResult::new)
        	.complete();
        }
        catch(Exception ex) {
        	try {
				factory.close();
			} 
        	catch (Exception e) {
        		LOGGER.log(Level.SEVERE, "validate", e);
			}
        	throw ex;
        }
	}

	@Override
	public CompletionStage<Set<String>> getCallerGroups(final CallerPrincipal callerPrincipal) {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		final EPFPrincipal principal = (EPFPrincipal) callerPrincipal;
		return Stage.stage(principal.getFactory().createEntityManager())
				.compose(manager -> {
					final Query<String> query = manager.createNativeQuery(String.format(NativeQueries.GET_CURRENT_ROLES, principal.getName()));
					return query.getSingleResult();
					}
				)
				.apply(roles -> RoleUtil.parse(roles).map(Role::getName).collect(Collectors.toSet()))
				.complete();
	}
}
