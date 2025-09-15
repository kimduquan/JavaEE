package epf.webapp.persistence;

import epf.webapp.internal.LogoutServlet;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/Logout")
public class Logout extends LogoutServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Session session;

	@Override
	protected epf.management.schema.Session getSession() {
		return session;
	}
}
