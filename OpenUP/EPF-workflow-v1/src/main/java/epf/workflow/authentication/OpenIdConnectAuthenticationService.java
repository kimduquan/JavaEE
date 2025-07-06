package epf.workflow.authentication;

import epf.workflow.authentication.schema.OpenIdConnectAuthentication;

public interface OpenIdConnectAuthenticationService {

	void authenticate(final OpenIdConnectAuthentication openIdConnectAuthentication) throws Exception;
}
