package epf.webapp.persistence;

import epf.naming.Naming;
import epf.webapp.internal.LogoutServlet;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(Naming.WebApp.Internal.LOGOUT)
public class Logout extends LogoutServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
