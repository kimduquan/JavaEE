package epf.workflow;

import java.util.Map;
import java.util.Map.Entry;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import epf.api.API;
import epf.api.Operation;
import epf.api.PathItem;
import epf.api.PathItem.HttpMethod;
import epf.api.parameter.Parameter;
import epf.api.server.Server;
import epf.workflow.schema.AuthDefinition;
import epf.workflow.schema.BearerPropertiesDefinition;
import epf.workflow.schema.FunctionDefinition;
import epf.workflow.schema.FunctionRefDefinition;
import epf.workflow.schema.Invoke;
import epf.workflow.schema.Scheme;
import epf.workflow.schema.WorkflowDefinition;

/**
 * @author PC
 *
 */
public class RestFunction extends Function {
	
	/**
	 * 
	 */
	private final API api;
	/**
	 * 
	 */
	private final String path;
	/**
	 * 
	 */
	private final PathItem pathItem;
	/**
	 * 
	 */
	private final Operation operation;

	/**
	 * @param workflowDefinition
	 * @param functionDefinition
	 * @param api
	 * @param path
	 * @param pathItem
	 * @param operation
	 * @param functionRefDefinition
	 */
	public RestFunction(WorkflowDefinition workflowDefinition, FunctionDefinition functionDefinition, API api, String path, PathItem pathItem, Operation operation, FunctionRefDefinition functionRefDefinition) {
		super(workflowDefinition, functionDefinition, functionRefDefinition);
		this.api = api;
		this.path = path;
		this.pathItem = pathItem;
		this.operation = operation;
	}

	public API getApi() {
		return api;
	}

	public PathItem getPathItem() {
		return pathItem;
	}

	public Operation getOperation() {
		return operation;
	}

	@Override
	public void invoke() throws Exception {
		Server server = this.getApi().getServers().iterator().next();
		if(this.getOperation().getServers() != null && !this.getOperation().getServers().isEmpty()) {
			server = this.getOperation().getServers().iterator().next();
		}
		else if(this.getPathItem().getServers() != null && !this.getPathItem().getServers().isEmpty()) {
			server = this.getPathItem().getServers().iterator().next();
		}
		HttpMethod method = null;
		for(Entry<HttpMethod, Operation> entry : this.getPathItem().getOperations().entrySet()) {
			if(entry.getValue().getOperationId().equals(this.getOperation().getOperationId())) {
				method = entry.getKey();
				break;
			}
		}
		final Client client = ClientBuilder.newClient();
		WebTarget target = client.target(server.getUrl()).path(this.getPath());
		if(this.getFunctionRefDefinition() != null) {
			@SuppressWarnings("unchecked")
			final Map<String, Object> parameters = (Map<String, Object>) this.getFunctionRefDefinition().getArguments();
			for(Parameter parameter : this.getOperation().getParameters()) {
				switch(parameter.getIn()) {
					case path:
						target = target.path(parameters.get(parameter.getName()).toString());
						break;
					case query:
						target = target.queryParam(parameter.getName(), parameters.get(parameter.getName()));
						break;
					default:
						break;
				}
			}
		}
		Builder builder = target.request();
		if(this.getFunctionRefDefinition() != null) {
			@SuppressWarnings("unchecked")
			final Map<String, Object> parameters = (Map<String, Object>) this.getFunctionRefDefinition().getArguments();
			for(Parameter parameter : this.getOperation().getParameters()) {
				switch(parameter.getIn()) {
					case cookie:
						builder = builder.cookie(parameter.getName(), parameters.get(parameter.getName()).toString());
						break;
					case header:
						builder = builder.header(parameter.getName(), parameters.get(parameter.getName()).toString());
						break;
					default:
						break;
				}
			}
		}
		if(this.getFunctionDefinition().getAuthRef() != null && getWorkflowDefinition().getAuth() instanceof AuthDefinition[]) {
			final AuthDefinition[] authDefs = (AuthDefinition[]) getWorkflowDefinition().getAuth();
			for(AuthDefinition authDef : authDefs) {
				if(authDef.getName().equals(this.getFunctionDefinition().getAuthRef())) {
					if(authDef.getScheme() == Scheme.bearer) {
						final BearerPropertiesDefinition bearerDefinition = (BearerPropertiesDefinition) authDef.getProperties();
						builder = builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + bearerDefinition.getToken());
					}
					break;
				}
			}
		}
		if(this.getOperation().getRequestBody() != null) {
			builder = builder.accept(this.getOperation().getRequestBody().getContent().getMediaTypes().keySet().toArray(new String[0]));
		}
		if(this.getFunctionRefDefinition() != null && this.getFunctionRefDefinition().getInvoke() == Invoke.async) {
			builder.async().method(method.name());
		}
		else {
			final Invocation invoke = builder.build(method.name());
			final javax.ws.rs.core.Response response = invoke.invoke();
			response.close();
		}
		client.close();
	}

	public String getPath() {
		return path;
	}
}
