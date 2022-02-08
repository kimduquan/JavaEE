package epf.security.internal.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Password;
import javax.transaction.Transactional;
import org.eclipse.microprofile.context.ManagedExecutor;
import epf.security.internal.sql.NativeQueries;
import epf.security.schema.Principal;
import epf.security.util.JPAPrincipal;
import epf.security.util.PrincipalStore;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class JPAPrincipalStore implements PrincipalStore {
	
	/**
	 * 
	 */
	@Inject
	transient ManagedExecutor executor;

	@Override
	@Transactional
	public CompletionStage<Void> setCallerPassword(final CallerPrincipal callerPrincipal, final Password password) {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		return executor.supplyAsync(() -> (JPAPrincipal) callerPrincipal)
				.thenApply(principal -> principal.getDefaultManager().createQuery(NativeQueries.SET_PASSWORD))
				.thenApply(query -> query.setParameter(1, new String(password.getValue())))
				.thenApply(query -> query.executeUpdate())
				.thenAccept(i -> {});
	}

	@Override
	public CompletionStage<Map<String, Object>> getCallerClaims(final CallerPrincipal callerPrincipal) {
		Objects.requireNonNull(callerPrincipal, "CallerPrincipal");
		return executor.supplyAsync(() -> (JPAPrincipal) callerPrincipal)
		.thenApply(principal -> principal.getDefaultManager().find(Principal.class, principal.getName()))
		.thenApply(principal -> principal.getClaims() != null ? new HashMap<>(principal.getClaims()) : new HashMap<>());
	}
}
