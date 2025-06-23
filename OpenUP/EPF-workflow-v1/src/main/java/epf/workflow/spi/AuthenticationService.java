package epf.workflow.spi;

import epf.workflow.schema.Authentication;

public interface AuthenticationService {

	void authenticate(final Authentication authentication) throws Exception;
}
