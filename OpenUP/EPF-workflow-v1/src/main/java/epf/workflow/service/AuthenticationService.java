package epf.workflow.service;

import epf.workflow.schema.Authentication;

public interface AuthenticationService {

	void authenticate(final Authentication authentication) throws Exception;
}
