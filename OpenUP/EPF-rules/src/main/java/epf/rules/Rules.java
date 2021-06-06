/**
 * 
 */
package epf.rules;

import java.io.InputStream;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.rules.Handle;
import javax.rules.StatelessRuleSession;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.schema.roles.Role;
import epf.util.json.Adapter;
import epf.util.json.Decoder;
import epf.util.json.Encoder;

/**
 * @author PC
 *
 */
@Path("rules")
@RequestScoped
@RolesAllowed(Role.DEFAULT_ROLE)
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
	public Response executeRules(final String ruleSet, final InputStream stream) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withAdapters(new Adapter()))){
			final Decoder decoder = new Decoder();
			final List<Object> input  = decoder.decodeArray(jsonb, stream);
			final StatelessRuleSession ruleSession = request.createRuleSession(ruleSet);
			@SuppressWarnings("unchecked")
			final List<Object> result = (List<Object>)ruleSession.executeRules(input);
			final Encoder encoder = new Encoder();
			final String json = encoder.encodeArray(jsonb, result);
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		}
	}

	@Override
	public Response executeRules(final String ruleSet) throws Exception {
		session.getRuleSession(ruleSet).executeRules();
		return Response.ok().build();
	}

	@Override
	public Response addObject(final String ruleSet, final InputStream input) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withAdapters(new Adapter()))){
			final Decoder decoder = new Decoder();
			final Object object = decoder.decode(jsonb, input);
			final Handle handle = session.getRuleSession(ruleSet).addObject(object);
			return Response.ok(handle).build();
		}
	}

}
