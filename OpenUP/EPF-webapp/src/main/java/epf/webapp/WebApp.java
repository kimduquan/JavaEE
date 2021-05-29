/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.webapp;

import epf.schema.EPF;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.EmptyRoleSemantic;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import epf.schema.roles.Role;

/**
 *
 * @author FOXCONN
 */
@WebServlet(
        name = "WebApp", 
        urlPatterns = {"/*"}, 
        loadOnStartup = 1, 
        asyncSupported = true, 
        description = "EPF",
        displayName = EPF.SCHEMA
)
@ServletSecurity(
        value = @HttpConstraint(
                value = EmptyRoleSemantic.DENY,
                transportGuarantee = TransportGuarantee.CONFIDENTIAL,
                rolesAllowed = Role.DEFAULT_ROLE
        ),
        httpMethodConstraints = {
            @HttpMethodConstraint(
                    value = HttpMethod.GET,
                    emptyRoleSemantic = EmptyRoleSemantic.DENY,
                    transportGuarantee = TransportGuarantee.CONFIDENTIAL,
                    rolesAllowed = Role.DEFAULT_ROLE
            ),
            @HttpMethodConstraint(
                    value = HttpMethod.POST,
                    emptyRoleSemantic = EmptyRoleSemantic.DENY,
                    transportGuarantee = TransportGuarantee.CONFIDENTIAL,
                    rolesAllowed = Role.DEFAULT_ROLE
            )
        }
)
@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = EPF.SCHEMA)
@FacesConfig
@Named("webapp")
public class WebApp extends HttpServlet {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException{
        req.getRequestDispatcher("tasks.html").forward(req, resp);
    }
}
