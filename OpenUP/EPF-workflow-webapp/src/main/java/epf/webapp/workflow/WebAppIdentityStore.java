package epf.webapp.workflow;

import java.util.Set;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import epf.webapp.internal.TokenCredential;
import epf.webapp.internal.TokenIdentityStore;

@ApplicationScoped
public class WebAppIdentityStore implements IdentityStore {

	@Inject
	private transient TokenIdentityStore identityStore;
	
	public CredentialValidationResult validate(final TokenCredential credential) throws Exception {
		return identityStore.validate(credential);
	}
	
	@Override
	public Set<String> getCallerGroups(final CredentialValidationResult validationResult) {
		return identityStore.getCallerGroups(validationResult);
	}
}
