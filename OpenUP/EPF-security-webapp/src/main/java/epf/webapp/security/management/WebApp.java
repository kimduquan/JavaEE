package epf.webapp.security.management;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;

/**
 * @author PC
 *
 */
@ApplicationScoped
@FacesConfig
@BasicAuthenticationMechanismDefinition
public class WebApp {
	
}
