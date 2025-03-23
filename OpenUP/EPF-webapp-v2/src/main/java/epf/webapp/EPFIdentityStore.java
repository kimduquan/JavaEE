package epf.webapp;

import java.util.Set;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

@ApplicationScoped
public class EPFIdentityStore implements IdentityStore {

	@Override
	public Set<String> getCallerGroups(final CredentialValidationResult validationResult) {
        return Set.of("Any_Role");
    }
}
