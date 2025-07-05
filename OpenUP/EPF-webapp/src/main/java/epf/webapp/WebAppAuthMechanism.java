package epf.webapp;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.AutoApplySession;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import epf.webapp.internal.AuthMechanism;

/**
 * @author PC
 *
 */
@ApplicationScoped
@AutoApplySession
@LoginToContinue(
		loginPage = "/security/login.html"
		)
public class WebAppAuthMechanism implements HttpAuthenticationMechanism {
	
	/**
	 * 
	 */
	@Inject
	private transient AuthMechanism authMechanism;

	@Override
	public AuthenticationStatus validateRequest(final HttpServletRequest request, final HttpServletResponse response,
			final HttpMessageContext httpMessageContext) throws AuthenticationException {
		return authMechanism.validateRequest(request, response, httpMessageContext);
	}

}
