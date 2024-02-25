package epf.webapp.workflow;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
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
