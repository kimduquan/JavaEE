package epf.workflow.authentication;

import epf.workflow.authentication.schema.DigestAuthentication;

public interface DigestAuthenticationService {

	void authenticate(final DigestAuthentication digestAuthentication) throws Exception;
}
