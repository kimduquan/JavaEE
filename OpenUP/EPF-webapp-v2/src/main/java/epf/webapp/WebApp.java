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
		clientSecret = "${config.clientSecret}",
		redirectURI = "${baseURL}/Callback",
		logout = @LogoutDefinition(
					notifyProvider = true,
					accessTokenExpiry = true,
					identityTokenExpiry = true
				),
		useSession = true,
		redirectToOriginalResource = true,
		extraParametersExpression = "${config.extraParameters}",
		tokenAutoRefresh = true
		)
public class WebApp {

}
