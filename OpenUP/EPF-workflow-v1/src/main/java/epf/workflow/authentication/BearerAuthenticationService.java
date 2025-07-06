package epf.workflow.authentication;

import epf.workflow.authentication.schema.BearerAuthentication;

public interface BearerAuthenticationService {

	void authenticate(final BearerAuthentication bearerAuthentication) throws Exception;
}
