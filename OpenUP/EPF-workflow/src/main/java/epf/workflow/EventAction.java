package epf.workflow;

import epf.workflow.schema.ActionDefinition;
import epf.workflow.schema.WorkflowDefinition;

/**
 * @author PC
 *
 */
public class EventAction extends Action {

	/**
	 * @param workflowDefinition
	 * @param actionDefinition
	 * @param workflowData
	 */
	public EventAction(
			WorkflowDefinition workflowDefinition, 
			ActionDefinition actionDefinition,
			WorkflowData workflowData) {
		super(workflowDefinition, actionDefinition, workflowData);
	}

	@Override
	protected void perform() throws Exception {
	}
}
