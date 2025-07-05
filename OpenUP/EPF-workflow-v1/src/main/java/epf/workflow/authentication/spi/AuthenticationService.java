package epf.workflow.authentication.spi;

import epf.workflow.authentication.schema.Authentication;

public interface AuthenticationService {

	void authenticate(final Authentication authentication) throws Exception;
}
