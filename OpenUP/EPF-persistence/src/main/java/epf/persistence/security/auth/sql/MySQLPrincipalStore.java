package epf.persistence.security.auth.sql;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Password;
import javax.transaction.Transactional;
import epf.persistence.security.auth.EPFPrincipal;
import epf.persistence.security.auth.PrincipalStore;
import epf.security.schema.Principal;
import epf.util.concurrent.Stage;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class MySQLPrincipalStore implements PrincipalStore {

	@Override
	@Transactional
	public CompletionStage<Void> setCallerPassword(final CallerPrincipal callerPrincipal, final Password password) {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		final EPFPrincipal principal = (EPFPrincipal) callerPrincipal;
		final String query = String.format(NativeQueries.SET_PASSWORD, principal.getName());
		return Stage.stage(principal.getFactory().createEntityManager())
		.apply(manager -> manager.createNativeQuery(query).setParameter(1, new String(password.getValue())))
		.compose(q -> q.executeUpdate())
		.accept()
		.complete();
	}

	@Override
	public CompletionStage<Map<String, Object>> getCallerClaims(final CallerPrincipal callerPrincipal) {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		final EPFPrincipal principal = (EPFPrincipal) callerPrincipal;
		return Stage.stage(principal.getFactory().createEntityManager())
		.compose(manager -> manager.find(Principal.class, callerPrincipal.getName()))
		.apply(p -> {
			final Map<String, Object> claims = new HashMap<>(p.getClaims());
			return claims;
		})
		.complete();
	}
}
