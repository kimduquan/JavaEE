package epf.webapp.security.auth;

import java.io.IOException;
import java.net.URLEncoder;
import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
			final String[] fragments = state.split(System.lineSeparator());
			if(fragments.length == 3) {
				final String flow = fragments[0];
				final String cid = fragments[1];
				final String csrfToken = fragments[2];
				if(!cid.isEmpty() && !csrfToken.isEmpty() ) {
					final String redirectUrl = "/webapp/security/auth.xhtml?" + req.getQueryString() + "&flow=" + flow + "&cid=" + cid + "&javax.faces.Token=" + URLEncoder.encode(csrfToken, "UTF-8");
					resp.setHeader("Referrer-Policy", "no-referrer");
					resp.sendRedirect(redirectUrl);
					return;
				}
			}
		}
		Response.status(Status.BAD_REQUEST);
	}
}
