/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp;

import epf.schema.OpenUP;
import epf.schema.openup.Role;
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
        value = @HttpConstraint(
                value = EmptyRoleSemantic.DENY,
                transportGuarantee = TransportGuarantee.CONFIDENTIAL
        ),
        httpMethodConstraints = {
            @HttpMethodConstraint(
                    value = HttpMethod.GET,
                    emptyRoleSemantic = EmptyRoleSemantic.PERMIT,
                    transportGuarantee = TransportGuarantee.CONFIDENTIAL,
                    rolesAllowed = Role.ANY_ROLE
            ),
            @HttpMethodConstraint(
                    value = HttpMethod.POST,
                    emptyRoleSemantic = EmptyRoleSemantic.PERMIT,
                    transportGuarantee = TransportGuarantee.CONFIDENTIAL,
                    rolesAllowed = Role.ANY_ROLE
            )
        }
)
@ApplicationScoped
@BasicAuthenticationMechanismDefinition(realmName = OpenUP.Schema)
@FacesConfig
@Named("webapp")
public class WebApp extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException{
        req.getRequestDispatcher("tasks.html").forward(req, resp);
    }
}
