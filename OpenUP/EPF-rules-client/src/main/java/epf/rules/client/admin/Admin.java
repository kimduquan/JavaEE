package epf.rules.client.admin;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import epf.client.util.Client;
import epf.naming.Naming;
import java.io.InputStream;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.client.Entity;

@Path(Naming.Rules.RULES_ADMIN)
public interface Admin {

	@Path("{ruleSet}")
	@PUT
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	Response registerRuleExecutionSet(
			@PathParam("ruleSet")
			final String name,
			@Context
			final UriInfo uriInfo,
			final InputStream input
			) throws Exception;
	
	static Response registerRuleExecutionSet(
			final Client client, 
			final String name, 
			final InputStream input) {
		return client
				.request(
						target -> target.path("admin").path(name), 
						req -> req
						)
				.put(
						Entity.entity(
								input, 
								MediaType.APPLICATION_OCTET_STREAM
								)
						);
	}
	
	@Path("{ruleSet}")
	@DELETE
	Response deregisterRuleExecutionSet(
			@PathParam("ruleSet")
			final String name,
			@Context
			final UriInfo uriInfo
			) throws Exception;
	
	static Response deregisterRuleExecutionSet(
			final Client client,
			final String name
			) throws Exception{
		return client
				.request(
						target -> target.path("admin").path(name), 
						req -> req
						)
				.delete();
	}
}
