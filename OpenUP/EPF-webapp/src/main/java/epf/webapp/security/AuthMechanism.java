package epf.webapp.security;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author PC
 *
 */
@ApplicationScoped
@RememberMe(isRememberMeExpression = "#{self.rememberMe}")
@AutoApplySession
@LoginToContinue(
		loginPage = "/security/login.xhtml"
		)
public class AuthMechanism implements HttpAuthenticationMechanism {
	
	/**
	 * 
	 */
	@Inject
	private transient IdentityStoreHandler handler;
	
	/**
	 * 
	 */
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
	
	/**
	 * @return
	 */
	public boolean isRememberMe() {
		return authParams.isRememberMe();
	}
}
