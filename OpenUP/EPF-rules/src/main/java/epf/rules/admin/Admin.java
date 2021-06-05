/**
 * 
 */
package epf.rules.admin;

import java.io.InputStream;
import java.util.HashMap;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.rules.admin.RuleExecutionSet;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author PC
 *
 */
@Path("rules/admin")
@ApplicationScoped
public class Admin implements epf.client.rules.admin.Admin {
	
	/**
	 * 
	 */
	@Inject
	private transient Administrator administrator;

	@Override
	public Response registerRuleExecutionSet(String ruleSet, InputStream input) throws Exception {
		RuleExecutionSet ruleExeSet = administrator.getLocalRuleProvider().createRuleExecutionSet(input, new HashMap<Object, Object>());
		administrator.getRuleAdmin().registerRuleExecutionSet(ruleExeSet.getName(), ruleExeSet, new HashMap<Object, Object>());
		return Response.ok().build();
	}
}
