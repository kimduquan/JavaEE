package epf.webapp.event;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;

/**
 * @author PC
 *
 */
@ApplicationScoped
@FacesConfig
@BasicAuthenticationMechanismDefinition
public class WebApp {
	
}
