package epf.workflow.authentication;

import epf.workflow.schema.BearerAuthentication;

public interface BearerAuthenticationService {

	void authenticate(final BearerAuthentication bearerAuthentication) throws Exception;
}
