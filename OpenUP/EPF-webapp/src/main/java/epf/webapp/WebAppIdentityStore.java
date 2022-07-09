package epf.webapp;

import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import epf.webapp.internal.TokenCredential;
import epf.webapp.internal.TokenIdentityStore;

/**
 * @author PC
 *
 */
@ApplicationScoped
public class WebAppIdentityStore implements IdentityStore {

	/**
	 * 
	 */
	@Inject
	private transient TokenIdentityStore identityStore;
	
	/**
	 * @param credential
	 * @return
	 * @throws Exception
	 */
	public CredentialValidationResult validate(final TokenCredential credential) throws Exception {
		return identityStore.validate(credential);
	}
	
	@Override
	public Set<String> getCallerGroups(final CredentialValidationResult validationResult) {
		return identityStore.getCallerGroups(validationResult);
	}
}
