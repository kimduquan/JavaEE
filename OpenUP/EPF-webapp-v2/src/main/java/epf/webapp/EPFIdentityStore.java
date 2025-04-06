package epf.webapp;

import static jakarta.security.enterprise.identitystore.IdentityStore.ValidationType.PROVIDE_GROUPS;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.identitystore.openid.OpenIdContext;

@ApplicationScoped
public class EPFIdentityStore implements IdentityStore {
	
	@Inject
	private OpenIdContext context;
	
	@Override
	public Set<ValidationType> validationTypes() {
        return EnumSet.of(PROVIDE_GROUPS);
    }
	
	@Override
	public Set<String> getCallerGroups(final CredentialValidationResult validationResult) {
		final Map<String, Object> claims = context.getAccessToken().getClaims();
		return validationResult.getCallerGroups();
    }
}
