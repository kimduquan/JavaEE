package epf.workflow.service;

import epf.workflow.schema.OpenIdConnectAuthentication;

public interface OpenIdConnectAuthenticationService {

	void authenticate(final OpenIdConnectAuthentication openIdConnectAuthentication) throws Exception;
}
