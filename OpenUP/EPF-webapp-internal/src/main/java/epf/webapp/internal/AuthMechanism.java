package epf.webapp.internal;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@ApplicationScoped
public class AuthMechanism {
	
	@Inject
	private transient IdentityStoreHandler handler;

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
