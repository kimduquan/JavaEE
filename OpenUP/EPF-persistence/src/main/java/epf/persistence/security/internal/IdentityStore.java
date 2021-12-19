package epf.persistence.security.internal;

import java.util.Set;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;

/**
 * @author PC
 *
 */
public interface IdentityStore {

	/**
	 * @param credential
	 * @return
	 */
	CredentialValidationResult validate(final UsernamePasswordCredential credential);
	
	/**
	 * @param validationResult
	 * @return
	 */
	Set<String> getCallerGroups(final CredentialValidationResult validationResult);
}
