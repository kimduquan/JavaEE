package epf.webapp.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.AutoApplySession;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.security.enterprise.authentication.mechanism.http.RememberMe;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ApplicationScoped
@RememberMe(isRememberMeExpression = "#{self.rememberMe}")
@AutoApplySession
@LoginToContinue(
		loginPage = "/security/login.html"
		)
public class AuthMechanism implements HttpAuthenticationMechanism {
	
	@Inject
	private transient IdentityStoreHandler handler;
	
	@Inject
	private AuthParams authParams;

	@Override
	public AuthenticationStatus validateRequest(final HttpServletRequest request, final HttpServletResponse response,
			final HttpMessageContext httpMessageContext) throws AuthenticationException {
		if(httpMessageContext.isAuthenticationRequest()) {
			final Credential credential = httpMessageContext.getAuthParameters().getCredential();
			final CredentialValidationResult result = handler.validate(credential);
			return httpMessageContext.notifyContainerAboutLogin(result);
		}
		return httpMessageContext.doNothing();
	}
	
	public boolean isRememberMe() {
		return authParams.isRememberMe();
	}
}
