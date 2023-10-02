package epf.workflow.function;

import java.util.Optional;
import java.util.Map.Entry;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import epf.api.API;
import epf.api.Operation;
import epf.api.PathItem;
import epf.util.ListUtil;
import epf.workflow.action.Action;
import epf.workflow.schema.WorkflowData;
import epf.workflow.schema.WorkflowDefinition;
import epf.workflow.schema.action.ActionDefinition;
import epf.workflow.schema.function.FunctionDefinition;
import epf.workflow.schema.function.FunctionRefDefinition;
import epf.workflow.schema.function.FunctionType;

/**
 * @author PC
 *
 */
public class FunctionAction extends Action {

	/**
	 * @param workflowDefinition
	 * @param actionDefinition
	 * @param workflowData
	 */
	public FunctionAction(
			WorkflowDefinition workflowDefinition, 
			ActionDefinition actionDefinition,
			WorkflowData workflowData) {
		super(workflowDefinition, actionDefinition, workflowData);
	}

	private FunctionDefinition getFunctionDefinition(final WorkflowDefinition workflowDefinition, final String functionRef) {
		if(workflowDefinition.getFunctions() instanceof FunctionDefinition[]) {
			final FunctionDefinition[] functionDefs = (FunctionDefinition[]) workflowDefinition.getFunctions();
			return ListUtil.findFirst(functionDefs, f -> f.getName().equals(functionRef)).get();
		}
		return null;
	}
	
	private void performRestFunction(final WorkflowDefinition workflowDefinition, final FunctionDefinition functionDef, final FunctionRefDefinition functionRef, final WorkflowData workflowData) throws Exception {
		if(functionDef.getType() == FunctionType.rest) {
			final int index = functionDef.getOperation().lastIndexOf("#");
			final String uri = functionDef.getOperation().substring(0, index);
			final String operationId = functionDef.getOperation().substring(index + 1);
			final Client client = ClientBuilder.newClient();
			final API api = client.target(uri).request().accept(MediaType.APPLICATION_JSON).get(API.class);
			for(Entry<String, PathItem> entry : api.getPaths().getPathItems().entrySet()) {
				final PathItem pathItem = entry.getValue();
				final Optional<Operation> operation = pathItem.getOperations().values().stream().filter(o -> o.getOperationId().equals(operationId)).findFirst();
				if(operation.isPresent()) {
					final RestFunction restFunction = new RestFunction(workflowDefinition, functionDef, api, entry.getKey(), pathItem, operation.get(), functionRef, workflowData);
					restFunction.invoke();
					break;
				}
			}
			client.close();
		}
	}

	@Override
	protected void perform() throws Exception {
		FunctionDefinition functionDefinition = null;
		FunctionRefDefinition functionRefDefinition = null;
		if(getActionDefinition().getFunctionRef() instanceof String) {
			final String functionRef = (String) getActionDefinition().getFunctionRef();
			functionDefinition = getFunctionDefinition(getWorkflowDefinition(), functionRef);
		}
		else if(getActionDefinition().getFunctionRef() instanceof FunctionRefDefinition) {
			functionRefDefinition = (FunctionRefDefinition) getActionDefinition().getFunctionRef();
			functionDefinition = getFunctionDefinition(getWorkflowDefinition(), functionRefDefinition.getRefName());
		}
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
			case rest:
				performRestFunction(getWorkflowDefinition(), functionDefinition, functionRefDefinition, getWorkflowData());
				break;
			case rpc:
				break;
			default:
				break;
		}
	}
}
