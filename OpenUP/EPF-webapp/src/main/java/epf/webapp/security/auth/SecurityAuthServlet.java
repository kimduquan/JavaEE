package epf.webapp.security.auth;

import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			final String redirectUrl = "/webapp/security/auth?cid=" + state + req.getQueryString();
			resp.sendRedirect(redirectUrl);
		}
	}
}
