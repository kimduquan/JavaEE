package epf.workflow.service;

import epf.workflow.schema.DigestAuthentication;

public interface DigestAuthenticationService {

	void authenticate(final DigestAuthentication digestAuthentication) throws Exception;
}
