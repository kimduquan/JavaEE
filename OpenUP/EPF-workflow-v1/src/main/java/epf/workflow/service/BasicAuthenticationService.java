package epf.workflow.service;

import epf.workflow.schema.BasicAuthentication;

public interface BasicAuthenticationService {

	void authenticate(final BasicAuthentication basicAuthentication) throws Exception;
}
