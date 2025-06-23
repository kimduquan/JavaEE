package epf.workflow.spi;

import epf.workflow.schema.DigestAuthentication;

public interface DigestAuthenticationService {

	void authenticate(final DigestAuthentication digestAuthentication) throws Exception;
}
