package epf.webapp.security;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@ApplicationScoped
@FormAuthenticationMechanismDefinition(
		loginToContinue = @LoginToContinue()
		)
@DeclareRoles(value = { Naming.Security.DEFAULT_ROLE })
public class Security {

}
