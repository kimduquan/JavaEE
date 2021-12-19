package epf.persistence.security.internal;

import java.util.Set;

import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.transaction.Transactional;

/**
 * @author PC
 *
 */
public interface IdentityStore {
	
	/**
	 * 
	 */
	String PERSISTENCE_UNIT = "EPF";
	/**
	 * 
	 */
	String JDBC_USER = "javax.persistence.jdbc.user";
	/**
	 * 
	 */
	String JDBC_PASSWORD = "javax.persistence.jdbc.password";

	/**
	 * @param credential
	 * @return
	 */
	CredentialValidationResult validate(final UsernamePasswordCredential credential);
	
	/**
	 * @param callerPrincipal
	 * @return
	 */
	Set<String> getCallerGroups(final CallerPrincipal callerPrincipal);
	
	/**
	 * @param callerPrincipal
	 * @param password
	 */
	@Transactional
	void setPassword(final CallerPrincipal callerPrincipal, final char... password);
}
