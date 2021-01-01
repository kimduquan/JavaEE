/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp;

import epf.schema.OpenUP;
import epf.schema.openup.Role;
import java.io.IOException;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;
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

/**
 *
 * @author FOXCONN
 */
@WebServlet(
        name = "WebApp", 
        urlPatterns = {"/*"}, 
        loadOnStartup = 1, 
        asyncSupported = true, 
        description = "OpenUP",
        displayName = OpenUP.Schema
)
@ServletSecurity(
        @HttpConstraint(
                value = EmptyRoleSemantic.PERMIT,
                transportGuarantee = TransportGuarantee.CONFIDENTIAL,
                rolesAllowed = Role.ANY_ROLE
        )
)
@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = OpenUP.Schema)
@FacesConfig
@Named("webapp")
@RolesAllowed(Role.ANY_ROLE)
public class WebApp extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException{
        req.getRequestDispatcher("faces/tasks.xhtml").forward(req, resp);
    }
}
