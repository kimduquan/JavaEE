package epf.mcp.gateway;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;
import epf.query.schema.NativeQuery;
import epf.query.schema.ResultList;
import epf.query.schema.SingleResult;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = Naming.Query.QUERY_CONFIG)
public interface QueryClient {

	@POST
	@Path("query/result")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	SingleResult executeQuerySingleResult(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
			final String authorization, 
			final NativeQuery query) throws Exception;
	
	@POST
	@Path("query/results")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	ResultList executeQueryResultList(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
			final String authorization,
			@QueryParam(Naming.Query.Client.FIRST)
            final Integer firstResult,
            @QueryParam(Naming.Query.Client.MAX)
            final Integer maxResults,
            final NativeQuery query) throws Exception;
	
	@GET
    @Path("entity/{schema}/{entity}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	Response getEntity(
    		@PathParam(Naming.SCHEMA)
            final String schema,
            @PathParam(Naming.Query.ENTITY)
            final String name,
            @PathParam(Naming.Query.ID)
            final String id) throws Exception;
	
	@HEAD
	@Path("entity/{schema}/{entity}")
	Response countEntity(
    		@PathParam(Naming.SCHEMA)
            final String schema,
            @PathParam(Naming.Query.ENTITY)
            final String name) throws Exception;
}
