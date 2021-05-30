/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.webapp;

import epf.schema.EPF;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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
                transportGuarantee = TransportGuarantee.CONFIDENTIAL,
                rolesAllowed = Role.DEFAULT_ROLE
        )
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
}
