package epf.webapp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;
import jakarta.security.enterprise.authentication.mechanism.http.OpenIdAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.openid.ClaimsDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.openid.LogoutDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.openid.OpenIdConstant;

@ApplicationScoped
@FacesConfig
@OpenIdAuthenticationMechanismDefinition(
		providerURI = "${config.providerURI}",
		clientId = "${config.clientId}",
		clientSecret = "${config.clientSecret}",
		claimsDefinition = @ClaimsDefinition(
					callerNameClaim = OpenIdConstant.SUBJECT_IDENTIFIER
				),
		logout = @LogoutDefinition(
					notifyProvider = true,
					accessTokenExpiry = true,
					identityTokenExpiry = true
				),
		redirectToOriginalResource = true,
		tokenAutoRefresh = true
		)
public class WebApp {

}
