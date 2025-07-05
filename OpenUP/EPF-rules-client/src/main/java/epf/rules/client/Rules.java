package epf.rules.client;

import java.io.InputStream;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;

@Path(Naming.RULES)
public interface Rules {

	@POST
	@Path("{ruleSet}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response executeRules(
			@PathParam("ruleSet")
			final String ruleSet,
			final InputStream input
			) throws Exception;
	
	static Response executeRules(
			final Client client, 
			final String ruleSet,
			final String input
			) throws Exception{
		return client
				.request(
						target -> target.path(ruleSet), 
						req -> req.accept(MediaType.APPLICATION_JSON)
						)
				.post(Entity.entity(input, MediaType.APPLICATION_JSON));
	}
	
	@PUT
	@Path("{ruleSet}")
	@Produces(MediaType.APPLICATION_JSON)
	Response executeRules(
			@PathParam("ruleSet")
			final String ruleSet
			) throws Exception;
	
	static Response executeRules(
			final Client client, 
			final String ruleSet) throws Exception{
		return client
				.request(
						target -> target.path(ruleSet), 
						req -> req.accept(MediaType.APPLICATION_JSON)
						)
				.put(Entity.json(null));
	}
	
	@PATCH
	@Path("{ruleSet}")
	@Consumes(MediaType.APPLICATION_JSON)
	Response addObject(
			@PathParam("ruleSet")
			final String ruleSet,
			final InputStream input) throws Exception;
	
	static Response addObject(
			final Client client, 
			final String ruleSet, 
			final String input) {
		return client
				.request(
						target -> target.path(ruleSet), 
						req -> req
						)
				.method(HttpMethod.PATCH, Entity.entity(input, MediaType.APPLICATION_JSON));
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Response getRegistrations() throws Exception;
	
	static Response getRegistrations(final Client client) {
		return client
				.request(
						target -> target, 
						req -> req.accept(MediaType.APPLICATION_JSON)
						)
				.get();
	}
}
