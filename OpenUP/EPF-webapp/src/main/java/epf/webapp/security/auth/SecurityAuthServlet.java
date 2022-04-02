package epf.webapp.security.auth;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Hashtable;
import javax.enterprise.context.ApplicationScoped;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import epf.util.http.HttpUtil;

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
				if(!flow.isEmpty() && !cid.isEmpty() && !csrfToken.isEmpty() ) {
					final String redirectUrl = "/webapp/security/auth.xhtml?" + req.getQueryString() + "&flow=" + flow + "&cid=" + cid + "&javax.faces.Token=" + URLEncoder.encode(csrfToken, "UTF-8");
					resp.setHeader("Referrer-Policy", "no-referrer");
					resp.sendRedirect(redirectUrl);
					return;
				}
			}
		}
		else {
			try {
				final URI requestUri = new URI(req.getRequestURI());
				if(requestUri.getFragment() != null && !requestUri.getFragment().isEmpty()) {
					final Hashtable<String, String[]> fragments = HttpUtil.parseQueryString(requestUri.getFragment());
					final String[] stateFragment = fragments.get("state");
					if(stateFragment != null && stateFragment.length > 0) {
						final String[] stateFragments = stateFragment[0].split(System.lineSeparator());
						if(stateFragments.length == 3) {
							final String flow = stateFragments[0];
							final String cid = stateFragments[1];
							final String csrfToken = stateFragments[2];
							if(!flow.isEmpty() && !cid.isEmpty() && !csrfToken.isEmpty() ) {
								final String redirectUrl = "/webapp/security/auth.xhtml?#" + requestUri.getFragment() + "&flow=" + flow + "&cid=" + cid + "&javax.faces.Token=" + URLEncoder.encode(csrfToken, "UTF-8");
								resp.setHeader("Referrer-Policy", "no-referrer");
								resp.sendRedirect(redirectUrl);
								return;
							}
						}
					}
				}
			}
			catch(Exception ex) {
				Response.status(Status.BAD_REQUEST);
			}
		}
		resp.setHeader("Referrer-Policy", "no-referrer");
		resp.sendRedirect("/webapp");
	}
}
