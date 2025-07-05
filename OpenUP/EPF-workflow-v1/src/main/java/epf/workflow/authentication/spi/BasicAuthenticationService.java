package epf.workflow.authentication.spi;

import epf.workflow.authentication.schema.BasicAuthentication;

public interface BasicAuthenticationService {

	void authenticate(final BasicAuthentication basicAuthentication) throws Exception;
}
