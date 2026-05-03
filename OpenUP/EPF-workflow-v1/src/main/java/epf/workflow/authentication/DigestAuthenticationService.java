package epf.workflow.authentication;

import epf.workflow.schema.DigestAuthentication;

public interface DigestAuthenticationService {

	void authenticate(final DigestAuthentication digestAuthentication) throws Exception;
}
