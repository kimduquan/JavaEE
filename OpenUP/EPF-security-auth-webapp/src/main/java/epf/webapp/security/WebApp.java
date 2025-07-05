package epf.webapp.security;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;
import epf.naming.Naming;

@ApplicationScoped
@FacesConfig
@DeclareRoles(value = { Naming.Security.DEFAULT_ROLE })
public class WebApp {
	
}
