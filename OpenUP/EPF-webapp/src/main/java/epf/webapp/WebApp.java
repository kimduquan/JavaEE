package epf.webapp;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@ApplicationScoped
@FacesConfig
@DeclareRoles(value = { Naming.Security.DEFAULT_ROLE })
public class WebApp {
	
}
