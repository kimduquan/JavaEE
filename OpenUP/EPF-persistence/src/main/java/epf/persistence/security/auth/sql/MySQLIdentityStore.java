package epf.persistence.security.auth.sql;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.transaction.Transactional;
import org.eclipse.microprofile.opentracing.Traced;
import epf.persistence.security.auth.EPFPrincipal;
import epf.persistence.security.auth.IdentityStore;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Traced
public class MySQLIdentityStore implements IdentityStore {

	@Override
	public CredentialValidationResult validate(final UsernamePasswordCredential credential) {
		Objects.requireNonNull(credential, "UsernamePasswordCredential");
		final Map<String, Object> props = new ConcurrentHashMap<>();
        props.put(JDBC_USER, credential.getCaller());
        props.put(JDBC_PASSWORD, credential.getPasswordAsString());
        EntityManagerFactory factory = null;
        EntityManager manager = null;
        try {
        	factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT, props);
            manager = factory.createEntityManager();
            final EPFPrincipal principal = new EPFPrincipal(credential.getCaller(), manager, factory);
            return new CredentialValidationResult(principal);
        }
        catch (Exception ex) {
            if(manager != null){
                manager.close();
            }
            if(factory != null){
                factory.close();
            }
            throw ex;
        }
	}

	@Override
	public Set<String> getCallerGroups(final CallerPrincipal callerPrincipal) {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		Set<String> callerGroups = Set.of();
		if(callerPrincipal instanceof EPFPrincipal) {
			final EPFPrincipal principal = (EPFPrincipal) callerPrincipal;
			final Query query = principal.getManager().createNativeQuery(String.format(NativeQueries.GET_CURRENT_ROLES, principal.getName()));
			final String roles = (String)query.getSingleResult();
			return RoleUtil.parse(roles).stream().map(Role::getName).collect(Collectors.toSet());
		}
		return callerGroups;
	}

	@Override
	@Transactional
	public void setCallerPassword(final CallerPrincipal callerPrincipal, final Password password) {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		if(callerPrincipal instanceof EPFPrincipal) {
			final EPFPrincipal principal = (EPFPrincipal) callerPrincipal;
			final EntityManager manager = principal.getManager();
			if(!manager.isJoinedToTransaction()) {
				manager.joinTransaction();
			}
			final String query = String.format(NativeQueries.SET_PASSWORD, principal.getName());
			manager
			.createNativeQuery(query)
			.setParameter(1, new String(password.getValue()))
			.executeUpdate();
			manager.flush();
		}
	}
}
