package epf.workflow.authentication.spi;

import epf.workflow.authentication.schema.BearerAuthentication;

public interface BearerAuthenticationService {

	void authenticate(final BearerAuthentication bearerAuthentication) throws Exception;
}
