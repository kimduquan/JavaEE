package epf.workflow.service;

import epf.workflow.schema.CertificateAuthentication;

public interface CertificateAuthenticationService {

	void authenticate(final CertificateAuthentication certificateAuthentication) throws Exception;
}
