package epf.security.util;

import java.util.Map;
import java.util.concurrent.CompletionStage;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Password;
import javax.transaction.Transactional;

/**
 * @author PC
 *
 */
public interface PrincipalStore {

	/**
	 * @param callerPrincipal
	 * @return
	 */
	CompletionStage<Map<String, Object>> getCallerClaims(final CallerPrincipal callerPrincipal);
	
	/**
	 * @param callerPrincipal
	 * @param password
	 */
	@Transactional
	CompletionStage<Void> setCallerPassword(final CallerPrincipal callerPrincipal, final Password password) throws Exception;
}
