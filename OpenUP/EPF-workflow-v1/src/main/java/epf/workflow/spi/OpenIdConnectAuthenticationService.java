package epf.workflow.spi;

import epf.workflow.schema.OpenIdConnectAuthentication;

public interface OpenIdConnectAuthenticationService {

	void authenticate(final OpenIdConnectAuthentication openIdConnectAuthentication) throws Exception;
}
