package epf.workflow.data;

import epf.workflow.expressions.WorkflowExpressions;
import epf.workflow.schema.action.ActionDataFilter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonValue;

@ApplicationScoped
public class ActionDataFilters {
	
	@Inject
	transient WorkflowExpressions workflowExpressions;
	
	public JsonValue filterActionDataInput(final ActionDataFilter actionDataFilters, final JsonValue input) throws Exception {
		if(actionDataFilters != null && actionDataFilters.getFromStateData() != null) {
			return workflowExpressions.getValue(actionDataFilters.getFromStateData(), input);
		}
		return input;
	}
}
