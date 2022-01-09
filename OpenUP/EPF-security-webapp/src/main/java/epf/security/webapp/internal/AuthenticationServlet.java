package epf.security.webapp.internal;

import java.io.IOException;
import java.util.Set;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.BasicAuthenticationCredential;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import epf.security.webapp.Naming;

/**
 * @author PC
 *
 */
@WebServlet(urlPatterns = Naming.LOGIN_PAGE, loadOnStartup = 1)
public class AuthenticationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Inject
	private transient SecurityContext context;
	
	/**
	 *
	 */
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		final Set<TokenPrincipal> principals = context.getPrincipalsByType(TokenPrincipal.class);
		final TokenPrincipal principal = principals.isEmpty() ? null : principals.iterator().next();
		if(principal == null) {
			final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			if(authHeader == null || authHeader.isEmpty()) {
				response.setHeader(HttpHeaders.WWW_AUTHENTICATE, HttpServletRequest.BASIC_AUTH + " realm=\"" + Naming.REALM_NAME + "\"");
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
			else {
				final String base64Header = authHeader.substring(HttpServletRequest.BASIC_AUTH.length() + 1);
				final BasicAuthenticationCredential credential = new BasicAuthenticationCredential(base64Header);
				final AuthenticationParameters params = AuthenticationParameters.withParams().credential(credential);
				final AuthenticationStatus status = context.authenticate(request, response, params);
				switch(status) {
				case NOT_DONE:
					response.sendError(Response.Status.UNAUTHORIZED.getStatusCode());
					break;
				case SEND_CONTINUE:
					break;
				case SEND_FAILURE:
					response.sendError(Response.Status.UNAUTHORIZED.getStatusCode());
					break;
				case SUCCESS:
					break;
				default:
					break;
				}
			}
		}
	}
}
