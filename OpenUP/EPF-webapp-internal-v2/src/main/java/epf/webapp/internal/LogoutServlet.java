package epf.webapp.internal;

import java.io.IOException;
import epf.management.schema.Session;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public abstract class LogoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
		getSession().setOrganization(null);
		getSession().setPrincipal(null);
		request.logout();
	}
	
	protected abstract Session getSession();
}
