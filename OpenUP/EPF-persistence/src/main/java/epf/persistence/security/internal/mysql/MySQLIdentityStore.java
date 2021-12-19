package epf.persistence.security.internal.mysql;

import java.util.Set;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import epf.persistence.security.internal.IdentityStore;

public class MySQLIdentityStore implements IdentityStore {

	@Override
	public CredentialValidationResult validate(final UsernamePasswordCredential credential) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getCallerGroups(final CredentialValidationResult validationResult) {
		// TODO Auto-generated method stub
		return null;
	}

}
