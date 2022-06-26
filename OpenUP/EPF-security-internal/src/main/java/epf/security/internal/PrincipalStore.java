package epf.security.internal;

import java.util.Map;
import java.util.concurrent.CompletionStage;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Password;

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
	 * @return
	 */
	CompletionStage<Void> setCallerClaims(final CallerPrincipal callerPrincipal, final Map<String, Object> claims);
	
	/**
	 * @param callerPrincipal
	 * @param password
	 */
	CompletionStage<Void> setCallerPassword(final CallerPrincipal callerPrincipal, final Password password) throws Exception;
	
	/**
	 * @param callerPrincipal
	 * @return
	 * @throws Exception
	 */
	CompletionStage<Void> putCaller(final CallerPrincipal callerPrincipal) throws Exception;
}
