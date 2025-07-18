package epf.webapp.security.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/security/auth/")
@ApplicationScoped
public class SecurityAuthServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(
			final HttpServletRequest req, 
			final HttpServletResponse resp)
	        throws ServletException, IOException {
		final String state = req.getParameter("state");
		if(state != null && !state.isEmpty()) {
			final String[] fragments = state.split(System.lineSeparator());
			if(fragments.length == 2) {
				final String cid = fragments[0];
				final String csrfToken = fragments[1];
				if(!cid.isEmpty() && !csrfToken.isEmpty() ) {
					final String redirectUrl = epf.webapp.naming.Naming.Security.Auth.CONTEXT_ROOT + epf.webapp.naming.Naming.View.SECURITY_AUTH_PAGE + "?" + req.getQueryString() + "&cid=" + cid + "&javax.faces.Token=" + URLEncoder.encode(csrfToken, "UTF-8");
					resp.setHeader("Referrer-Policy", "no-referrer");
					resp.sendRedirect(redirectUrl);
				}
			}
		}
		else {
			handleFragment(resp);
		}
	}
	
	private void handleFragment(final HttpServletResponse resp) throws IOException {
		final PrintWriter writer = resp.getWriter();
		writer.println("<!DOCTYPE html>");
		writer.println("<html>");
		writer.println("<head>");
		writer.println("<script type=\"text/javascript\">");
		writer.println("window.onload = function() { if (window.location.href.includes('#')){ window.location.href = window.location.href.replace('#', '?'); } }");
		writer.println("</script>");
		writer.println("</head>");
		writer.println("</html>");
	}
}
