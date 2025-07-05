package epf.workflow.authentication.spi;

import epf.workflow.authentication.schema.OpenIdConnectAuthentication;

public interface OpenIdConnectAuthenticationService {

	void authenticate(final OpenIdConnectAuthentication openIdConnectAuthentication) throws Exception;
}
