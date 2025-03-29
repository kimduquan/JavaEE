package epf.webapp;

import static jakarta.security.enterprise.identitystore.IdentityStore.ValidationType.PROVIDE_GROUPS;
import java.util.EnumSet;
import java.util.Set;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

@ApplicationScoped
public class EPFIdentityStore implements IdentityStore {
	
	@Override
	public Set<ValidationType> validationTypes() {
        return EnumSet.of(PROVIDE_GROUPS);
    }
	
	@Override
	public Set<String> getCallerGroups(final CredentialValidationResult validationResult) {
		return validationResult.getCallerGroups();
    }
}
