package epf.webapp.config;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@ApplicationScoped
@FacesConfig
@CustomFormAuthenticationMechanismDefinition(loginToContinue = @LoginToContinue())
@DeclareRoles(value = { Naming.Security.DEFAULT_ROLE })
public class Config {

}
