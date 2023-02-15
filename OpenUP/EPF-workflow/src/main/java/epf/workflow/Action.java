package epf.workflow;

import java.time.Duration;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import epf.api.API;
import epf.api.Operation;
import epf.api.PathItem;
import epf.util.ListUtil;
import epf.workflow.schema.ActionDefinition;
import epf.workflow.schema.FunctionDefinition;
import epf.workflow.schema.FunctionRefDefinition;
import epf.workflow.schema.Type;
import epf.workflow.schema.WorkflowDefinition;

/**
 * @author PC
 *
 */
public class Action implements Callable<Void> {
	
	/**
	 * 
	 */
	private final WorkflowDefinition workflowDefinition;
	
	/**
	 * 
	 */
	private final ActionDefinition actionDefinition;
	
	/**
	 * @param workflowDefinition
	 * @param actionDefinition
	 */
	public Action(WorkflowDefinition workflowDefinition, ActionDefinition actionDefinition) {
		this.workflowDefinition = workflowDefinition;
		this.actionDefinition = actionDefinition;
	}
	
	private FunctionDefinition getFunctionDefinition(final WorkflowDefinition workflowDefinition, final String functionRef) {
		if(workflowDefinition.getFunctions() instanceof FunctionDefinition[]) {
			final FunctionDefinition[] functionDefs = (FunctionDefinition[]) workflowDefinition.getFunctions();
			return ListUtil.findFirst(functionDefs, f -> f.getName().equals(functionRef)).get();
		}
		return null;
	}
	
	private void invokeFunction(final WorkflowDefinition workflowDefinition, final FunctionDefinition functionDef, final FunctionRefDefinition functionRef) throws Exception {
		if(functionDef.getType() == Type.rest) {
			final int index = functionDef.getOperation().lastIndexOf("#");
			final String uri = functionDef.getOperation().substring(0, index);
			final String operationId = functionDef.getOperation().substring(index + 1);
			final Client client = ClientBuilder.newClient();
			final API api = client.target(uri).request().accept(MediaType.APPLICATION_JSON).get(API.class);
			for(Entry<String, PathItem> entry : api.getPaths().getPathItems().entrySet()) {
				final PathItem pathItem = entry.getValue();
				final Optional<Operation> operation = pathItem.getOperations().values().stream().filter(o -> o.getOperationId().equals(operationId)).findFirst();
				if(operation.isPresent()) {
					final RestFunction restFunction = new RestFunction(workflowDefinition, functionDef, api, entry.getKey(), pathItem, operation.get(), functionRef);
					restFunction.invoke();
					break;
				}
			}
			client.close();
		}
	}

	@Override
	public Void call() throws Exception {
		if(actionDefinition.getCondition() == null && actionDefinition.getFunctionRef() != null) {
			FunctionDefinition functionDefinition = null;
			FunctionRefDefinition functionRefDefinition = null;
			if(actionDefinition.getFunctionRef() instanceof String) {
				final String functionRef = (String) actionDefinition.getFunctionRef();
				functionDefinition = getFunctionDefinition(workflowDefinition, functionRef);
			}
			else if(actionDefinition.getFunctionRef() instanceof FunctionRefDefinition) {
				functionRefDefinition = (FunctionRefDefinition) actionDefinition.getFunctionRef();
				functionDefinition = getFunctionDefinition(workflowDefinition, functionRefDefinition.getRefName());
			}
			if(functionDefinition != null) {
				if(actionDefinition.getSleep() != null && actionDefinition.getSleep().getBefore() != null) {
					final Duration before = Duration.parse(actionDefinition.getSleep().getBefore());
					Thread.sleep(before.toMillis());
				}
				if(actionDefinition.getRetryRef() != null) {
					final FunctionDefinition functionDef = functionDefinition;
					final FunctionRefDefinition functionRefDef = functionRefDefinition;
					final Callable<Void> callable = new Callable<Void>() {
						@Override
						public Void call() throws Exception {
							invokeFunction(workflowDefinition, functionDef, functionRefDef);
							return null;
						}};
					final Retry<Void> retry = new Retry<>(callable, workflowDefinition, actionDefinition);
					retry.call();
				}
				else {
					invokeFunction(workflowDefinition, functionDefinition, functionRefDefinition);
				}
				if(actionDefinition.getSleep() != null && actionDefinition.getSleep().getAfter() != null) {
					final Duration after = Duration.parse(actionDefinition.getSleep().getAfter());
					Thread.sleep(after.toMillis());
				}
			}
		}
		return null;
	}
}
