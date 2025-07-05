package epf.rules.admin;

import java.io.InputStream;
import java.util.HashMap;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import javax.rules.admin.RuleExecutionSet;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.health.Readiness;
import epf.naming.Naming;

@Path(Naming.Rules.RULES_ADMIN)
@ApplicationScoped
public class Admin implements epf.rules.client.admin.Admin {
	
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
