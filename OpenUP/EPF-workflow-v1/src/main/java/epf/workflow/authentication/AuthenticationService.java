package epf.workflow.authentication;

import epf.workflow.authentication.schema.Authentication;

public interface AuthenticationService {

	void authenticate(final Authentication authentication) throws Exception;
}
