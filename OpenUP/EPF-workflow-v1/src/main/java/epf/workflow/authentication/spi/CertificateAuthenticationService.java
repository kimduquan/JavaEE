package epf.workflow.authentication.spi;

import epf.workflow.authentication.schema.CertificateAuthentication;

public interface CertificateAuthenticationService {

	void authenticate(final CertificateAuthentication certificateAuthentication) throws Exception;
}
