package epf.workflow.authentication;

import epf.workflow.schema.BasicAuthentication;

public interface BasicAuthenticationService {

	void authenticate(final BasicAuthentication basicAuthentication) throws Exception;
}
