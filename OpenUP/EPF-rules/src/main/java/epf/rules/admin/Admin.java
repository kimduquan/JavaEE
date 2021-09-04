/**
 * 
 */
package epf.rules.admin;

import java.io.InputStream;
import java.util.HashMap;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.rules.admin.RuleExecutionSet;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.health.Readiness;
import epf.client.security.Security;

/**
 * @author PC
 *
 */
@Path("rules/admin")
@ApplicationScoped
@RolesAllowed(Security.DEFAULT_ROLE)
public class Admin implements epf.client.rules.admin.Admin {
	
	/**
	 * 
	 */
	@Inject @Readiness
	private transient Administrator administrator;

	@Override
	public Response registerRuleExecutionSet(final String ruleSet, final UriInfo uriInfo, final InputStream input) throws Exception {
		final RuleExecutionSet ruleExeSet = administrator.getLocalRuleProvider().createRuleExecutionSet(input, new HashMap<Object, Object>());
		administrator.getRuleAdmin().registerRuleExecutionSet(ruleSet, ruleExeSet, new HashMap<Object, Object>());
		return Response.ok().build();
	}

	@Override
	public Response deregisterRuleExecutionSet(final String name, final UriInfo uriInfo) throws Exception {
		administrator.getRuleAdmin().deregisterRuleExecutionSet(name, new HashMap<Object, Object>());
		return Response.ok().build();
	}
}
