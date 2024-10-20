package epf.workflow.functions.openapi;

import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.PathItem;
import org.eclipse.microprofile.openapi.models.parameters.Parameter;
import org.eclipse.microprofile.openapi.models.servers.Server;
import epf.nosql.schema.EitherUtil;
import epf.util.json.ext.JsonUtil;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.auth.AuthDefinition;
import epf.workflow.schema.auth.BearerPropertiesDefinition;
import epf.workflow.schema.function.FunctionDefinition;
import io.smallrye.openapi.runtime.io.OpenApiParser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class OpenAPIFunctions {
	
	private transient final Map<String, OpenAPI> openAPIs = new ConcurrentHashMap<>();
	
	private static OpenAPI read(final URL url) throws Exception {
		return OpenApiParser.parse(url);
	}

	private OpenAPI getOpenAPI(final FunctionDefinition functionDefinition) throws Exception {
		final int index = functionDefinition.getOperation().indexOf("#");
		final String pathToOpenAPIDefinition = functionDefinition.getOperation().substring(0, index);
		if(!openAPIs.containsKey(pathToOpenAPIDefinition)) {
			final URI uri = URI.create(pathToOpenAPIDefinition);
			final OpenAPI openAPI = read(uri.toURL());
			openAPIs.put(pathToOpenAPIDefinition, openAPI);
		}
		final OpenAPI openAPI = openAPIs.get(pathToOpenAPIDefinition);
		return openAPI;
	}
	
	private String getOperationId(final FunctionDefinition functionDefinition) {
		final int index = functionDefinition.getOperation().indexOf("#");
		final String operationId = functionDefinition.getOperation().substring(index + 1);
		return operationId;
	}
	
	private Response invoke(final WorkflowDefinition workflowDefinition, final FunctionDefinition functionDefinition, final JsonValue data, final OpenAPI openAPI, final String path, final PathItem pathItem, final PathItem.HttpMethod httpMethod, final Operation operation) throws Exception {
		Server server = openAPI.getServers().iterator().next();
		if(operation.getServers() != null && !operation.getServers().isEmpty()) {
			server = operation.getServers().iterator().next();
		}
		else if(pathItem.getServers() != null && !pathItem.getServers().isEmpty()) {
			server = pathItem.getServers().iterator().next();
		}
		final JsonObject input = data.asJsonObject();
		try(Client client = ClientBuilder.newClient()){
			WebTarget target = client.target(server.getUrl()).path(path);
			for(Parameter parameter : operation.getParameters()) {
				switch(parameter.getIn()) {
					case COOKIE:
						break;
					case HEADER:
						break;
					case PATH:
						final Object pathValue = JsonUtil.asValue(input.get(parameter.getName()));
						target = target.resolveTemplate("{" + parameter.getName() + "}", pathValue);
						break;
					case QUERY:
						final Object queryValue = JsonUtil.asValue(input.get(parameter.getName()));
						target = target.queryParam(parameter.getName(), queryValue);
						break;
					default:
						break;
				}
			}
			Builder builder = target.request();
			if(functionDefinition.getAuthRef() != null) {
				final List<AuthDefinition> authDefs = EitherUtil.getArray(workflowDefinition.getAuth());
				for(AuthDefinition authDef : authDefs) {
					if(authDef.getName().equals(functionDefinition.getAuthRef())) {
						switch(authDef.getScheme()) {
							case basic:
								break;
							case bearer:
								final BearerPropertiesDefinition bearerDefinition = (BearerPropertiesDefinition) authDef.getProperties();
								builder = builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + bearerDefinition.getToken());
								break;
							case oauth2:
								break;
							default:
								break;
						}
						break;
					}
				}
			}
			for(Parameter parameter : operation.getParameters()) {
				switch(parameter.getIn()) {
					case COOKIE:
						final String cookieValue = input.getString(parameter.getName());
						builder = builder.cookie(parameter.getName(), cookieValue);
						break;
					case HEADER:
						final Object headerValue = JsonUtil.asValue(input.get(parameter.getName()));
						builder = builder.header(parameter.getName(), headerValue);
						break;
					case PATH:
						break;
					case QUERY:
						break;
					default:
						break;
				}
			}
			Entity<?> entity = null;
			if(operation.getRequestBody() != null) {
				final String[] mediaTypes = operation.getRequestBody().getContent().getMediaTypes().keySet().toArray(new String[0]);
				builder = builder.accept(mediaTypes);
				entity = Entity.entity(data.toString(), mediaTypes[0]);
			}
			Invocation invoke;
			if(entity != null) {
				invoke = builder.build(httpMethod.name(), entity);
			}
			else {
				invoke = builder.build(httpMethod.name());
			}
			return invoke.invoke();
		}
	}
	
	public Response openapi(final WorkflowDefinition workflowDefinition, final FunctionDefinition functionDefinition, final JsonValue data) throws Exception {
		final String operationId = getOperationId(functionDefinition);
		final OpenAPI openAPI = getOpenAPI(functionDefinition);
		for(Entry<String, PathItem> pathItemEntry : openAPI.getPaths().getPathItems().entrySet()) {
			final String path = pathItemEntry.getKey();
			final PathItem pathItem = pathItemEntry.getValue();
			for(Entry<PathItem.HttpMethod, Operation> operationEntry : pathItem.getOperations().entrySet()) {
				final PathItem.HttpMethod httpMethod = operationEntry.getKey();
				final Operation operation = operationEntry.getValue();
				if(operation.getOperationId().equals(operationId)) {
					return invoke(workflowDefinition, functionDefinition, data, openAPI, path, pathItem, httpMethod, operation);
				}
			}
		}
		throw new BadRequestException();
	}
}
