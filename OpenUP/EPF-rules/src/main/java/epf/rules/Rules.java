package epf.rules;

import java.io.InputStream;
import java.util.List;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;
import javax.rules.Handle;
import javax.rules.StatelessRuleSession;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import org.eclipse.microprofile.health.Readiness;
import epf.function.LinkFunction;
import epf.naming.Naming;
import epf.util.json.ext.Adapter;
import epf.util.json.ext.Decoder;
import epf.util.json.ext.Encoder;

@Path(Naming.RULES)
@RequestScoped
public class Rules implements epf.rules.client.Rules {
	
	@Inject @Readiness
	private transient Provider provider;
	
	@Inject
	private transient Session session;
	
	@Inject
	private transient Request request;

	@Override
	public Response executeRules(final String ruleSet, final InputStream stream) throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withAdapters(new Adapter()))){
			final Decoder decoder = new Decoder();
			final List<Object> input  = decoder.decodeArray(jsonb, stream);
			final StatelessRuleSession ruleSession = request.createRuleSession(ruleSet);
			@SuppressWarnings("unchecked")
			final List<Object> output = (List<Object>)ruleSession.executeRules(input);
			ResponseBuilder builder = Response.ok();
			int linkIndex = 0;
			final Encoder encoder = new Encoder();
			for(Object object : output) {
				if(object instanceof LinkFunction) {
					final LinkFunction func = (LinkFunction) object;
					final Link link = func.toLink(linkIndex);
					builder = builder.links(link);
					linkIndex++;
				}
				else {
					final String json = encoder.encode(jsonb, object);
					builder = builder.entity(json).type(MediaType.APPLICATION_JSON);
				}
			}
			return builder.build();
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

	@Override
	public Response getRegistrations() throws Exception {
		try(Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withAdapters(new Adapter()))){
			final Encoder encoder = new Encoder();
			@SuppressWarnings("unchecked")
			List<Object> registrations = provider.getRuleRuntime().getRegistrations();
			final String json = encoder.encodeArray(jsonb, registrations);
			return Response.ok(json, MediaType.APPLICATION_JSON).build();
		}
	}

}
