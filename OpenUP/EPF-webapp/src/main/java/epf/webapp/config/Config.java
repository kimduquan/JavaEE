package epf.webapp.config;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;

/**
 * @author PC
 *
 */
@ApplicationScoped
@FacesConfig
@CustomFormAuthenticationMechanismDefinition(loginToContinue = @LoginToContinue())
public class Config {

}
