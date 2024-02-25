package epf.webapp.persistence;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
