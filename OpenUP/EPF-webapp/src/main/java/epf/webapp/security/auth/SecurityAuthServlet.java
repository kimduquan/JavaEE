package epf.webapp.security.auth;

import java.io.IOException;
import java.net.URLEncoder;
import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import epf.security.auth.openid.AuthRequest;

/**
 * @author PC
 *
 */
@WebServlet("/security/auth/")
@ApplicationScoped
public class SecurityAuthServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	protected void doGet(
			final HttpServletRequest req, 
			final HttpServletResponse resp)
	        throws ServletException, IOException {
		final String state = req.getParameter("state");
		if(state != null && !state.isEmpty()) {
			final String[] fragments = state.split(AuthRequest.STATE_SEPARATOR);
			final String cid = fragments[0];
			final String csrfToken = fragments[1];
			final String redirectUrl = "/webapp/security/auth.xhtml?" + req.getQueryString() + "&cid=" + cid + "&javax.faces.Token=" + URLEncoder.encode(csrfToken, "UTF-8");
			resp.setHeader("Referrer-Policy", "no-referrer");
			resp.sendRedirect(redirectUrl);
		}
	}
}
