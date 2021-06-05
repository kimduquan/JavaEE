/**
 * 
 */
package epf.rules;

import java.io.InputStream;
import java.util.ArrayList;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.rules.Handle;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author PC
 *
 */
@Path("rules")
@RequestScoped
public class Rules implements epf.client.rules.Rules {
	
	/**
	 * 
	 */
	@Inject
	private transient Session session;
	
	/**
	 * 
	 */
	@Inject
	private transient Request request;

	@Override
	public Response executeRules(final String ruleSet, final InputStream input) throws Exception {
		final Object result = request.createRuleSession(ruleSet).executeRules(new ArrayList<Object>());
		return Response.ok(result).build();
	}

	@Override
	public Response executeRules(final String ruleSet) throws Exception {
		session.getRuleSession(ruleSet).executeRules();
		return Response.ok().build();
	}

	@Override
	public Response addObject(final String ruleSet, final InputStream input) throws Exception {
		final Handle handle = session.getRuleSession(ruleSet).addObject(new Object());
		return Response.ok(handle).build();
	}

}
