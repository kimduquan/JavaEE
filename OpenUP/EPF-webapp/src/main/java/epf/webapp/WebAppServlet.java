package epf.webapp;

import java.io.IOException;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/"}, loadOnStartup = 1)
@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "EPF")
@ServletSecurity(
		@HttpConstraint(
				value = EmptyRoleSemantic.PERMIT,
				transportGuarantee = TransportGuarantee.CONFIDENTIAL,
				rolesAllowed = {epf.client.security.Security.DEFAULT_ROLE}
				)
		)
@RolesAllowed(epf.client.security.Security.DEFAULT_ROLE)
public class WebAppServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WebAppServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("<h1><font color=green>Simple Servlet ran successfully</font></h1>"
                        + "Powered by WebSphere Application Server Liberty Profile");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        //    TODO Auto-generated method stub
    }

}
