package epf.workflow.functions;

import java.util.List;
import java.util.Optional;

import epf.nosql.schema.EitherUtil;
import epf.nosql.schema.StringOrObject;
import epf.workflow.error.WorkflowErrorHandling;
import epf.workflow.functions.openapi.OpenAPIFunctions;
import epf.workflow.model.WorkflowData;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.function.FunctionDefinition;
import epf.workflow.schema.function.FunctionRefDefinition;
import epf.workflow.util.ResponseBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * 
 */
@ApplicationScoped
public class WorkflowFunctions {
	
	/**
	 * 
	 */
	@Inject
	transient OpenAPIFunctions openAPIFunctions;
	
	/**
	 * 
	 */
	@Inject
	transient WorkflowErrorHandling workflowErrorHandling;
	
	public ResponseBuilder function(final ResponseBuilder response, final WorkflowDefinition workflowDefinition, final ActionDefinition actionDefinition, final FunctionDefinition functionDefinition, final WorkflowData workflowData) throws Exception {
		switch(functionDefinition.getType()) {
			case asyncapi:
				break;
			case custom:
				break;
			case expression:
				break;
			case graphql:
				break;
			case odata:
				break;
			case openapi:
				try {
					final Response res = openAPIFunctions.openapi(workflowDefinition, functionDefinition, workflowData.getInput());
					return ResponseBuilder.fromResponse(response, res);
				}
				catch(WebApplicationException ex) {
					workflowErrorHandling.catchException(ex);
				}
			case rest:
				break;
			case rpc:
				break;
			default:
				break;
		}
		return response;
	}
	
	public FunctionDefinition getFunctionDefinition(final WorkflowDefinition workflowDefinition, final StringOrObject<FunctionRefDefinition> functionRef) {
		Optional<FunctionDefinition> functionDefinition = Optional.empty();
		final List<FunctionDefinition> functionDefinitions = EitherUtil.getArray(workflowDefinition.getFunctions());
		if(functionRef.isLeft()) {
			functionDefinition = functionDefinitions.stream().filter(func -> func.getName().equals(functionRef.getLeft())).findFirst();
		}
		else if(functionRef.isRight()) {
			final FunctionRefDefinition functionRefDefinition = functionRef.getRight();
			functionDefinition = functionDefinitions.stream().filter(func -> func.getName().equals(functionRefDefinition.getRefName())).findFirst();
		}
		return functionDefinition.orElseThrow(BadRequestException::new);
	}
}
