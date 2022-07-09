package epf.webapp.internal;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
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
public class AuthMechanism {
	
	/**
	 * 
	 */
	@Inject
	private transient IdentityStoreHandler handler;

	/**
	 * @param request
	 * @param response
	 * @param httpMessageContext
	 * @return
	 * @throws AuthenticationException
	 */
	public AuthenticationStatus validateRequest(final HttpServletRequest request, final HttpServletResponse response,
			final HttpMessageContext httpMessageContext) throws AuthenticationException {
		if(httpMessageContext.isAuthenticationRequest()) {
			final Credential credential = httpMessageContext.getAuthParameters().getCredential();
			final CredentialValidationResult result = handler.validate(credential);
			return httpMessageContext.notifyContainerAboutLogin(result);
		}
		return httpMessageContext.doNothing();
	}
}
