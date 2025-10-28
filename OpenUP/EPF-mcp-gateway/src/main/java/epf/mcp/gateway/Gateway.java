package epf.mcp.gateway;

import java.util.Map;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.query.schema.NativeQuery;
import epf.query.schema.ResultList;
import epf.query.schema.SingleResult;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;

@ApplicationScoped
public class Gateway {
	
	@RestClient
	transient QueryClient queryClient;
	
	@Inject
	JsonWebToken jwt;

	@Tool(description = "Execute a JPQL query which return a single result object", structuredContent = true)
	@RunOnVirtualThread
    SingleResult executeSingleResultQuery(
    		@ToolArg(description = "The JPQL query")
    		@NotBlank
    		final String query,
    		@ToolArg(description = "The input parameters", required = false)
    		final Map<String, Object> parameters) throws Exception {
		final NativeQuery nativeQuery = new NativeQuery();
		nativeQuery.setQuery(query);
		nativeQuery.setParameters(parameters);
		final String authorization = "Bearer " + jwt.getRawToken();
		final SingleResult singleResult = queryClient.executeSingleResultQuery(authorization, nativeQuery);
        return singleResult;
    }
	
	@Tool(description = "Execute a JPQL query which return a list of object", structuredContent = true)
	@RunOnVirtualThread
	ResultList executeResultListQuery(
    		@ToolArg(description = "The JPQL query")
    		@NotBlank
    		final String query,
    		@ToolArg(description = "The input parameters", required = false)
    		final Map<String, Object> parameters,
    		@ToolArg(description = "The first result position", required = false)
    		final Integer firstResult,
    		@ToolArg(description = "The first result position", required = false)
    		final Integer maxResults) throws Exception {
		final NativeQuery nativeQuery = new NativeQuery();
		nativeQuery.setQuery(query);
		nativeQuery.setParameters(parameters);
		final String authorization = "Bearer " + jwt.getRawToken();
		final ResultList resultList = queryClient.executeResultListQuery(authorization, maxResults, firstResult, nativeQuery);
        return resultList;
    }
}
