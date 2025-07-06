package epf.workflow.authentication;

import epf.workflow.authentication.schema.CertificateAuthentication;

public interface CertificateAuthenticationService {

	void authenticate(final CertificateAuthentication certificateAuthentication) throws Exception;
}
