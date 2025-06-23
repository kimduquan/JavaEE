package epf.workflow.spi;

import epf.workflow.schema.BearerAuthentication;

public interface BearerAuthenticationService {

	void authenticate(final BearerAuthentication bearerAuthentication) throws Exception;
}
