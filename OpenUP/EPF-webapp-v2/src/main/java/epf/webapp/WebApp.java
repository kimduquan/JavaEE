package epf.webapp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;
import jakarta.security.enterprise.authentication.mechanism.http.OpenIdAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.openid.LogoutDefinition;

@ApplicationScoped
@FacesConfig
@OpenIdAuthenticationMechanismDefinition(
		providerURI = "${config.providerURI}",
		clientId = "${config.clientId}",
		logout = @LogoutDefinition(
					notifyProvider = true,
					accessTokenExpiry = true,
					identityTokenExpiry = true
				),
		scopeExpression = "${config.scope}",
		redirectToOriginalResource = true,
		extraParametersExpression = "${config.extraParameters}",
		tokenAutoRefresh = true
		)
public class WebApp {

}
