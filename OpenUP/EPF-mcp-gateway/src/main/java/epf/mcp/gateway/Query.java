package epf.mcp.gateway;

import java.util.Map;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.query.schema.NativeQuery;
import epf.query.schema.ResultList;
import epf.query.schema.SingleResult;
import io.quarkiverse.mcp.server.McpServer;
import io.quarkiverse.mcp.server.ResourceTemplate;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Authenticated
public class Query {
	
	@RestClient
	transient QueryClient queryClient;
	
	@RestClient
	transient SchemaClient schemaClient;
	
	@Inject
	JsonWebToken jwt;

	@McpServer(Naming.QUERY)
	@Tool(name = "executeQuerySingleResult", description = "Execute a SELECT query that returns a single result.", structuredContent = true)
	@RunOnVirtualThread
    SingleResult executeQuerySingleResult(
    		@ToolArg(name = "query", description = "a Jakarta Persistence query string")
    		final String query,
    		@ToolArg(name = "parameters", description = "Bind argument values to named parameters.", required = false)
    		final Map<String, Object> parameters) throws Exception {
		final NativeQuery nativeQuery = new NativeQuery();
		nativeQuery.setQuery(query);
		nativeQuery.setParameters(parameters);
		final String authorization = "Bearer " + jwt.getRawToken();
		final SingleResult singleResult = queryClient.executeQuerySingleResult(authorization, nativeQuery);
        return singleResult;
    }
	
	@McpServer(Naming.QUERY)
	@Tool(name = "executeQueryResultList", description = "Execute a SELECT query and return the query results as an List.", structuredContent = true)
	@RunOnVirtualThread
	ResultList executeQueryResultList(
    		@ToolArg(name = "query", description = "a Jakarta Persistence query string")
    		final String query,
    		@ToolArg(name = "parameters", description = "Bind argument values to named parameters.", required = false)
    		final Map<String, Object> parameters,
    		@ToolArg(name = "firstResult", description = "Set the position of the first result to retrieve.", required = false)
    		final Integer firstResult,
    		@ToolArg(name = "maxResults", description = "Set the maximum number of results to retrieve.", required = false)
    		final Integer maxResults) throws Exception {
		final NativeQuery nativeQuery = new NativeQuery();
		nativeQuery.setQuery(query);
		nativeQuery.setParameters(parameters);
		final String authorization = "Bearer " + jwt.getRawToken();
		final ResultList resultList = queryClient.executeQueryResultList(authorization, maxResults, firstResult, nativeQuery);
        return resultList;
    }
	
	@McpServer(Naming.QUERY)
	@ResourceTemplate(uriTemplate = "query://{schema}/{entity}/{id}")
	@RunOnVirtualThread
	Object getEntity(final String schema, final String entity, final String id) throws Exception {
		final String authorization = "Bearer " + jwt.getRawToken();
		return queryClient.getEntity(authorization, schema, entity, id).getEntity();
	}
}
