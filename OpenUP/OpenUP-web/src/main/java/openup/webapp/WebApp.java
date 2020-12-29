/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp;

import java.io.IOException;
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
import openup.client.epf.schema.Roles;

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
        displayName = "OpenUP"
)
@ServletSecurity(
        @HttpConstraint(
                value = EmptyRoleSemantic.PERMIT,
                transportGuarantee = TransportGuarantee.CONFIDENTIAL,
                rolesAllowed = Roles.ANY_ROLE
        )
)
@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = "OpenUP")
@FacesConfig
@Named("webapp")
public class WebApp extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException{
        req.getRequestDispatcher("faces/tasks.xhtml").forward(req, resp);
    }
}
