package epf.security.internal;

import java.util.Set;
import java.util.concurrent.CompletionStage;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import epf.security.util.Credential;

/**
 * @author PC
 *
 */
public interface IdentityStore {
	
	/**
	 * 
	 */
	String SECURITY_UNIT_NAME = "EPF-security";
	
	/**
	 * 
	 */
	String SECURITY_MANAGEMENT_UNIT_NAME = "EPF-security-management";

	/**
	 * @param credential
	 * @return
	 */
	CompletionStage<CredentialValidationResult> validate(final Credential credential) throws Exception;
	
	/**
	 * @param callerPrincipal
	 * @return
	 */
	CompletionStage<Set<String>> getCallerGroups(final CallerPrincipal callerPrincipal);
	
	/**
	 * @param credential
	 * @return
	 * @throws Exception
	 */
	CompletionStage<Void> putCredential(final Credential credential) throws Exception;
	
	/**
	 * @param credential
	 * @return
	 * @throws Exception
	 */
	CompletionStage<Void> setCredential(final Credential credential) throws Exception;
	
	/**
	 * @param credential
	 * @return
	 * @throws Exception
	 */
	CompletionStage<Boolean> isCaller(final Credential credential) throws Exception;
	
	/**
	 * @param credential
	 * @return
	 * @throws Exception
	 */
	CompletionStage<CallerPrincipal> authenticate(final Credential credential) throws Exception;
	
	/**
	 * @param callerPrincipal
	 * @param group
	 * @return
	 * @throws Exception
	 */
	CompletionStage<Void> setCallerGroup(final CallerPrincipal callerPrincipal, final String group) throws Exception;
}
