package epf.mcp.gateway;

import java.util.Map;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.query.schema.NativeQuery;
import epf.query.schema.ResultList;
import epf.query.schema.SingleResult;
import io.quarkiverse.mcp.server.ResourceTemplate;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Authenticated
public class Query {
	
	@RestClient
	transient QueryClient queryClient;
	
	@RestClient
	transient SchemaClient schemaClient;
	
	@Inject
	JsonWebToken jwt;

	@Tool(name = "query.executeQuerySingleResult", description = "Execute a JPQL query which return a single result object", structuredContent = true)
	@RunOnVirtualThread
    SingleResult executeQuerySingleResult(
    		@ToolArg(name = "query", description = "The JPQL query")
    		final String query,
    		@ToolArg(name = "parameters", description = "The input parameters", required = false)
    		final Map<String, Object> parameters) throws Exception {
		final NativeQuery nativeQuery = new NativeQuery();
		nativeQuery.setQuery(query);
		nativeQuery.setParameters(parameters);
		final String authorization = "Bearer " + jwt.getRawToken();
		final SingleResult singleResult = queryClient.executeQuerySingleResult(authorization, nativeQuery);
        return singleResult;
    }
	
	@Tool(name = "query.executeQueryResultList", description = "Execute a JPQL query which return a list of object", structuredContent = true)
	@RunOnVirtualThread
	ResultList executeQueryResultList(
    		@ToolArg(name = "query", description = "The JPQL query")
    		final String query,
    		@ToolArg(name = "parameters", description = "The input parameters", required = false)
    		final Map<String, Object> parameters,
    		@ToolArg(name = "firstResult", description = "The first result position", required = false)
    		final Integer firstResult,
    		@ToolArg(name = "maxResults", description = "The first result position", required = false)
    		final Integer maxResults) throws Exception {
		final NativeQuery nativeQuery = new NativeQuery();
		nativeQuery.setQuery(query);
		nativeQuery.setParameters(parameters);
		final String authorization = "Bearer " + jwt.getRawToken();
		final ResultList resultList = queryClient.executeQueryResultList(authorization, maxResults, firstResult, nativeQuery);
        return resultList;
    }
	
	@Tool(name = "query.countEntity", description = "Count entity", structuredContent = true)
	@RunOnVirtualThread
	Integer countEntity(
			@ToolArg(name = "schema", description = "The schema")
			final String schema,
			@ToolArg(name = "entity", description = "The entity")
			final String entity) throws Exception {
		final String authorization = "Bearer " + jwt.getRawToken();
		final Response response = queryClient.countEntity(authorization, schema, entity);
		return Integer.valueOf(response.getHeaderString(Naming.Query.COUNT));
	}
	
	@ResourceTemplate(uriTemplate = "query://{schema}/{entity}/{id}")
	@RunOnVirtualThread
	Object getEntity(final String schema, final String entity, final String id) throws Exception {
		final String authorization = "Bearer " + jwt.getRawToken();
		return queryClient.getEntity(authorization, schema, entity, id).getEntity();
	}
}
